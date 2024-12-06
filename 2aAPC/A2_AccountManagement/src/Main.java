import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;

        while (true) {
            System.out.println("Welche Aktion moechten Sie durchfuehren?");
            System.out.println("1 - Konto anlegen");
            System.out.println("2 - einzahlen");
            System.out.println("3 - abheben");
            System.out.println("4 - Kontoauszug");
            System.out.println("5 - Konto aufloesen");
            System.out.println("6 - Programm beenden");

            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (action) {
                case 1:
                    if (account == null) {
                        System.out.println("Welches Konto möchten Sie anlegen?");
                        System.out.println("1 - Girokonto (Current Account)");
                        System.out.println("2 - Sparkonto (Savings Account)");
                        System.out.println("3 - Kreditkonto (Credit Account)");

                        int accountType = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Bitte geben Sie den Kontoinhaber ein:");
                        String accountOwner = scanner.nextLine();

                        System.out.println("Bitte geben Sie die Bankleitzahl (BIC) ein:");
                        String BIC = scanner.nextLine();

                        System.out.println("Bitte geben Sie die Kontonummer (IBAN) ein:");
                        String IBAN = scanner.nextLine();

                        if (accountType == 1) {
                            System.out.println("Bitte geben Sie den Überziehungsrahmen ein:");
                            double overdraftLimit = scanner.nextDouble();
                            account = new CurrentAccount(accountOwner, BIC, IBAN, overdraftLimit);
                        } else if (accountType == 2) {
                            account = new SavingsAccount(accountOwner, BIC, IBAN);
                        } else if (accountType == 3) {
                            System.out.println("Bitte geben Sie den Kreditbetrag ein:");
                            double creditAmount = scanner.nextDouble();
                            account = new CreditAccount(accountOwner, BIC, IBAN, creditAmount);
                        }
                        System.out.println("Konto erfolgreich angelegt.");
                    } else {
                        System.out.println("Ein Konto ist bereits angelegt.");
                    }
                    break;

                case 2:
                    if (account != null) {
                        System.out.println("Bitte geben Sie den Einzahlungsbetrag ein:");
                        double depositAmount = scanner.nextDouble();
                        if (account.Deposit(depositAmount)) {
                            System.out.println("Einzahlung erfolgreich.");
                        } else {
                            System.out.println("Einzahlung fehlgeschlagen.");
                        }
                    } else {
                        System.out.println("Sie müssen zuerst ein Konto anlegen.");
                    }
                    break;

                case 3:
                    if (account != null) {
                        System.out.println("Bitte geben Sie den Abhebebetrag ein:");
                        double withdrawAmount = scanner.nextDouble();
                        if (account.Withdraw(withdrawAmount)) {
                            System.out.println("Abhebung erfolgreich.");
                        } else {
                            System.out.println("Abhebung fehlgeschlagen.");
                        }
                    } else {
                        System.out.println("Sie müssen zuerst ein Konto anlegen.");
                    }
                    break;

                case 4:
                    if (account != null) {
                        System.out.println("Kontoauszug");
                        System.out.println("Kontoinhaber: " + account.GetAccountOwner());
                        System.out.println("BLZ: " + account.GetBIC());
                        System.out.println("Kontonummer: " + account.GetIBAN());
                        System.out.println("Kontostand: " + account.GetAccountBalance());
                        System.out.println("Überziehungsrahmen: " + account.GetOverdraftLimit());
                        System.out.println("=============================");
                        System.out.println("Kontoauszug:");
                        System.out.println(account.GetHistory());
                    } else {
                        System.out.println("Sie müssen zuerst ein Konto anlegen.");
                    }
                    break;

                case 5:
                    if (account != null) {
                        System.out.println("Wollen Sie Ihr Konto wirklich auflösen? y / n");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("y")) {
                            account = null;
                            System.out.println("Konto wurde aufgelöst.");
                        }
                    } else {
                        System.out.println("Es ist kein Konto zum Auflösen vorhanden.");
                    }
                    break;

                case 6:
                    System.out.println("Programm beenden.");
                    return;

                default:
                    System.out.println("Ungültige Auswahl.");
            }
        }
    }
}
