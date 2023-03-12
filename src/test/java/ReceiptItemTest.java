import org.example.ReceiptItem;
import org.example.ReceiptService;
import org.junit.Test;

import static org.junit.Assert.*;


public class ReceiptItemTest {

    private ReceiptService receiptService = new ReceiptService();

    @Test
    public void toString_validItem_validOutput() {
        ReceiptItem res = new ReceiptItem();
        res.setQuantity(1);
        res.setName("music CD");
        res.setShelfPrice(16.49001f);
        assertEquals("1 music CD: 16.49", res.toString());
    }

}
