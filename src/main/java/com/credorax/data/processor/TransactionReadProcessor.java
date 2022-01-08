package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionReadProcessor {

    private final TransactionProcessor processor;

    public TransactionReadProcessor() {
        final var panProcessor = new DecodePanProcessor(null);
        final var expiryDateProcessor = new DecodeExpiryDateProcessor(panProcessor);
        processor = new DecodeCardHolderProcessor(expiryDateProcessor);
    }

    public void process(Transaction transaction) {
        this.processor.process(transaction);
    }

}
