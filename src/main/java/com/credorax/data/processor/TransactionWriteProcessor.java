package com.credorax.data.processor;

import com.credorax.data.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionWriteProcessor {
    private final TransactionProcessor processor;

    public TransactionWriteProcessor() {
        final var panProcessor = new EncodePanProcessor(null);
        final var expiryDateProcessor = new EncodeExpiryDateProcessor(panProcessor);
        processor = new EncodeCardHolderProcessor(expiryDateProcessor);
    }

    public void process(Transaction transaction) {
        this.processor.process(transaction);
    }
}
