package com.credorax.data.validator;

import com.credorax.data.Transaction;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;

@FieldDefaults(level = AccessLevel.PRIVATE)
class EmailValidator extends TransactionValidator {

    org.apache.commons.validator.EmailValidator emailValidator;

    public EmailValidator(TransactionValidator nextProcessor) {
        super(nextProcessor);
        emailValidator = org.apache.commons.validator.EmailValidator.getInstance();
    }

    @Override
    protected void doValidate(Transaction transaction, Validator validator) {
        final var cardholder = transaction.getCardholder();
        if (cardholder != null) {
            final var email = cardholder.getEmail();
            if (!Strings.isBlank(email)) {
                final var valid = this.emailValidator.isValid(email);
                if (!valid) {
                    validator.addError("email", "Invalid cardholder email format.");
                }
            }
        }
    }
}
