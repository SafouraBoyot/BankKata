import com.codurance.Transaction;
import com.codurance.TransactionRepository;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionRepositoryShould {

    @Test
    public void
    retrieveAll_transaction_in_proper_order() {
        Transaction depositTransaction = new Transaction(1000, "10-01-2012");
        Transaction withdrawalTransaction = new Transaction(-500, "14-01-2012");
        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.add(depositTransaction);
        transactionRepository.add(withdrawalTransaction);

        List<Transaction> actualTransactions = transactionRepository.retrieveAll();

        assertEquals(2, actualTransactions.size());
        assertEquals(depositTransaction, actualTransactions.get(0));
        assertEquals(withdrawalTransaction, actualTransactions.get(1));


    }
}
