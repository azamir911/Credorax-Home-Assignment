package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;

class EncodePanProcessor extends TransactionProcessor {

    private final Base64 base64 = new Base64();

    public EncodePanProcessor(TransactionProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doProcess(Transaction transaction) {
        final var card = transaction.getCard();
        final var pan = card.getPan();
        final var newValue = new String(base64.encode(pan.getBytes()));
        card.setPan(newValue);
    }
}
