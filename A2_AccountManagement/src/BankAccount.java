public class BankAccount {
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
    void ChangeBalance(double balance) {
        this.m_accountBalance += balance;
    }

    // Private Member Variables
    private String m_accountOwner;
    private String m_BIC;
    private String m_IBAN;
    private double m_overdraftLimit;
    private double m_accountMaintenanceFees;
    private double m_accountBalance;
    private String m_accountType;
}
