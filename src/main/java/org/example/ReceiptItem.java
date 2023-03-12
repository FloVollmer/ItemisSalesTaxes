package org.example;

import lombok.Getter;

@Getter
public class ReceiptItem {
    String name;
    int quantity;
    float priceExclTax;
    float taxes;

}
