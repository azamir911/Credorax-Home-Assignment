package com.credorax.service;

import com.credorax.data.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionSavedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7291160392572635811L;

	protected TransactionSavedEvent(Transaction transaction) {
		super(transaction);
	}

}
