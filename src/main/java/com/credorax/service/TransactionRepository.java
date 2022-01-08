package com.credorax.service;

import com.credorax.data.Transaction;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface TransactionRepository {

    void save(@NonNull Transaction transaction);

    Optional<Transaction> get(@NonNull Long invoice);

}
