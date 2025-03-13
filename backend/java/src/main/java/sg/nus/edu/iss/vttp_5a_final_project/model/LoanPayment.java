package sg.nus.edu.iss.vttp_5a_final_project.model;

import java.time.LocalDate;

import jakarta.json.JsonObject;

public class LoanPayment {
    // JSON fields
    private static final String AMOUNT="amount";
    private static final String DATE="date";
    private static final String LOAN_ID="loan_id";

    private double amount;
    private LocalDate date;
    private String loanId;

    public LoanPayment() {
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getLoanId() {
        return loanId;
    }
    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public static LoanPayment convertJSONToLoanPayment(JsonObject object){
        LoanPayment loanPayment = new LoanPayment();
        loanPayment.setAmount(object.getJsonNumber(AMOUNT).doubleValue());
        loanPayment.setDate(LocalDate.parse(object.getString(DATE)));
        loanPayment.setLoanId(object.getString(LOAN_ID));

        return loanPayment;
    }
}
