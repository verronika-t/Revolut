package bg.sofia.uni.fmi.mjt.revolut.account;

public class BGNAccount extends Account{

    public BGNAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return "BGN";
    }

    @Override
    public double conversionMoney(double amount) {
        return amount / 1.95583;
    }
}
