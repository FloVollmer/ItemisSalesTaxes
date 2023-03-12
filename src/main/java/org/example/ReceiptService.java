package org.example;


public class ReceiptService {

    // List of known exempted products
    private static final String[] EXEMPTED_PRODUCTS = new String[]{"book", "chocolate", "pills"};

    public ReceiptItem createFromString (String str) {
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

        return item;
    }



    public void computeTaxes () {

    }

}
