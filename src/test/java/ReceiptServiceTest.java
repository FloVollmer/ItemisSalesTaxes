import lombok.SneakyThrows;
import org.example.Receipt;
import org.example.ReceiptItem;
import org.example.ReceiptParsingException;
import org.example.ReceiptService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;


public class ReceiptServiceTest {

    private ReceiptService receiptService = new ReceiptService();

    @Test
    public void createFromItems_ValidItems_ValidTotals() {
        ReceiptItem item1 = new ReceiptItem();
        item1.setQuantity(1);
        item1.setName("item1");
        item1.setTaxes(5f);
        item1.setShelfPrice(20f);

        ReceiptItem item2 = new ReceiptItem();
        item2.setQuantity(2);
        item2.setName("item2");
        item2.setTaxes(3.5f);
        item2.setShelfPrice(15f);

        Receipt res = receiptService.createFromItems(Arrays.asList(item1, item2));

        assertEquals(12f, res.getTaxes(), 0.001f);
        assertEquals(50f, res.getTotal(), 0.001f);

    }

    @SneakyThrows
    @Test
    public void createItemFromString_validInput_nonNullReceiptItem() {
        ReceiptItem res = receiptService.createItemFromString("1 book at 12.49");
        assertNotNull(res);
    }

    @SneakyThrows
    @Test
    public void createItemFromString_validInput_validReceiptItem() {
        ReceiptItem res = receiptService.createItemFromString("1 book at 12.49");
        assertEquals(1, res.getQuantity());
        assertEquals("book", res.getName());
        assertEquals(12.49f, res.getPriceExclTax(), 0.001f);
        assertFalse(res.isImported());
        assertTrue(res.isExempted());
    }

    @SneakyThrows
    @Test
    public void createFromString_validInput2_validReceiptItem() {
        ReceiptItem res = receiptService.createItemFromString("1 imported box of chocolates at 10.00");
        assertEquals(1, res.getQuantity());
        assertEquals("imported box of chocolates", res.getName());
        assertEquals(10f, res.getPriceExclTax(), 0.001f);
        assertTrue(res.isImported());
        assertTrue(res.isExempted());
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @SneakyThrows
    @Test
    public void createItemFromString_typo1_ThrowsException() {
        exceptionRule.expect(ReceiptParsingException.class);
        exceptionRule.expectMessage(ReceiptParsingException.GENERAL_MESSAGE);
        receiptService.createItemFromString("1 bookat 10.00");
    }
    @SneakyThrows
    @Test
    public void createItemFromString_typo2_ThrowsException() {
        exceptionRule.expect(ReceiptParsingException.class);
        exceptionRule.expectMessage(ReceiptParsingException.GENERAL_MESSAGE);
        receiptService.createItemFromString("1 book zt 10.00");
    }
    @SneakyThrows
    @Test
    public void createItemFromString_invalidQuantityThrowsException2() {
        exceptionRule.expect(ReceiptParsingException.class);
        exceptionRule.expectMessage(ReceiptParsingException.QUANTITY_MESSAGE);
        receiptService.createItemFromString("One book at 10.00");
    }
    @SneakyThrows
    @Test
    public void createItemFromString_invalidPrice_ThrowsException() {
        exceptionRule.expect(ReceiptParsingException.class);
        exceptionRule.expectMessage(ReceiptParsingException.EXCLTAX_MESSAGE);
        receiptService.createItemFromString("1 book at 10.00001");
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
        assertEquals(1.5f, item.getTaxes(), 0.001f);
    }

}
