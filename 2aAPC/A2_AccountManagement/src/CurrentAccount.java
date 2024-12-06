public class CurrentAccount extends BankAccount {
    public CurrentAccount(String accountOwner, String BIC, String IBAN, double overdraftLimit) {
        super(accountOwner, BIC, IBAN, "Current Account");
        SetOverdraftLimit(overdraftLimit);
    }

    // Public Member Methods
    @Override
    public Boolean Deposit(double amount) {
        this.m_accountBalance += amount;
        m_accountHistory.add("Deposit: " + amount + " \tnew balance: " + this.m_accountBalance);
        return true;
    }

    @Override
    public Boolean Withdraw(double amount) {
        if (this.m_accountBalance >= amount) {
            this.m_accountBalance -= amount;
            m_accountHistory.add("Withdraw: " + amount + " \tnew balance: " + this.m_accountBalance);
            return true;
        }

        if ((this.m_accountBalance + this.m_overdraftLimit) >= amount) {
            this.m_accountBalance -= amount;
            m_accountHistory.add("Withdraw: " + amount + " \tnew balance: " + this.m_accountBalance);
            return true;
        }

        return false;
    }
}
