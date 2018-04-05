import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceShould {

    @Mock
    private Clock clock;

    @Mock
    private Output outPut;

    @Mock
    private TransactionRepository transactionRepository;


    private BankAccountService bankAccountService;


    @Before
    public void
    setUp() {
        bankAccountService = new BankAccountService(clock, transactionRepository);

    }
    @Test public void
    accept_deposit(){
        given(clock.today()).willReturn("10-01-2012");

        bankAccountService.deposit(1000);

        verify(transactionRepository).add(new Transaction(1000,"10-01-2012"));
    }

    
}
