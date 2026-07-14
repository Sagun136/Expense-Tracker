package expensetracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFileManager {

    private static final String FILE_NAME = "expenses.csv";

    public static void saveExpenses(List<Expense> expenses) {

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Expense e : expenses) {

                bw.write(e.toCSV());
                bw.newLine();

            }

            System.out.println("Expenses saved successfully.");

        }

        catch (IOException e) {

            System.out.println(e.getMessage());

        }

    }

    public static List<Expense> loadExpenses() {

        List<Expense> expenses = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            return expenses;

        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                expenses.add(Expense.fromCSV(line));

            }

        }

        catch (IOException e) {

            System.out.println(e.getMessage());

        }

        return expenses;

    }

}
