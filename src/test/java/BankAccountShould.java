import com.codurance.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountShould {

    @Mock
    private Clock clock;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private final List<Transaction> transactions = new ArrayList<>();

    @Mock
    private StatementPrinter statementPrinter;

    private BankAccount bankAccount;


    @Before
    public void
    setUp() {
        bankAccount = new BankAccount(clock, transactionRepository, statementPrinter);

    }

    @Test
    public void
    accept_deposit() {
        given(clock.today()).willReturn("10/01/2012");

        bankAccount.deposit(1000);

        verify(transactionRepository).add(new Transaction(1000, "10/01/2012"));
    }


    @Test
    public void
    accept_withdrawal() {
        given(clock.today()).willReturn("14/01/2012");

        bankAccount.withdraw(500);

        verify(transactionRepository).add(new Transaction(-500, "14/01/2012"));
    }

    @Test
    public void
    print_statement() {
        given(transactionRepository.retrieveAll()).willReturn(transactions);

        bankAccount.printStatement();

        verify(statementPrinter).printStatement(transactions);
    }
}
