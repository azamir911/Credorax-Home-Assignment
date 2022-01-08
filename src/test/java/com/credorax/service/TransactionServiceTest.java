package com.credorax.service;

import com.credorax.data.Card;
import com.credorax.data.Cardholder;
import com.credorax.data.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void testSubmitAndRetrieve() {
        final var cardholder1 = new Cardholder("First Last", "email@domain.com");
        final var card1 = new Card("4188846122476411", "0624");
        final var t1 = new Transaction(1L, 100D, "EUR", cardholder1, card1);
        transactionService.save(t1);

        final var cardholder2 = new Cardholder("First Last", "email@domain.com");
        final var card2 = new Card("4580276254729185", "0624");
        final var t2 = new Transaction(2L, 100D, "EUR", cardholder2, card2);
        transactionService.save(t2);

        final var cardholder3 = new Cardholder("First Last", "email@domain.com");
        final var card3 = new Card("4580363316282013", "0624");
        final var t3 = new Transaction(3L, 100D, "EUR", cardholder3, card3);
        transactionService.save(t3);

        final var t4 = transactionService.get(1L);
        compare(t1, t4);

        final var t5 = transactionService.get(2L);
        compare(t2, t5);

        final var t6 = transactionService.get(3L);
        compare(t3, t6);
    }

    @Test
    void testRetrieveNotExists() {
        assertThatThrownBy(() -> transactionService.get(4L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("doesn't exists");
    }

    private void compare(Transaction t1, Transaction t2) {
        assertThat(t1).isNotNull();
        assertThat(t2).isNotNull();
        assertThat(t1.getAmount()).isEqualTo(t2.getAmount());
        assertThat(t1.getCard()).isEqualTo(t2.getCard());
        assertThat(t1.getCardholder()).isEqualTo(t2.getCardholder());
        assertThat(t1.getCurrency()).isEqualTo(t2.getCurrency());
        assertThat(t1.getInvoice()).isEqualTo(t2.getInvoice());
    }

}
