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
public class PanValidatorTest {

    @Test
    void testValidateOK() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().plusMonths(6);
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476411", expiry);
        final var t1 = new Transaction(1L, 100D, "EUR", cardholder1, card1);

        final var panValidator = new PanValidator(null);
        final var validator = new Validator();
        panValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isTrue();
        assertThat(validator.getErrors()).isEmpty();
    }

    @Test
    void testValidateFailedInvalidPan() {
        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476412", "0624");
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var panValidator = new PanValidator(null);
        final var validator = new Validator();
        panValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("pan");
        assertThat(validator.getErrors().get("pan")).contains("Invalid");
    }

    @Test
    void testValidateFailedInvalidSize() {
        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("418884612247641", "0624");
        final var t1 = new Transaction(1L, 0D, "EUR", cardholder1, card1);

        final var panValidator = new PanValidator(null);
        final var validator = new Validator();
        panValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("pan");
        assertThat(validator.getErrors().get("pan")).contains("16 digits long");
    }


}
