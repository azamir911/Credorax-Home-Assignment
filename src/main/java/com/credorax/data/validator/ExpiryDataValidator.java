package com.credorax.data.validator;

import com.credorax.data.Transaction;
import org.apache.logging.log4j.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ExpiryDataValidator extends TransactionValidator {

    public ExpiryDataValidator(TransactionValidator nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doValidate(Transaction transaction, Validator validator) {
        final var card = transaction.getCard();
        if (card != null) {
            final var expiry = card.getExpiry();
            if (!Strings.isBlank(expiry)) {
                if (expiry.length() != 4) {
                    validator.addError("expiry", "Payment card should be 4 digits long.");
                }
                else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");
                    simpleDateFormat.setLenient(false);
                    try {
                        Date expiryDate = simpleDateFormat.parse(expiry);
                        boolean expired = expiryDate.before(new Date());
                        if (expired) {
                            validator.addError("expiry", "Payment card is expired.");
                        }
                    } catch (ParseException e) {
                        validator.addError("expiry", "Invalid payment card format.");
                    }
                }
            }
        }
    }
}
