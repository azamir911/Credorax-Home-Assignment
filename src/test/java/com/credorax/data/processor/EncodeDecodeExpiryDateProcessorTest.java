package com.credorax.data.processor;

import com.credorax.data.Card;
import com.credorax.data.Cardholder;
import com.credorax.data.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EncodeDecodeExpiryDateProcessorTest {

    @Test
    void testEncodeDecode() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().plusMonths(6);
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476411", expiry);
        final var t1 = new Transaction(1L, 100D, "EUR", cardholder1, card1);

        EncodeExpiryDateProcessor encodeExpiryDateProcessor = new EncodeExpiryDateProcessor(null);
        encodeExpiryDateProcessor.doProcess(t1);
        assertThat(t1.getCard().getExpiry()).isNotEqualTo(expiry);

        DecodeExpiryDateProcessor decodeExpiryDateProcessor = new DecodeExpiryDateProcessor(null);
        decodeExpiryDateProcessor.doProcess(t1);
        assertThat(t1.getCard().getExpiry()).isEqualTo("*".repeat(expiry.length()));
    }
}
