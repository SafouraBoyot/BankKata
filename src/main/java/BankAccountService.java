public class BankAccountService {
    private final Clock clock;
    private final TransactionRepository transactionRepository;

    public BankAccountService(Clock clock, TransactionRepository transactionRepository) {

        this.clock = clock;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(int amount) {
     transactionRepository.add(new Transaction(amount,clock.today()));
    }

    public void withdraw(int amount) {
        throw new UnsupportedOperationException();
    }

    public void printStatement() {
        throw new UnsupportedOperationException();
    }
}
