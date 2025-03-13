package sg.nus.edu.iss.vttp_5a_final_project.model;

import java.time.LocalDate;

import jakarta.json.JsonObject;

public class Expense {

    // JSON key fields
    private static final String NAME="name";
    private static final String DATE="date";
    private static final String AMOUNT="amount";
    private static final String CATEGORY="category";
    private static final String DESCRIPTION="description";
    private static final String EMAIL="email";

    private String name;
    private LocalDate date;
    private double amount;
    private String category;
    private String description;
    private String email;

    public Expense() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static Expense convertJSONToExpense(JsonObject object){
        Expense expense = new Expense();
        expense.setName(object.getString(NAME));
        expense.setDate(LocalDate.parse(object.getString(DATE)));
        expense.setAmount(object.getJsonNumber(AMOUNT).doubleValue());
        expense.setCategory(object.getString(CATEGORY));
        expense.setDescription(object.getString(DESCRIPTION));
        expense.setEmail(object.getString(EMAIL));
        
        return expense;
    }
    
}
