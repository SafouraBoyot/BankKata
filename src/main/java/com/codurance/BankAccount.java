package com.codurance;

import java.util.List;

public class BankAccount {
    private final Clock clock;
    private final TransactionRepository transactionRepository;
    private StatementPrinter statementPrinter;


    public BankAccount(Clock clock, TransactionRepository transactionRepository, StatementPrinter statementPrinter) {

        this.clock = clock;
        this.transactionRepository = transactionRepository;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(int amount) {
        transactionRepository.add(new Transaction(amount, clock.today()));
    }

    public void withdraw(int amount) {
        transactionRepository.add(new Transaction(-amount, clock.today()));
    }

    public void printStatement() {

        List transactions = transactionRepository.retrieveAll();
        statementPrinter.printStatement(transactions);

    }
}
