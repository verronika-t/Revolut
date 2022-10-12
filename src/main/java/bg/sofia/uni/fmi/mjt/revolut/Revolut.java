package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;

import java.util.Objects;

public class Revolut implements RevolutAPI {
    private Account[] accounts;
    private Card[] cards;

    public Revolut(Account[] accounts, Card[] cards) {
        this.accounts = accounts;
        this.cards = cards;
    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {
        if (this.checkIsCardExist(card, pin)) {
            if (!card.getType().equals("PHYSICAL")) {
                return false;
            }
            return this.getAmountFromRightCurrency(currency, amount);
        }
        return false;
    }


    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {
            if (shopURL.endsWith(".biz")) {
                return false;
            }

        if (this.checkIsCardExist(card, pin)) {
            boolean payed = this.getAmountFromRightCurrency(currency, amount);
            if (payed) {
                if (card.getType().equals("VIRTUALONETIME")) {
                    card.block();
                }
            }
            return payed;
        }
        return false;
    }



    @Override
    public boolean addMoney(Account account, double amount) {
        if (this.checkIfAccountExist(account)) {
            account.addMoney(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        if (this.checkIfAccountExist(from) && this.checkIfAccountExist(to)) {
            if (!from.getIBAN().equals(to.getIBAN())) {
                if (!from.getCurrency().equals(to.getCurrency())) {
                    if (from.sufficientAmount(amount))
                    from.getMoney(amount);
                    to.addMoney(from.conversionMoney(amount));
                } else {
                    from.getMoney(amount);
                    to.addMoney(amount);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public double getTotalAmount() {
        double totalSum = 0;
        for (Account account : accounts) {
            if (account.getCurrency().equals("BGN")) {
                totalSum += account.getAmount();
            } else {
                totalSum += account.getAmount() * 1.95583;
            }
        }
        return totalSum;
    }

    public boolean checkIsCardExist(Card card, int pin) {
        for (Card card1 : cards) {
            if (card1.equals(card)) {
                if (!card.isBlocked() && card.isValid() && card.checkPin(pin)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean getAmountFromRightCurrency(String currency, double amount) {
        for (Account account : accounts) {
            if (account.getCurrency().equals(currency) && account.sufficientAmount(amount)) {
                account.getMoney(amount);
                return true;
            }
        }
        return false;
    }

    public boolean checkIfAccountExist(Account account) {
        for (Account accountToCompare : accounts) {
            if (accountToCompare.equals(account)) {
                return true;
            }
        }
        return false;
    }

}
