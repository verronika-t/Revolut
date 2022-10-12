package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.account.BGNAccount;
import bg.sofia.uni.fmi.mjt.revolut.account.EURAccount;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualPermanentCard;

import java.time.LocalDate;

public class main {

    public static void main(String[] args) {
        Card card1 = new VirtualOneTimeCard("12345", 1234, LocalDate.of(2025,10,7));
        Card card2 = new VirtualPermanentCard("14345", 1234, LocalDate.of(2025,11,7));
        Card card3 = new PhysicalCard("14345", 1234, LocalDate.of(2025,11,7));

        Card cardToCheck = new VirtualPermanentCard("123453", 1234, LocalDate.of(2025,10,7));

        Account bgn = new BGNAccount("dbwh1344", 200.20);
        Account eur = new EURAccount("fj1k34", 300.80);

        Account[] accounts = new Account[]{bgn, eur};
        Card[] cards = new Card[]{card1, card2, card3};

        Revolut revolut = new Revolut(accounts, cards);

        //false non exist card
        revolut.pay(cardToCheck, 1234, 25.60, "BGN");

        //false wrong pin
        revolut.pay(card1, 1233, 34, "BGN");
        revolut.pay(card1, 1233, 34, "BGN");


        //true and block the card after
        revolut.payOnline(card1, 1234, 56, "BGN", "djfld.bg");

        System.out.println();

    }
}
