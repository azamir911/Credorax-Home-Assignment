package com.credorax.data.validator;

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
public class ExpiryDataValidatorTest {

    @Test
    void testValidate() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().plusMonths(6);
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476411", expiry);
        final var t1 = new Transaction(1L, 100D, "EUR", cardholder1, card1);

        final var expiryDataValidator = new ExpiryDataValidator(null);
        final var validator = new Validator();
        expiryDataValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isTrue();
        assertThat(validator.getErrors()).isEmpty();
    }

    @Test
    void testValidateFailedNow() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now();
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476412", expiry);
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var expiryDataValidator = new ExpiryDataValidator(null);
        final var validator = new Validator();
        expiryDataValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("expiry");
        assertThat(validator.getErrors().get("expiry")).contains("is expired");
    }

    @Test
    void testValidateFailedBefore() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().minusMonths(6);
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476412", expiry);
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var expiryDataValidator = new ExpiryDataValidator(null);
        final var validator = new Validator();
        expiryDataValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("expiry");
        assertThat(validator.getErrors().get("expiry")).contains("is expired");
    }

    @Test
    void testValidateFailedNonExistsExpiry() {
        final var expiry = "1388";

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476412", expiry);
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var expiryDataValidator = new ExpiryDataValidator(null);
        final var validator = new Validator();
        expiryDataValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("expiry");
        assertThat(validator.getErrors().get("expiry")).contains("Invalid");
        assertThat(validator.getErrors().get("expiry")).contains("format");
    }

    @Test
    void testValidateFailedFormat() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().minusMonths(6);
        final var expiry = dtf.format(localDateTime) + "7";

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476412", expiry);
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var expiryDataValidator = new ExpiryDataValidator(null);
        final var validator = new Validator();
        expiryDataValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("expiry");
        assertThat(validator.getErrors().get("expiry")).contains("4 digits long");
    }

}
