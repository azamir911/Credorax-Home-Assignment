package com.credorax.data.processor;

import com.credorax.data.Transaction;

abstract class TransactionProcessor {

    protected TransactionProcessor nextProcessor;

    public TransactionProcessor(TransactionProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public void process(Transaction transaction) {
        doProcess(transaction);
        if (this.nextProcessor != null) {
            this.nextProcessor.process(transaction);
        }
    }

    protected abstract void doProcess(Transaction transaction);

}
