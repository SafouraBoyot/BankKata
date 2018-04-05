import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PrintStatementShould {
    @Mock
    private Clock clock;

    @Mock
    private Output consoleOutPut;


    private BankAccountService bankAccountService;

    private TransactionRepository transactionRepository;

    private StatementPrinter statementPrinter;

    @Before
    public void
    setUp() {
        transactionRepository = new TransactionRepository();
        statementPrinter = new StatementPrinter(consoleOutPut);
        bankAccountService = new BankAccountService(clock, transactionRepository, statementPrinter);

    }


    @Test
    public void
    print_statement_with_all_transactions() {

        given(clock.today()).willReturn("10/01/2012", "13/01/2012", "13/01/2012");

        bankAccountService.deposit(1000);
        bankAccountService.deposit(2000);
        bankAccountService.withdraw(500);

        bankAccountService.printStatement();

        verify(consoleOutPut).printLine("date || credit || debit || balance");
        verify(consoleOutPut).printLine("13/01/2012 || || 500.00 || 2500.00");
        verify(consoleOutPut).printLine("13/01/2012 || 2000.00 || || 3000.00");
        verify(consoleOutPut).printLine("10/01/2012 || 1000.00 || || 1000.00");

    }

}
