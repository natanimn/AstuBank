package et.edu.astu.core.services;

import et.edu.astu.core.dtos.AccountResponse;
import et.edu.astu.core.dtos.CreateAccountRequest;
import et.edu.astu.core.generators.AccountGenerator;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountGenerator generator;

    @Transactional
    public AccountResponse create(CreateAccountRequest dto){
        long accountNumber = generator.generate(dto);
        Account account = new Account(
                accountNumber,
                dto.firstName(),
                dto.middleName(),
                dto.lastName(),
                dto.birthDate(),
                dto.initialBalance()
        );

        Account saved = repository.save(account);
        return AccountResponse.map(saved);
    }

    public AccountResponse searchByPhone(String phone){
        Account account = repository.findByPhoneNumber(phone)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountResponse.map(account);
    }

    @Transactional
    public void linkWithTelegram(Long accountNumber, Long userId){
        Account account = repository.findByAccountNumber(accountNumber)
                .filter(a -> !a.linkedWithTelegram())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setTelegramUserId(userId);
        account.setLinkVerified(false);
    }



    public boolean linkedWithTelegramId(long accountNumber, long telegramUserId){
        Account account = repository.findByAccountNumber(accountNumber).orElseThrow();
        return account.linkedWithTelegram() && account.getTelegramUserId().equals(telegramUserId);
    }
}
