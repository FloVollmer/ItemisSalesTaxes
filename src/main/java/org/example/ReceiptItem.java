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
    float shelfPrice;
    boolean exempted;
    boolean imported;

    @Override
    public String toString() {
        return quantity + " " + name + ": " + String.format("%.2f", shelfPrice*quantity);
    }

}
