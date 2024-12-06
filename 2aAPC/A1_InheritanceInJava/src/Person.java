public class Person {
    public Person(String lastname, String firstname) {
        this.m_lastname = lastname;
        this.m_firstname = firstname;
    }

    // Public Member Methods
    public String GetLastname() {
        return this.m_lastname;
    }

    public void SetLastname(String lastName) {
        this.m_lastname = lastName;
    }

    public String GetFirstname() {
        return this.m_firstname;
    }

    public void SetFirstname(String firstname) {
        this.m_firstname = firstname;
    }

    public String GetFullName() {
        return this.m_lastname + " " + this.m_firstname;
    }

    public int GetAge() {
        return this.m_age;
    }

    public void SetAge(int age) {
        this.m_age = age;
    }

    // Private Member Variables
    private String m_lastname;
    private String m_firstname;
    private int m_age;
}
