import org.example.Receipt;
import org.example.ReceiptItem;
import org.example.ReceiptService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ReceiptTest {


    @Test
    public void toString_validItem_validOutput() {
        Receipt res = new Receipt();
        res.setItems(new ArrayList<>());
        res.setTaxes(12.340001f);
        res.setTotal(234.56001f);
        String[] strs = res.toString().split(System.lineSeparator());
        assertEquals("Sales Taxes: 12.34", strs[1]);
        assertEquals("Total: 234.56", strs[2]);
    }

}
