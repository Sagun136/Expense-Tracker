package expensetracker;

import java.time.LocalDate;
import java.util.UUID;

public class Expense {

    private UUID id;
    private String description;
    private double amount;
    private Category category;
    private LocalDate date;

    public Expense(UUID id, String description, double amount,
                   Category category, LocalDate date) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toCSV() {
        return id + "," + description + "," +
                amount + "," + category + "," + date;
    }

    public static Expense fromCSV(String line) {

        String[] parts = line.split(",");

        UUID id = UUID.fromString(parts[0]);
        String description = parts[1];
        double amount = Double.parseDouble(parts[2]);
        Category category = Category.valueOf(parts[3]);
        LocalDate date = LocalDate.parse(parts[4]);

        return new Expense(id, description, amount, category, date);
    }
}