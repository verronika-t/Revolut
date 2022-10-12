package bg.sofia.uni.fmi.mjt.revolut.account;

public class EURAccount extends Account{

    public EURAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return "EUR";
    }

    @Override
    public double conversionMoney(double amount) {
        return amount * 1.95583;
    }
}
