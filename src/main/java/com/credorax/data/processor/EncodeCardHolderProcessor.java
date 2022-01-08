package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;

class EncodeCardHolderProcessor extends TransactionProcessor {

    private final Base64 base64 = new Base64();

    public EncodeCardHolderProcessor(TransactionProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doProcess(Transaction transaction) {
        final var cardholder = transaction.getCardholder();
        final var name = cardholder.getName();
        final var newValue = new String(base64.encode(name.getBytes()));
        cardholder.setName(newValue);
    }
}
