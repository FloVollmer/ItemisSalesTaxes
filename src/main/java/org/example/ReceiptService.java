package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReceiptService {

    // List of known exempted products
    private static final String[] EXEMPTED_PRODUCTS = new String[]{"book", "chocolate", "pills"};
    private static final float NORMAL_RATE = 0.1f;
    private static final float IMPORTED_RATE = 0.05f;

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

        for (String prod : EXEMPTED_PRODUCTS) {
            if (name.contains(prod)) {
                item.setExempted(true);
            }
        }

        item.setPriceExclTax(Float.parseFloat(strs[nbWords-1]));

        computeTaxes(item);

        return item;
    }

    public void computeTaxes(ReceiptItem item) {

        float rate = (item.isImported() ? IMPORTED_RATE : 0f) + (item.isExempted() ? 0f : NORMAL_RATE);

        float taxes = item.getPriceExclTax() * rate;
        taxes = (float) Math.ceil(taxes*20.0)/20f;

        item.setTaxes(taxes);
        item.setShelfPrice(item.getPriceExclTax() + taxes);
    }

    public static void main(String[] args) {

        ReceiptService service = new ReceiptService();
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println();
            System.out.println("Enter your products (press enter twice to finish)");
            List<ReceiptItem> items = new ArrayList<>();
            do {
                input = scanner.nextLine();
                if (!input.isBlank()) {
                    items.add(service.createItemFromString(input));
                }
            } while (!input.isBlank());
            System.out.println(service.createFromItems(items));

        } while (!input.equals("exit"));


    }

}
