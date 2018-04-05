import com.codurance.Output;
import com.codurance.StatementPrinter;
import com.codurance.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class StatementPrinterShould {

    @Mock
    private Output consoleOutput;

    @Test
    public void
    print_transactions_in_reverse_order() {

        List<Transaction> transactions = asList(new Transaction(1000, "10/01/2012")
                , new Transaction(2000, "13/01/2012")
                , new Transaction(-500, "14/01/2012"));

        StatementPrinter statementPrinter = new StatementPrinter(consoleOutput);

        statementPrinter.printStatement(transactions);
        InOrder inOrder = inOrder(consoleOutput);

        inOrder.verify(consoleOutput).printLine("date || credit || debit || balance");
        inOrder.verify(consoleOutput).printLine("14/01/2012 || || 500.00 || 2500.00");
        inOrder.verify(consoleOutput).printLine("13/01/2012 || 2000.00 || || 3000.00");
        inOrder.verify(consoleOutput).printLine("10/01/2012 || 1000.00 || || 1000.00");



    }

}
