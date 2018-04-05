import java.text.DecimalFormat;
import java.util.ArrayList;
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

    public void print(List<Transaction> transactions) {
        printHeader();
        printStatement(transactions);
    }

    private void printStatement(List<Transaction> transactions) {
        AtomicInteger balance = new AtomicInteger(0);
        List<String> reverseTransactionLines = statementLinesFor(transactions, balance);
        Collections.reverse(reverseTransactionLines);
        reverseTransactionLines
                .stream()
                .forEach(transactionLine -> output.printLine(transactionLine));
    }


    private List<String> statementLinesFor(List<Transaction> transactions, AtomicInteger balance) {
        return transactions
                .stream()
                .map(transaction -> statementLineFor(balance, transaction))
                .collect(toList());
    }

    private String statementLineFor(AtomicInteger balance, Transaction transaction) {
        DecimalFormat df = new DecimalFormat("#.00");
        String statementLine = null;
        if (transaction.amount() < 0) {
            statementLine = transaction.date() + " ||" + " || " + df.format(Math.abs(transaction.amount())) + " || " + df.format(balance.addAndGet(transaction.amount()));
        }
        if (transaction.amount() > 0) {
            statementLine = transaction.date() + " || " + df.format(Math.abs(transaction.amount())) + " ||" + " || " + df.format(balance.addAndGet(transaction.amount()));
        }
        return statementLine;
    }

    private void printHeader() {
        output.printLine(HEADER);
    }
}
