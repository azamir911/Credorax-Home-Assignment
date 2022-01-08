package com.credorax.data.validator;

import com.credorax.data.Transaction;
import org.apache.logging.log4j.util.Strings;

class PanValidator extends TransactionValidator {

    public PanValidator(TransactionValidator nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doValidate(Transaction transaction, Validator validator) {
        final var card = transaction.getCard();
        if (card != null) {
            final var pan = card.getPan();
            if (!Strings.isBlank(transaction.getCard().getPan())) {
                if (pan.length() != 16) {
                    validator.addError("pan", "PAN should be 16 digits long.");
                }
                else if (!checkLuhn(pan)) {
                    validator.addError("pan", "Invalid PAN format.");
                }
            }
        }
    }

    private boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNo.charAt(i) - '0';

            if (isSecond) {
                d = d * 2;
            }

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
