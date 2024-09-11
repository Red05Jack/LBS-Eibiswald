import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();

        bankAccounts.add(new CurrentAccount("V", "ABC", "0001"));
        bankAccounts.add(new SavingsAccount("Jackie", "ABC", "0002"));
        bankAccounts.add(new CreditAccount("Johnny", "ABC", "0003"));



    }
}
