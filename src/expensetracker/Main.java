package expensetracker;

import java.time.LocalDate;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static List<Expense> expenses = ExpenseFileManager.loadExpenses();

    public static void main(String[] args) {

        while (true) {
            menu();
        }

    }

    // ================= MENU =================

    static void menu() {

        System.out.println("\n===== Expense Tracker =====");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. Monthly Summary");
        System.out.println("5. Exit");

        System.out.print("Enter Choice : ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                addExpense();
                break;

            case 2:
                viewExpenses();
                break;

            case 3:
                deleteExpense();
                break;

            case 4:
                monthlySummary();
                break;

            case 5:
                System.out.println("Thank You!");
                sc.close();
                System.exit(0);

            default:
                System.out.println("Invalid Choice!");

        }

    }

    // ================= ADD EXPENSE =================

    static void addExpense() {

        System.out.print("Enter Description : ");
        String description = sc.nextLine();

        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();
        sc.nextLine();

        try {

            if (amount <= 0) {
                throw new InvalidAmountException(
                        "Amount must be greater than zero.");
            }

            System.out.print("Enter Category (FOOD/TRAVEL/BILLS/ENTERTAINMENT/OTHER): ");
            Category category =
                    Category.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Enter Date (yyyy-MM-dd): ");
            LocalDate date =
                    LocalDate.parse(sc.nextLine());

            Expense expense = new Expense(
                    UUID.randomUUID(),
                    description,
                    amount,
                    category,
                    date
            );

            expenses.add(expense);

            ExpenseFileManager.saveExpenses(expenses);

            System.out.println("Expense Added Successfully.");

        }

        catch (InvalidAmountException e) {

            System.out.println(e.getMessage());

        }

        catch (IllegalArgumentException e) {

            System.out.println("Invalid Category or Date.");

        }

    }

    // ================= VIEW EXPENSES =================

    static void viewExpenses() {

        if (expenses.isEmpty()) {

            System.out.println("No expenses found.");
            return;

        }

        System.out.println("\n===== All Expenses =====");

        for (Expense e : expenses) {

            System.out.println("ID          : " + e.getId());
            System.out.println("Description : " + e.getDescription());
            System.out.println("Amount      : " + e.getAmount());
            System.out.println("Category    : " + e.getCategory());
            System.out.println("Date        : " + e.getDate());

            System.out.println("--------------------------------------");

        }

    }

    // ================= DELETE EXPENSE =================

    static void deleteExpense() {

        if (expenses.isEmpty()) {

            System.out.println("No expenses found.");
            return;

        }

        viewExpenses();

        System.out.print("Enter Expense ID to Delete : ");
        String id = sc.nextLine();

        Expense expenseToDelete = null;

        for (Expense e : expenses) {

            if (e.getId().toString().equals(id)) {

                expenseToDelete = e;
                break;

            }

        }

        if (expenseToDelete != null) {

            expenses.remove(expenseToDelete);

            ExpenseFileManager.saveExpenses(expenses);

            System.out.println("Expense Deleted Successfully.");

        }

        else {

            System.out.println("Expense Not Found.");

        }

    }

    // ================= MONTHLY SUMMARY =================

    static void monthlySummary() {

        if (expenses.isEmpty()) {

            System.out.println("No expenses found.");
            return;

        }

        HashMap<Category, Double> summary = new HashMap<>();

        for (Expense e : expenses) {

            double total =
                    summary.getOrDefault(
                            e.getCategory(),
                            0.0
                    );

            summary.put(
                    e.getCategory(),
                    total + e.getAmount()
            );

        }

        System.out.println("\n===== Monthly Summary =====");

        System.out.printf("%-20s%s%n", "Category", "Total");
        System.out.println("--------------------------------");

        for (Category c : summary.keySet()) {

            Pair<Category, Double> pair =
                    new Pair<>(c, summary.get(c));

            System.out.println(pair);

        }

    }

}