package sg.nus.edu.iss.vttp_5a_final_project.model;

import java.util.UUID;

import jakarta.json.JsonObject;

public class Loan {
    // JSON fields
    private static final String AMOUNT="amount";
    private static final String DESCRIPTION="description";
    private static final String EMAIL="email";

    private String id;
    private double amount;
    private String description;
    private String email;

    public Loan() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
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

    public static Loan convertJSONToLoan(JsonObject object){
        Loan loan = new Loan();
        loan.setAmount(object.getJsonNumber(AMOUNT).doubleValue());
        loan.setDescription(object.getString(DESCRIPTION));
        loan.setEmail(object.getString(EMAIL));

        return loan;
    }
    
}
