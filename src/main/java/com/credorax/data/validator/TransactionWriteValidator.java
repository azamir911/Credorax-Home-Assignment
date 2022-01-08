package com.credorax.data.validator;

import com.credorax.data.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionWriteValidator {
    private final TransactionValidator validator;

    public TransactionWriteValidator() {
        final var emailValidator = new EmailValidator(null);
        final var expiryDataValidator = new ExpiryDataValidator(emailValidator);
        final var positiveAmountValidator = new PositiveAmountValidator(expiryDataValidator);
        final var panValidator = new PanValidator(positiveAmountValidator);
        validator = new AllFieldsPresentValidator(panValidator);
    }

    public Validator validate(Transaction transaction) {
        final var validator = new Validator();
        this.validator.validate(transaction, validator);
        return validator;
    }
}
