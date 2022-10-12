package bg.sofia.uni.fmi.mjt.revolut.account;

import java.util.Objects;

public abstract class Account {

    private double amount;
    private String IBAN;

    public Account(String IBAN) {
        this(IBAN, 0);
    }

    public Account(String IBAN, double amount) {
        this.IBAN = IBAN;
        this.amount = amount;
    }

    public abstract String getCurrency();

    //**
    // * conversion money from BGN to EUR
    // * or from EUR to BGN and return the amount
    // * @param amount the amount to transfer
    // * @return the amount
    public abstract double conversionMoney(double amount);

    public double getAmount() {
        return amount;
    }

    public void addMoney(double amount) {
        if (amount < 0) {
            return;
        }
        this.amount += amount;
    }

    public void getMoney(double amount) {
        if (this.getAmount() >= amount) {
            this.amount -= amount;
        }
    }

    public String getIBAN() {
        return this.IBAN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.amount, amount) == 0 && Objects.equals(IBAN, account.IBAN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, IBAN);
    }

    public boolean sufficientAmount(double amount) {
        return this.amount >= amount;
    }


}