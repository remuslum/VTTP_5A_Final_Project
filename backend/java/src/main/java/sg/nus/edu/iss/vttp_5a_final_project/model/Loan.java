package sg.nus.edu.iss.vttp_5a_final_project.model;

public class Loan {
    private int loanAmount;
    private double annualInterest;

    public Loan(int loanAmount, double annualInterest) {
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
        double paymentPerInterest = this.annualInterest/frequencyOfPayment;
        int numOfPayments = duration * frequencyOfPayment;

        /* Formula: 
        *                     ((principal) * (monthly interest) * (1 + monthly interest)**n)
        *         amount =    --------------------------------------------------------------
        *                                    (1 + monthly interest)**n - 1 
        */
        
        double compoundedInterest = Math.pow((1 + paymentPerInterest),numOfPayments);
        double numerator = loanAmount * paymentPerInterest * compoundedInterest;
        double denominator = compoundedInterest - 1;

        return numerator/denominator;
    }

    public long calcuateNumberOfPayments(double paymentPerPeriod, int frequencyOfPayment){
        /* Formula: 
         *              log(paymentPerPeriod/(paymentPerPeriod - (principal * monthly interest)))
         *  duration =  -------------------------------------------------------------------------
         *                                   log(1 + monthly interest)
         */

        
        double interestPerPayment = this.annualInterest/frequencyOfPayment;
        
        // Calculation of numerator
        double numerator = Math.log(paymentPerPeriod / (paymentPerPeriod - (loanAmount * interestPerPayment)));
        
        // Calculation of denominator
        double denominator = Math.log(1 + interestPerPayment);

        return Math.round(numerator/denominator);
    }
}
