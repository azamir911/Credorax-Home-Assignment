package com.credorax.rest;

//import lombok.extern.slf4j.Slf4j;

import com.credorax.audit.AuditService;
import com.credorax.data.Transaction;
import com.credorax.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PaymentGatewayRestController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AuditService auditService;

	@ApiOperation(value = "Get a Transaction and run: Validation, PCI Restrictions, Persistence and Auditing",
			notes = "If the Validation was failed, return an error.",
			tags = {"PaymentGateway"})
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ResponseEntity<Object> submitPayment(@RequestBody Transaction transaction) {
		try {
			final var validator = transactionService.save(transaction);
			return new ResponseEntity<>(validator, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Got exception while trying to execute 'submitPayment'", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get an Invoice number and return the Transaction after running PCI Restrictions",
			notes = "Return an error if the Invoice doesn't exists.",
			tags = {"PaymentGateway"})
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieve(@RequestParam long invoice) {
		Transaction transaction;
		try {
			transaction = transactionService.get(invoice);
		} catch (Exception e) {
			log.error("Got exception while trying to execute 'retrieve'", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

	@ApiOperation(value = "Get a new file path for auditing",
			notes = "This is not persistence",
			tags = {"Audit"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Object> updateFile(@RequestBody String path) {
		try {
			auditService.updateFile(path);
		} catch (Exception e) {
			log.error("Got exception while trying to execute 'update' to file", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
