package com.credorax.service;

import com.credorax.data.Transaction;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
class TransactionRepositoryImpl implements TransactionRepository {

    Map<Long, Transaction> map = new ConcurrentHashMap<>();

    @Override
    public void save(@NonNull Transaction transaction) {
        map.put(transaction.getInvoice(), transaction);
    }

    @Override
    public Optional<Transaction> get(@NonNull Long invoice) {
        return Optional.ofNullable(map.get(invoice));
    }
}
