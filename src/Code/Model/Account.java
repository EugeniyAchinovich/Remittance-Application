package Code.Model;

import Code.Exception.RemittanceException;

public class Account {
    private String accountNumber;
    private int accountBalance;

    public Account(String accountNumber, int accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void changeAccountBalance(int amount, boolean chargeOff) throws RemittanceException {
        String MSG_WRONG_AMOUNT = "неверно введена сумма";
        String MSG_WRONG_BALANCE = "недостаточный баланс";

        if (this.accountBalance - amount < 0)
            throw new RemittanceException(MSG_WRONG_BALANCE);
        else if (amount < 0 && !chargeOff)
            throw new RemittanceException(MSG_WRONG_AMOUNT);
        else
            this.accountBalance += amount;
    }

    @Override
    public String toString() {
        return "\nAccount: " + this.accountNumber + ", balance: " + this.accountBalance;
    }
}
