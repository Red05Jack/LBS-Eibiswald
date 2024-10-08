import java.util.ArrayList;

public abstract class BankAccount {
    public BankAccount(
            String accountOwner,
            String BIC,
            String IBAN,
            String accountType
    ) {
       this.m_accountOwner = accountOwner;
       this.m_BIC = BIC;
       this.m_IBAN = IBAN;

       this.m_overdraftLimit = 0.0;
       this.m_accountMaintenanceFees = 0.0;
       this.m_accountBalance = 0.0;

       this.m_accountType = accountType;

       this.m_accountHistory = new ArrayList<String>();
    }

    // Getter & Setter
    public String GetAccountOwner() {
        return m_accountOwner;
    }

    public String GetBIC() {
        return m_BIC;
    }

    public String GetIBAN() {
        return m_IBAN;
    }

    public double GetOverdraftLimit() {
        return m_overdraftLimit;
    }

    public void SetOverdraftLimit(double m_overdraftLimit) {
        this.m_overdraftLimit = m_overdraftLimit;
    }

    public double GetAccountMaintenanceFees() {
        return m_accountMaintenanceFees;
    }

    public void SetAccountMaintenanceFees(double m_accountMaintenanceFees) {
        this.m_accountMaintenanceFees = m_accountMaintenanceFees;
    }

    public double GetAccountBalance() {
        return m_accountBalance;
    }

    public String GetAccountType() {
        return m_accountType;
    }

    // Public Member Methods
    public StringBuilder GetHistory() {
        StringBuilder history = new StringBuilder();

        for (String e : m_accountHistory) {
            history.append(e).append("\n");
        }

        return history;
    }

    public abstract Boolean Deposit(double amount);
    public abstract Boolean Withdraw(double amount);

    // Private Member Variables
    protected String m_accountOwner;
    protected String m_BIC;
    protected String m_IBAN;
    protected double m_overdraftLimit;
    protected double m_accountMaintenanceFees;
    protected double m_accountBalance;
    protected String m_accountType;

    protected ArrayList<String> m_accountHistory;
}
