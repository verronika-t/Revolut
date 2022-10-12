package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.Revolut;
import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.account.BGNAccount;
import bg.sofia.uni.fmi.mjt.revolut.account.EURAccount;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualPermanentCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;

public class RevolutTest {
    private Revolut revolut;
    private Card virtualCard;
    private Card permanentCard;
    private Card physicalCard;
    private Card notValidCard;

    private Account bgn;
    private Account eur;

    @Before
    public void init() {
        this.virtualCard = new VirtualOneTimeCard("12345", 1234, LocalDate.of(2025,10,7));
        this.permanentCard = new VirtualPermanentCard("14345", 1234, LocalDate.of(2025,11,7));
        this.physicalCard = new PhysicalCard("14345", 1234, LocalDate.of(2025,11,7));
        this.notValidCard = new PhysicalCard("14345", 1234, LocalDate.of(2020,11,7));

        bgn = new BGNAccount("dbwh1344", 200.20);
        eur = new EURAccount("fj1k34", 300.80);

        Account[] accounts = new Account[]{this.bgn, this.eur};
        Card[] cards = new Card[]{this.virtualCard, this.permanentCard, this.physicalCard, this.notValidCard};

        this.revolut = new Revolut(accounts, cards);
    }


    // pay method
    @Test
    public void testPayShouldReturnFalseIfNotPhysical() {
        boolean payed = this.revolut.pay(this.virtualCard, 1234, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayShouldReturnFalseIfInsufficientAmount() {
        boolean payed = this.revolut.pay(this.physicalCard, 1234, 20000, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayShouldReturnFalseIfIncorrectPin() {
        boolean payed = this.revolut.pay(this.physicalCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayShouldReturnFalseIfBlockedCard() {
        this.physicalCard.block();
        boolean payed = this.revolut.pay(this.physicalCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayShouldReturnFalseIfNotValid() {
        boolean payed = this.revolut.pay(this.notValidCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayShouldReturnFalseIfCardNotAvailableInRevolut() {
        Card card = new PhysicalCard("12345", 6578, LocalDate.of(2028, 2, 27));
        boolean payed = this.revolut.pay(card, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }



    @Test
    public void testPayShouldReturnTrue() {
        boolean payed = this.revolut.pay(this.physicalCard, 1234, 20, "BGN");
        Assert.assertTrue(payed);
    }


    //payOnline method
    @Test
    public void testPayOnlineShouldReturnFalseIfIncorrectDomain() {
        boolean payed = this.revolut.payOnline(this.permanentCard, 1234, 20, "BGN", "about.biz");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnFalseIfCardNotAvailableInRevolut() {
        Card card = new VirtualPermanentCard("12345", 6578, LocalDate.of(2028, 2, 27));
        boolean payed = this.revolut.pay(card, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnFalseIfNotValid() {
        boolean payed = this.revolut.pay(this.notValidCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnFalseIfBlockedCard() {
        this.physicalCard.block();
        boolean payed = this.revolut.pay(this.physicalCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnFalseIfIncorrectPin() {
        boolean payed = this.revolut.pay(this.physicalCard, 1235, 20, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnFalseIfInsufficientAmount() {
        boolean payed = this.revolut.pay(this.physicalCard, 1234, 20000, "BGN");
        Assert.assertFalse(payed);
    }

    @Test
    public void testPayOnlineShouldReturnTrue() {
        boolean payed = this.revolut.pay(this.physicalCard, 1234, 20, "BGN");
        Assert.assertTrue(payed);
    }


    //addMoney method

    @Test
    public void testAddMoneyShouldReturnTrue() {
        Assert.assertTrue(this.revolut.addMoney(this.bgn, 20));
    }

    @Test
    public void testAddMoneyShouldReturnFalseIfNotExistingAccount() {
        Account account = new BGNAccount("sdlw", 2);
        Assert.assertFalse(this.revolut.addMoney(account, 20));
    }


    //transferMoney method

    @Test
    public void testTransferMoneyShouldReturnTrue() {
        Assert.assertTrue(this.revolut.transferMoney(this.bgn, this.eur, 1));
    }

    @Test
    public void testTransferMoneyShouldReturnFalseIfNotExistingAccount() {
        Account account = new BGNAccount("sdlw", 2);
        Assert.assertFalse(this.revolut.transferMoney(account, this.bgn, 1));
    }

    @Test
    public void testTransferMoneyShouldReturnFalseIfInsufficientAmount() {
        Account account = new BGNAccount("sdlw", 2);
        Assert.assertFalse(this.revolut.transferMoney(account, this.bgn, 1000));
    }

    @Test
    public void testTransferMoneyShouldReturnFalseIfEqualsIbans() {
        Assert.assertFalse(this.revolut.transferMoney(this.bgn, this.bgn, 1000));
    }


    //totalAmount method

    @Test
    public void testGetTotalAmountShouldReturnCorrect() {
        double amount = this.bgn.getAmount() + (this.eur.getAmount() * 1.95583);
        Assert.assertEquals(this.revolut.getTotalAmount(), amount, 1);
    }

    //getAmountFromRightCurrency method

    @Test
    public void testGetAmountFromRightCurrencyShouldReturnTrue() {
        Assert.assertTrue(this.revolut.getAmountFromRightCurrency("BGN", 1));
    }

    @Test
    public void testGetAmountFromRightCurrencyShouldReturnFalseIfNotCorrectCurrency() {
        Assert.assertFalse(this.revolut.getAmountFromRightCurrency("LIR", 1));
    }





}
