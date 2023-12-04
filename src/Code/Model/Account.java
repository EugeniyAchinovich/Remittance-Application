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
        if (this.accountBalance - amount < 0)
            throw new RemittanceException("недостаточный баланс");
        else if (amount < 0 && !chargeOff)
            throw new RemittanceException("неверно введена сумма");
        else
            this.accountBalance += amount;
    }

    @Override
    public String toString() {
        return "\nAccount: " + this.accountNumber + ", balance: " + this.accountBalance;
    }
}
