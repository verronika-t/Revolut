package bg.sofia.uni.fmi.mjt.revolut.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class BGNAccountTest {

    private Account bgnAccount;

    @Before
    public void inti() {
        this.bgnAccount = new BGNAccount("sjf1", 200);
    }

    @Test
    public void testConversionMoney() {
        double expected = 20 / 1.95583;
        Assert.assertEquals(expected, this.bgnAccount.conversionMoney(20), 1);
    }
}
