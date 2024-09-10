import java.sql.Driver;

public class Employee extends Person {
    public Employee(String lastName, String firstName, String supervisorID) {
        super(lastName, firstName);
        this.m_supervisorID = supervisorID;
    }

    // Public Member Methods
    public String GetID() {
        return this.m_supervisorID;
    }

    public void SetID(String supervisorID) {
        this.m_supervisorID = supervisorID;
    }

    public float GetSalary() {
        return this.m_salary;
    }

    public void SalaryIncrease(float salaryIncrease) {
        m_salary += salaryIncrease;
    }

    // Private Member Variables
    private String m_supervisorID;
    private float m_salary;
}
