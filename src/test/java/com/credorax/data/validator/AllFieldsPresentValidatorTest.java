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
public class AllFieldsPresentValidatorTest {

    @Test
    void testValidate() {
        final var dtf = DateTimeFormatter.ofPattern("MMyy");
        final var localDateTime = LocalDateTime.now().plusMonths(6);
        final var expiry = dtf.format(localDateTime);

        final var name = "First Last";
        final var cardholder1 = new Cardholder(name, "email@domain.com");
        final var card1 = new Card("4188846122476411", expiry);
        final var t1 = new Transaction(1L, 100D, "EUR", cardholder1, card1);

        final var allFieldsPresentValidator = new AllFieldsPresentValidator(null);
        final var validator = new Validator();
        allFieldsPresentValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isTrue();
        assertThat(validator.getErrors()).isEmpty();
    }

    @Test
    void testValidateFailed() {
        final var cardholder1 = new Cardholder(null, null);
        final var card1 = new Card(null, null);
        final var t1 = new Transaction(null, null, null, cardholder1, card1);

        final var allFieldsPresentValidator = new AllFieldsPresentValidator(null);
        final var validator = new Validator();
        allFieldsPresentValidator.doValidate(t1, validator);
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
        assertThat(validator.getErrors()).containsKey("invoice");
        assertThat(validator.getErrors().get("invoice")).contains("required");
        assertThat(validator.getErrors()).containsKey("amount");
        assertThat(validator.getErrors().get("amount")).contains("required");
        assertThat(validator.getErrors()).containsKey("currency");
        assertThat(validator.getErrors().get("currency")).contains("required");
        assertThat(validator.getErrors()).containsKey("name");
        assertThat(validator.getErrors().get("name")).contains("required");
        assertThat(validator.getErrors()).containsKey("email");
        assertThat(validator.getErrors().get("email")).contains("required");
        assertThat(validator.getErrors()).containsKey("pan");
        assertThat(validator.getErrors().get("pan")).contains("required");
        assertThat(validator.getErrors()).containsKey("expiry");
        assertThat(validator.getErrors().get("expiry")).contains("required");
    }


}
