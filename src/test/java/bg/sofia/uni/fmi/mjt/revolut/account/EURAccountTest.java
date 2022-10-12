package bg.sofia.uni.fmi.mjt.revolut.account;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EURAccountTest {

    private Account eurAccount;

    @Before
    public void inti() {
        this.eurAccount = new EURAccount("sjf1", 200);
    }

    @Test
    public void testConversionMoney() {
        double expected = 20 * 1.95583;
        Assert.assertEquals(expected, this.eurAccount.conversionMoney(20), 1);
    }
}

