import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StatementPrinter {

    private static final String HEADER = "date || credit || debit || balance";
    private final Output output;


    public StatementPrinter(Output output) {
        this.output = output;
    }

    public void print(List<Transaction> transactions) {
        printHeader();
        printTransactions(transactions);
    }

    private void printTransactions(List<Transaction> transactions) {
        AtomicInteger balance = new AtomicInteger(0);
        List<String> reverseTransactions = createStatementFor(transactions, balance);
        Collections.reverse(reverseTransactions);
        for (String statement : reverseTransactions) {
            output.printLine(statement);

        }
    }


    private List<String> createStatementFor(List<Transaction> transactions, AtomicInteger balance) {
        List<String> statements = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        for (Transaction transaction : transactions) {
            if (transaction.amount() < 0) {
                statements.add(transaction.date() + " ||" + " || " + df.format(Math.abs(transaction.amount())) + " || " + df.format(balance.addAndGet(transaction.amount())));
            }
            if (transaction.amount() > 0) {
                statements.add(transaction.date() + " || " + df.format(Math.abs(transaction.amount())) + " ||" + " || " + df.format(balance.addAndGet(transaction.amount())));
            }
        }
        return statements;
    }

    private void printHeader() {
        output.printLine(HEADER);
    }
}
