package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiptItem {
    String name;
    int quantity;
    float priceExclTax;
    float taxes;
    boolean exempted;
    boolean imported;

}
