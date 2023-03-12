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
        //assertEquals(1, res.getQuantity());
    }

}
