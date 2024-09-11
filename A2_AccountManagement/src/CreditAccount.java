public class CreditAccount extends BankAccount {
    public CreditAccount(String accountOwner, String BIC, String IBAN, double accountBalance) {
        super(accountOwner, BIC, IBAN, "Credit Account");
        SetOverdraftLimit(accountBalance);
        m_accountBalance -= accountBalance;
        m_accountHistory.add("Withdraw: " + accountBalance + " \tnew balance: " + this.m_accountBalance);
    }

    // Public Member Methods
    @Override
    public Boolean Deposit(double amount) {
        if (this.m_accountBalance + amount <= 0.0) {
            this.m_accountBalance += amount;
            m_accountHistory.add("Deposit: " + amount + " \tnew balance: " + this.m_accountBalance);
            return true;
        }

        return false;
    }

    @Override
    public Boolean Withdraw(double amount) {
        return false;
    }
}
