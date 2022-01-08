package com.credorax.data.validator;

import com.credorax.data.Transaction;

abstract class TransactionValidator {

    protected TransactionValidator nextValidator;

    public TransactionValidator(TransactionValidator nextProcessor) {
        this.nextValidator = nextProcessor;
    }

    public void validate(Transaction transaction, Validator validator) {
        doValidate(transaction, validator);
        if (this.nextValidator != null) {
            this.nextValidator.validate(transaction, validator);
        }
    }

    protected abstract void doValidate(Transaction transaction, Validator validator);

}
