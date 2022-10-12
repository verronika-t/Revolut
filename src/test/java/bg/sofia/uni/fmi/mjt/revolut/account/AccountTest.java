package bg.sofia.uni.fmi.mjt.revolut.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class AccountTest {
    Account eurAccount;
    Account bgnAccount;

    @Before
    public void init() {
        this.eurAccount = new BGNAccount("sjdk1", 200);
        this.bgnAccount = new EURAccount("skfl5", 100);
    }

    @Test
    public void testAddAmount() {
        double current = this.bgnAccount.getAmount();
        this.bgnAccount.addMoney(20);
        Assert.assertEquals(this.bgnAccount.getAmount(), current + 20, 1);
    }
}
