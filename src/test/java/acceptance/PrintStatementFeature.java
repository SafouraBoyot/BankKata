package acceptance;

import com.codurance.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PrintStatementFeature {
    @Mock
    private Clock clock;

    @Mock
    private Output consoleOutput;


    private BankAccount bankAccount;

    private TransactionRepository transactionRepository;

    private StatementPrinter statementPrinter;

    @Before
    public void
    setUp() {
        transactionRepository = new TransactionRepository();
        statementPrinter = new StatementPrinter(consoleOutput);
        bankAccount = new BankAccount(clock, transactionRepository, statementPrinter);

    }


    @Test
    public void
    print_statement_with_all_transactions() {

        given(clock.today()).willReturn("10/01/2012", "13/01/2012", "14/01/2012");
        bankAccount.deposit(1000);
        bankAccount.deposit(2000);
        bankAccount.withdraw(500);

        bankAccount.printStatement();

        verify(consoleOutput).printLine("date || credit || debit || balance");
        verify(consoleOutput).printLine("14/01/2012 || || 500.00 || 2500.00");
        verify(consoleOutput).printLine("13/01/2012 || 2000.00 || || 3000.00");
        verify(consoleOutput).printLine("10/01/2012 || 1000.00 || || 1000.00");

    }

}
