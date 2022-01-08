package com.credorax.service;

import com.credorax.data.Transaction;
import com.credorax.data.processor.TransactionReadProcessor;
import com.credorax.data.processor.TransactionWriteProcessor;
import com.credorax.data.validator.TransactionWriteValidator;
import com.credorax.data.validator.Validator;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionWriteProcessor writeProcessor;
    @Autowired
    TransactionReadProcessor readProcessor;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionWriteValidator transactionWriteValidator;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Validator save(@NonNull Transaction transaction) {
        final var validate = transactionWriteValidator.validate(transaction);
        if (validate.isValid()) {
            writeProcessor.process(transaction);
            transactionRepository.save(transaction);
            applicationEventPublisher.publishEvent(new TransactionSavedEvent(transaction));
        }

        return validate;
    }

    @Override
    public Transaction get(@NonNull Long invoice) {
        final var optionalTransaction = transactionRepository.get(invoice);
        final var transaction = optionalTransaction.orElseThrow(() -> new RuntimeException("Transaction '" + invoice + "' doesn't exists"));
        readProcessor.process(transaction);
        return transaction;
    }
}
