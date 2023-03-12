import org.example.ReceiptItem;
import org.example.ReceiptService;
import org.junit.Test;

import static org.junit.Assert.*;


public class ReceiptServiceTest {

    private ReceiptService receiptService = new ReceiptService();

    @Test
    public void createFromString_validInput_nonNullReceiptItem() {
        ReceiptItem res = receiptService.createFromString("1 book at 12.49");
        assertNotNull(res);
    }

    @Test
    public void createFromString_validInput_validReceiptItem() {
        ReceiptItem res = receiptService.createFromString("1 book at 12.49");
        assertEquals(1, res.getQuantity());
        assertEquals("book", res.getName());
        assertEquals(12.49f, res.getPriceExclTax(), 0.001f);
        assertFalse(res.isImported());
        assertFalse(res.isExempted()); // No exemptions for imported products
    }

    @Test
    public void createFromString_validInput2_validReceiptItem() {
        ReceiptItem res = receiptService.createFromString("1 imported box of chocolates at 10.00");
        assertEquals(1, res.getQuantity());
        assertEquals("imported box of chocolates", res.getName());
        assertEquals(10f, res.getPriceExclTax(), 0.001f);
        assertTrue(res.isImported());
        assertTrue(res.isExempted());
    }

    @Test
    public void computeTaxes_ValidItem_validPrice() {
        ReceiptItem item = new ReceiptItem();
        item.setPriceExclTax(10f);

        receiptService.computeTaxes(item);
        assertEquals(1f, item.getTaxes(), 0.001f);

        item.setExempted(true);
        receiptService.computeTaxes(item);
        assertEquals(0f, item.getTaxes(), 0.001f);

        item.setImported(true);
        receiptService.computeTaxes(item);
        assertEquals(0.5f, item.getTaxes(), 0.001f);

        item.setExempted(false);
        receiptService.computeTaxes(item);
        assertEquals(0.5f, item.getTaxes(), 0.001f);
    }

}
