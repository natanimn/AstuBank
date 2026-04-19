package et.edu.astu.core.services;

import et.edu.astu.core.dtos.AccountResponseDTO;
import et.edu.astu.core.dtos.CreateAccountRequestDTO;
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
    public AccountResponseDTO create(CreateAccountRequestDTO dto){
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
        return AccountResponseDTO.map(saved);
    }

    public boolean linkedWithTelegramId(long accountNumber, long telegramUserId){
        Account account = repository.findById(accountNumber).orElseThrow();
        return account.linkedWithTelegram() && account.getTelegramUserId().equals(telegramUserId);
    }
}
