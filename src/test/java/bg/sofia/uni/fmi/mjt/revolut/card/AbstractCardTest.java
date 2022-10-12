package bg.sofia.uni.fmi.mjt.revolut.card;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;

public class AbstractCardTest {
    private PhysicalCard card;

    @Before
    public void init() {
        this.card = new PhysicalCard("12234", 1234, LocalDate.of(2028, 2, 8));
    }

    @Test
    public void TestSetPinIfLengthMoreThanFour() {
        this.card.setPin(12345);
        Assert.assertFalse(this.card.checkPin(12345));
    }

    @Test
    public void TestCardBlockedWhenThreeWrongPin() {
        this.card.checkPin(1);
        this.card.checkPin(2);
        this.card.checkPin(3);
        Assert.assertTrue(this.card.isBlocked());
    }

    @Test
    public void TestCardValid() {
        PhysicalCard notValid = new PhysicalCard("1324", 1234, LocalDate.of(2000, 5, 3));
        Assert.assertFalse(notValid.isValid());
    }


}
