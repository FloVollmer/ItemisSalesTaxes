package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReceiptService {

    // List of known exempted products
    private static final String[] EXEMPTED_PRODUCTS = new String[]{"book", "chocolate", "pills"};
    private static final float NORMAL_RATE = 0.1f;
    private static final float IMPORTED_RATE = 0.05f;
    private static final float EXEMPTED_RATE = 0f;

    public Receipt createFromItems (List<ReceiptItem> items) {

        Receipt receipt = new Receipt();
        receipt.setItems(items);
        receipt.setTotal(items.stream().map(item -> item.getShelfPrice() * item.getQuantity()).reduce(0f, Float::sum));
        receipt.setTaxes(items.stream().map(item -> item.getTaxes() * item.getQuantity()).reduce(0f, Float::sum));

        return receipt;
    }

    public ReceiptItem createItemFromString(String str) {
        ReceiptItem item = new ReceiptItem();
        String[] strs = str.split(" ");
        int nbWords = strs.length;

        item.setQuantity(Integer.parseInt(strs[0]));

        String name = strs[1];
        for (int i=2; i<nbWords-2; ++i) {
            name += " " + strs[i];
        }
        item.setName(name);

        item.setImported(name.contains("imported"));

        if (item.isImported()) {
            for (String prod : EXEMPTED_PRODUCTS) {
                if (name.contains(prod)) {
                    item.setExempted(true);
                }
            }
        }

        item.setPriceExclTax(Float.parseFloat(strs[nbWords-1]));

        return item;
    }

    public void computeTaxes(ReceiptItem item) {

        float rate = item.isImported() ? IMPORTED_RATE :
                (item.isExempted() ? EXEMPTED_RATE : NORMAL_RATE);

        item.setTaxes(item.getPriceExclTax() * rate);
        item.setShelfPrice(item.getPriceExclTax() + item.getTaxes());
    }

}
