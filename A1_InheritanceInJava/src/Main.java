import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<Employee>();

        employees.add(new Employee("Wick", "John", "000000"));
        employees.add(new Employee("Tarasov", "Viggo", "000001"));

        for (Employee employee : employees) {
            System.out.println(employee.GetFirstname() + " " + employee.GetLastname());
        }
    }
}
