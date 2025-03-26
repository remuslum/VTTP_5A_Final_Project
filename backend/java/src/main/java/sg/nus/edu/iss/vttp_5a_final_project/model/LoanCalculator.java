package sg.nus.edu.iss.vttp_5a_final_project.model;

public class LoanCalculator {
    private int loanAmount;
    private double annualInterest;

    public LoanCalculator(int loanAmount, double annualInterest) {
        this.loanAmount = loanAmount;
        this.annualInterest = annualInterest;
    }
    public int getLoanAmount() {
        return loanAmount;
    }
    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }
    public double getAnnualInterest() {
        return annualInterest;
    }
    public void setAnnualInterest(double annualInterest) {
        this.annualInterest = annualInterest;
    }

    public double calculateLoanPayment(int duration, int frequencyOfPayment){
        double decimalInterest = this.annualInterest / 100.0;
        
        double periodicInterest = decimalInterest / frequencyOfPayment;
        
        int numOfPayments = duration * frequencyOfPayment;
        
        if (periodicInterest == 0) {
            return this.loanAmount / numOfPayments;
        }
        
        double compoundedInterest = Math.pow((1 + periodicInterest), numOfPayments);
        return (this.loanAmount * periodicInterest * compoundedInterest) / (compoundedInterest - 1);
    }

    public long calcuateNumberOfPayments(double paymentPerPeriod, int frequencyOfPayment){
        // Convert annual interest from percentage to decimal
        double decimalInterest = this.annualInterest / 100.0;
        
        // Calculate periodic interest rate
        double periodicInterest = decimalInterest / frequencyOfPayment;
        
        // Handle special cases
        if (periodicInterest == 0) {
            // Zero interest case - simple division
            return (int) Math.ceil(loanAmount / paymentPerPeriod);
        }
        
        // Verify valid inputs
        if (paymentPerPeriod <= loanAmount * periodicInterest) {
            throw new IllegalArgumentException("Payment per period is too small to cover interest");
        }
        
        // Calculate numerator and denominator
        double ratio = paymentPerPeriod / (paymentPerPeriod - loanAmount * periodicInterest);
        double numerator = Math.log(ratio);
        double denominator = Math.log(1 + periodicInterest);
        
        // Calculate and round up to ensure full repayment
        double exactPayments = numerator / denominator;
        return (int) Math.ceil(exactPayments);
    }
}
