package com.codurance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TransactionRepository {


    private final List<Transaction> transactions;

    public TransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public List retrieveAll() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRepository that = (TransactionRepository) o;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
}
