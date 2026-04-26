package et.edu.astu.core.services;

import et.edu.astu.common.dto.DepositRequest;
import et.edu.astu.common.dto.DepositResponse;
import et.edu.astu.common.dto.TransactionResponses;
import et.edu.astu.common.dto.TransferRequest;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.common.interfaces.TransactionResponse;
import et.edu.astu.core.mapper.Mapper;
import et.edu.astu.core.generators.TransactionGenerator;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.Employee;
import et.edu.astu.core.models.transactions.Deposit;
import et.edu.astu.core.models.transactions.Transaction;
import et.edu.astu.core.models.transactions.Transfer;
import et.edu.astu.core.models.transactions.Withdraw;
import et.edu.astu.core.repositories.AccountRepository;
import et.edu.astu.core.repositories.EmployeeRepository;
import et.edu.astu.core.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

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
        String transactionId = generator.generateTransactionId(repository.count(), (int) request.amount());

        Deposit deposit = new Deposit();
        deposit.setTransactionId(transactionId);
        deposit.setPerformer(employee);
        deposit.setHolder(account);
        deposit.setAmount(request.amount());
        account.setBalance(account.getBalance() + request.amount());
        repository.save(deposit);

        botService.notifyDeposit(account, deposit);

        return Mapper.map(deposit);
    }

    @Transactional
    public DepositResponse withdraw(String employeeId, DepositRequest request){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Account account = accountRepository.findByAccountNumber(request.accountNumber()).orElseThrow();
        String transactionId = generator.generateTransactionId(repository.count(), (int) request.amount());

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

        return Mapper.map(withdraw);
    }

    private void validateTransfer(TransferRequest request){
        if (request.sender() == null)
            throw new RuntimeException("Sender field required");
        if (request.receiver() == null)
            throw new RuntimeException("Receiver field required");
        if (request.amount() == null)
            throw new RuntimeException("Amount field required");
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request){
        validateTransfer(request);
        Account sender = accountRepository.findByAccountNumber(request.sender()).orElseThrow();
        Account receiver = accountRepository.findByAccountNumber(request.sender()).orElseThrow();

        if (request.amount() > sender.getBalance())
            throw new RuntimeException("Insufficient remaining funds");

        if (request.receiver().equals(request.sender()))
            throw new RuntimeException("Receiver's and Sender's account numbers are the same");

        Transfer transfer = new Transfer();
        String transactionId = generator.generateTransactionId(repository.count(), request.amount());

        transfer.setTransactionId(transactionId);
        transfer.setAmount(request.amount());
        transfer.setHolder(sender);
        transfer.setReceiver(receiver);

        sender.setBalance(sender.getBalance() - request.amount());
        receiver.setBalance(receiver.getBalance() + request.amount());

        botService.notifyTransfer(transfer);

        return Mapper.map(transfer);
    }

    public TransactionResponses findTransaction(Long accountNumber, Pageable pageable){
        List<TransactionResponse> transactions = repository.findTransactions(accountNumber, pageable);
        long count = repository.countByAccountAccountNumber(accountNumber);

        return new TransactionResponses(count, transactions);
    }

    public Object findTransaction(String trxId){
        Transaction transaction = repository.findByTransactionId(trxId).orElseThrow();

        if (transaction instanceof Deposit || transaction instanceof Withdraw)
            return Mapper.map(transaction);
        if (transaction instanceof Transfer)
            return Mapper.map((Transfer) transaction);

        return transaction;
    }
}
