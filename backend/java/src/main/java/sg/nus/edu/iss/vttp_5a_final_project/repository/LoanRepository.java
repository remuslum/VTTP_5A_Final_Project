package sg.nus.edu.iss.vttp_5a_final_project.repository;

import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;

@Repository
public class LoanRepository {

    private final int NUMBER_OF_PAYMENTS_MONTHLY=12;
    private final int NUMBER_OF_PAYMENTS_QUARTERLY=4;
    private final int NUMBER_OF_PAYMENTS_ANNUALLY=1;

    // Calculation of loan payment per period

    public double getMonthlyPayments(int loanAmount, double annualInterest,int duration){
        Loan loan = new Loan(loanAmount, annualInterest);
        return loan.calculateLoanPayment(duration, NUMBER_OF_PAYMENTS_MONTHLY);
    }

    public double getQuarterlyPayments(int loanAmount, double annualInterest, int duration){
        Loan loan = new Loan(loanAmount, annualInterest);
        return loan.calculateLoanPayment(duration, NUMBER_OF_PAYMENTS_QUARTERLY); 
    }

    public double getYearlyPayments(int loanAmount, double annualInterest, int duration){
        Loan loan = new Loan(loanAmount, annualInterest);
        return loan.calculateLoanPayment(duration, NUMBER_OF_PAYMENTS_ANNUALLY); 
    }

    // Calculation of number of periods
    public long getNumberOfPeriods(int loanAmount, double annualInterest, double paymentPerPeriod){
        Loan loan = new Loan(loanAmount, annualInterest);
        return loan.calcuateNumberOfPayments(paymentPerPeriod, loanAmount);
    }
 
}
