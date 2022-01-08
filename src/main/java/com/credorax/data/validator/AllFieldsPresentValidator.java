package com.credorax.data.validator;

import com.credorax.data.Transaction;
import org.apache.logging.log4j.util.Strings;

class AllFieldsPresentValidator extends TransactionValidator {

    public AllFieldsPresentValidator(TransactionValidator nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doValidate(Transaction transaction, Validator validator) {
        if (transaction.getInvoice() == null) {
            validator.addError("invoice", "Invoice is required.");
        }
        if (transaction.getAmount() == null) {
            validator.addError("amount", "Amount is required.");
        }
        if (Strings.isBlank(transaction.getCurrency())) {
            validator.addError("currency", "Currency is required.");
        }
        if (transaction.getCardholder() == null) {
            validator.addError("name", "Name is required.");
            validator.addError("email", "Email is required.");
        }
        else {
            if (Strings.isBlank(transaction.getCardholder().getName())) {
                validator.addError("name", "Name is required.");
            }
            if (Strings.isBlank(transaction.getCardholder().getEmail())) {
                validator.addError("email", "Email is required.");
            }
        }
        if (transaction.getCard() == null) {
            validator.addError("pan", "Pan is required.");
            validator.addError("expiry", "Expiry is required.");
        }
        else {
            if (Strings.isBlank(transaction.getCard().getPan())) {
                validator.addError("pan", "Pan is required.");
            }
            if (Strings.isBlank(transaction.getCard().getExpiry())) {
                validator.addError("expiry", "Expiry is required.");
            }
        }
    }
}
