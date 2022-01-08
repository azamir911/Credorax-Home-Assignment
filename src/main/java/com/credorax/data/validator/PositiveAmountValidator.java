package com.credorax.data.validator;

import com.credorax.data.Transaction;

class PositiveAmountValidator extends TransactionValidator {

    public PositiveAmountValidator(TransactionValidator nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doValidate(Transaction transaction, Validator validator) {
        final var amount = transaction.getAmount();
        if (amount != null) {
            if (amount <= 0) {
                validator.addError("amount", "Amount should be a positive double.");
            }
        }
    }
}
