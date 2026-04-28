package et.edu.astu.core.services;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.CreateAccountRequest;
import et.edu.astu.core.mapper.Mapper;
import et.edu.astu.core.generators.AccountGenerator;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.User;
import et.edu.astu.core.repositories.AccountRepository;
import et.edu.astu.core.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountService class.
 *
 * @author Natanim
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountGenerator generator;
    private final UserRepository userRepository;

    private void validate(CreateAccountRequest request){
        if (request.firstName() == null)
            throw new RuntimeException("First name cannot be empty nor null");
        if (request.middleName() == null)
            throw new RuntimeException("Middle name cannot be empty nor null");
        if (request.lastName() == null)
            throw new RuntimeException("Last name cannot be empty nor null");
        if (request.birthDate() == null)
            throw new RuntimeException("Birthdate cannot be empty nor null");
        if (request.initialBalance() == null)
            throw new RuntimeException("Initial balance cannot be empty nor null");
        if (request.phone() == null)
            throw new RuntimeException("Phone number cannot be empty nor null");
    }
    @Transactional
    public AccountResponse create(CreateAccountRequest dto){
        validate(dto);
        long accountNumber = generator.generate(dto);
        Account account = new Account(
                accountNumber,
                dto.firstName().toUpperCase(),
                dto.middleName().toUpperCase(),
                dto.lastName().toUpperCase(),
                dto.phone(),
                dto.birthDate(),
                dto.initialBalance()
        );

        Account saved = repository.save(account);
        return Mapper.map(saved);
    }

    public AccountResponse searchByPhone(String phone){
        Account account = repository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return Mapper.map(account);
    }

    public AccountResponse searchByAccount(Long accountNumber){
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return Mapper.map(account);
    }

    public AccountResponse findCustomer(Long accountNumber){
        return repository.findByAccountNumber(accountNumber)
                .map(Mapper::map)
                .orElseThrow();
    }

    @Transactional
    protected void linkWithTelegram(Long accountNumber, Long userId){
        Account account = repository.findByAccountNumber(accountNumber)
                .filter(a -> !a.linkedWithTelegram())
                .orElseThrow(() -> new RuntimeException("Account not found or already linked with other telegram user"));
        User user = userRepository.findByUserId(userId)
                .filter(u -> u.getAccount() == null)
                .orElseThrow(() -> new RuntimeException("User not found or already linked to another account"));

        account.setUser(user);
        user.setAccount(account);
    }

    @Transactional
    public void unlinkWithTelegram(Long accountNumber){
        Account account = repository.findByAccountNumber(accountNumber)
                .filter(Account::linkedWithTelegram)
                .orElseThrow(() -> new RuntimeException("Account not found or already not linked with telegram user"));
        account.setUser(null);
        User user = account.getUser();
        user.setAccount(null);
    }
}
