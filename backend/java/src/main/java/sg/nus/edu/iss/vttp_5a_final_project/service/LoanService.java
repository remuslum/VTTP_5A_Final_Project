package sg.nus.edu.iss.vttp_5a_final_project.service;

import java.text.DecimalFormat;
import java.time.LocalDate;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_final_project.repository.LoanRepository;

@Service
public class LoanService {

    private final int NUMBER_OF_MONTHS_IN_A_QUARTER=3;
    
    @Autowired
    private LoanRepository loanRepository;

    public String getLoanAmount(int loanAmount, double annualInterest, int duration, String paymentType){
        double payment = 0.00;

        switch (paymentType) {
            case "Monthly" -> payment = loanRepository.getMonthlyPayments(loanAmount, annualInterest, duration);

            case "Quarterly" -> payment = loanRepository.getQuarterlyPayments(loanAmount, annualInterest, duration);

            case "Yearly" -> payment = loanRepository.getYearlyPayments(loanAmount, annualInterest, duration);
        }
        // Format to 2 decimal places
        DecimalFormat df = new DecimalFormat("##.00");
        Document d = new Document().append("payment", Double.valueOf(df.format(payment)));

        return d.toJson();
    }

    public String getNumberOfPeriods(int loanAmount, double annualInterest, double paymentPerPeriod, String paymentType){
        long numberOfPeriods = loanRepository.getNumberOfPeriods(loanAmount, annualInterest, paymentPerPeriod);
        LocalDate expectedRepaymentDate = LocalDate.now();

        switch (paymentType) {
            case "Monthly" -> expectedRepaymentDate = expectedRepaymentDate.plusMonths(numberOfPeriods);

            case "Quarterly" -> expectedRepaymentDate = expectedRepaymentDate.plusMonths(numberOfPeriods * NUMBER_OF_MONTHS_IN_A_QUARTER);

            case "Yearly" -> expectedRepaymentDate = expectedRepaymentDate.plusYears(numberOfPeriods);
        }

        Document d = new Document().append("date_of_last_repayment", expectedRepaymentDate.toString());
        return d.toJson(); 
    }
}
