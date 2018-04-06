package com.codurance;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class StatementPrinter {

    private static final String HEADER = "date || credit || debit || balance";
    private final Output output;


    public StatementPrinter(Output output) {
        this.output = output;
    }

    public void printStatement(List<Transaction> transactions) {
        printHeader();
        printBody(transactions);
    }

    private void printHeader() {
        output.printLine(HEADER);
    }

    private void printBody(List<Transaction> transactions) {
        AtomicInteger balance = new AtomicInteger(0);
        List<String> reverseTransactionLines = statementBodyLinesFor(transactions, balance);
        Collections.reverse(reverseTransactionLines);
        reverseTransactionLines
                .stream()
                .forEach(transactionLine -> output.printLine(transactionLine));
    }


    private List<String> statementBodyLinesFor(List<Transaction> transactions, AtomicInteger balance) {
        return transactions
                .stream()
                .map(transaction -> formatStatementBodyLineFor(balance, transaction))
                .collect(toList());
    }

    private String formatStatementBodyLineFor(AtomicInteger balance, Transaction transaction) {
        DecimalFormat df = new DecimalFormat("#.00");
        Integer amount = transaction.amount();
        String date = transaction.date();
        if (transaction.amount() < 0) {
            return date + " ||" + " || " + df.format(Math.abs(amount)) + " || " + df.format(balance.addAndGet(amount));
        }
        return date + " || " + df.format(Math.abs(amount)) + " ||" + " || " + df.format(balance.addAndGet(amount));
    }

}
