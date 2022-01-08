package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;

class DecodeExpiryDateProcessor extends TransactionProcessor {

    private final Base64 base64 = new Base64();

    public DecodeExpiryDateProcessor(TransactionProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doProcess(Transaction transaction) {
        final var card = transaction.getCard();
        final var expiry = card.getExpiry();
        var newValue = new String(base64.decode(expiry.getBytes()));
        newValue = "*".repeat(newValue.length());
        card.setExpiry(newValue);
    }
}
