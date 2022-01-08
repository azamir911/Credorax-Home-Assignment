package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;

class DecodePanProcessor extends TransactionProcessor {

    private final Base64 base64 = new Base64();

    public DecodePanProcessor(TransactionProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doProcess(Transaction transaction) {
        final var card = transaction.getCard();
        final var pan = card.getPan();
        var newValue = new String(base64.decode(pan.getBytes()));
        newValue = "*".repeat(newValue.length() - 4) + newValue.substring(newValue.length() - 4);
        card.setPan(newValue);
    }
}
