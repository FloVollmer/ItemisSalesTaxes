package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Receipt {

    private List<ReceiptItem> items;
    private float taxes;
    private float total;

    @Override
    public String toString() {
        return items.stream().map(item -> item.toString()).collect(Collectors.joining(System.lineSeparator()))
                + System.lineSeparator() + "Sales Taxes: " + String.format("%.2f", taxes)
                + System.lineSeparator() + "Total: " + String.format("%.2f", total);
    }

}
