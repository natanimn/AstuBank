package et.edu.astu.core.services;

import et.edu.astu.core.dtos.DepositRequest;
import et.edu.astu.core.dtos.DepositResponse;
import et.edu.astu.core.generators.TransactionGenerator;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.Employee;
import et.edu.astu.core.models.transactions.Deposit;
import et.edu.astu.core.models.transactions.Withdraw;
import et.edu.astu.core.repositories.AccountRepository;
import et.edu.astu.core.repositories.EmployeeRepository;
import et.edu.astu.core.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final TransactionGenerator generator;
    private final BotService botService;

    @Transactional
    public DepositResponse deposit(String employeeId, DepositRequest request){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Account account = accountRepository.findByAccountNumber(request.accountNumber()).orElseThrow();
        String transactionId = generator.generateTransactionId(repository.count(), request.amount());

        Deposit deposit = new Deposit();
        deposit.setTransactionId(transactionId);
        deposit.setPerformer(employee);
        deposit.setHolder(account);
        deposit.setAmount(request.amount());
        account.setBalance(account.getBalance() + request.amount());
        repository.save(deposit);

        botService.notifyDeposit(account, deposit);

        return DepositResponse.map(deposit);
    }

    @Transactional
    public DepositResponse withdraw(String employeeId, DepositRequest request){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Account account = accountRepository.findByAccountNumber(request.accountNumber()).orElseThrow();
        String transactionId = generator.generateTransactionId(repository.count(), request.amount());

        if (request.amount() > account.getBalance())
            throw new RuntimeException("Insufficient remaining funds");

        Withdraw withdraw = new Withdraw();
        withdraw.setTransactionId(transactionId);
        withdraw.setPerformer(employee);
        withdraw.setHolder(account);
        withdraw.setAmount(request.amount());
        account.setBalance(account.getBalance() - request.amount());
        repository.save(withdraw);

        botService.notifyWithdraw(account, withdraw);

        return DepositResponse.map(withdraw);
    }
}
