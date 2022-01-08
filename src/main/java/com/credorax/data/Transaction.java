package com.credorax.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    Long invoice;
    Double amount;
    String currency;
    Cardholder cardholder;
    Card card;
}
