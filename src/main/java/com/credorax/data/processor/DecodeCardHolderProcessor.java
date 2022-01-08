package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;

class DecodeCardHolderProcessor extends TransactionProcessor {

    private final Base64 base64 = new Base64();

    public DecodeCardHolderProcessor(TransactionProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    protected void doProcess(Transaction transaction) {
        final var cardholder = transaction.getCardholder();
        final var name = cardholder.getName();
        var newValue = new String(base64.decode(name.getBytes()));
        newValue = "*".repeat(newValue.length());
        cardholder.setName(newValue);
    }
}
