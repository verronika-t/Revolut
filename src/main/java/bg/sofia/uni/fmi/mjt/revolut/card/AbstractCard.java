package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractCard implements Card {
    private String number;
    private int pin;
    private LocalDate expirationDate;
    private boolean isBlocked;
    private int incorrectPin;

    public AbstractCard(String number, int pin, LocalDate expirationDate) {
        this.setNumber(number);
        this.setPin(pin);
        this.setExpirationDate(expirationDate);
        this.incorrectPin = 0;
    }

    protected void setNumber(String number) {
        this.number = number;
    }

    protected void setPin(int pin) {
        if (String.valueOf(pin).length() == 4) {
            this.pin = pin;
        }
    }

    protected void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    protected void setBlocked() {
        this.isBlocked = false;
    }


    @Override
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public boolean checkPin(int pin) {
        if (this.pin == pin) {
            this.incorrectPin = 0;
            return true;
        } else {
            this.incorrectPin++;
            if (this.incorrectPin == 3) {
                this.block();
            }
        }
        return false;
    }


    @Override
    public boolean isBlocked() {
        return this.isBlocked;
    }

    @Override
    public boolean isValid() {
        LocalDate expDate = this.getExpirationDate();
        LocalDate now = LocalDate.now();
            int cmp = (expDate.getYear() - now.getYear());
            if (cmp >= 0) {
                return true;
            } else {
                cmp = (expDate.getMonthValue() - now.getMonthValue());
                if (cmp >= 0) {
                    return true;
                } else {
                    cmp = (expDate.getDayOfMonth() - now.getDayOfMonth());
                    return cmp >= 0;
                }
            }
        }

    @Override
    public void block() {
        this.isBlocked = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCard that = (AbstractCard) o;
        return pin == that.pin && isBlocked == that.isBlocked && Objects.equals(number, that.number) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pin, expirationDate, isBlocked);
    }
}
