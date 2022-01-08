package com.credorax.service;

import com.credorax.data.Transaction;
import com.credorax.data.validator.Validator;
import org.springframework.lang.NonNull;

public interface TransactionService {

    Validator save(@NonNull Transaction transaction);

    Transaction get(@NonNull Long invoice);
}
