import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Employee> employeeMap = new HashMap<>();
        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add Employee");
            System.out.println("2. Modify Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Print Employee");
            System.out.println("5. Print all Employee");

            String command = userInput.nextLine();

            String lastname;
            String firstname;
            String id;
            String age;
            String salary;

            switch (command) {
                case "0":
                    System.out.println("Goodbye!");
                    return;
                case "1":
                    System.out.print("Lastname: ");
                    lastname = userInput.nextLine();

                    System.out.print("Firstname: ");
                    firstname = userInput.nextLine();

                    System.out.print("ID: ");
                    id = userInput.nextLine();

                    employeeMap.put(id, new Employee(lastname, firstname, id));

                    System.out.print("Age: ");
                    age = userInput.nextLine();
                    employeeMap.get(id).SetAge(Integer.parseInt(age));

                    System.out.print("Salary: ");
                    salary = userInput.nextLine();
                    employeeMap.get(id).SalaryIncrease(Float.parseFloat(salary));

                    break;
                case "2":
                    System.out.print("ID: ");
                    id = userInput.nextLine();

                    if (employeeMap.get(id) == null) {
                        System.out.println("Employee not found!");
                        break;
                    }

                    System.out.println("Employee found, press empty ENTER to skip a input.");

                    System.out.print("Lastname: ");
                    lastname = userInput.nextLine();

                    System.out.print("Firstname: ");
                    firstname = userInput.nextLine();

                    System.out.print("Age: ");
                    age = userInput.nextLine();

                    System.out.print("Salary to add: ");
                    salary = userInput.nextLine();

                    if (!lastname.isEmpty())
                        employeeMap.get(id).SetLastname(lastname);

                    if (!firstname.isEmpty())
                        employeeMap.get(id).SetFirstname(firstname);

                    if (!age.isEmpty())
                        employeeMap.get(id).SetAge(Integer.parseInt(age));

                    if (!salary.isEmpty())
                        employeeMap.get(id).SalaryIncrease(Float.parseFloat(salary));

                    break;
                case "3":
                    System.out.print("ID: ");
                    id = userInput.nextLine();

                    if (employeeMap.get(id) == null) {
                        System.out.println("Employee not found!");
                        break;
                    }

                    System.out.println("Do you really want to delete employee " + employeeMap.get(id).GetLastname() + "?");
                    System.out.println("YES/NO");

                    if (Objects.equals(userInput.nextLine(), "YES")) {
                        employeeMap.remove(id);
                        System.out.println("Employee was deleted!");
                    } else {
                        System.out.println("Employee was not deleted!");
                    }

                    break;
                case "4":
                    System.out.print("ID: ");
                    id = userInput.nextLine();

                    if (employeeMap.get(id) == null) {
                        System.out.println("Employee not found!");
                        break;
                    }

                    System.out.println("ID: " + id);
                    System.out.println("Name:" + employeeMap.get(id).GetFullName());
                    System.out.println("Age: " + employeeMap.get(id).GetAge());
                    System.out.println("Salary: " + employeeMap.get(id).GetSalary());

                    break;
                case "5":
                    for (String key : employeeMap.keySet()) {
                        System.out.print(employeeMap.get(key).GetID() + "\t");
                        System.out.print(employeeMap.get(key).GetLastname() + "\t");
                        System.out.print(employeeMap.get(key).GetFirstname() + "\t");
                        System.out.print(employeeMap.get(key).GetAge() + "\t");
                        System.out.print(employeeMap.get(key).GetSalary() + "\n");
                    }

                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
}
