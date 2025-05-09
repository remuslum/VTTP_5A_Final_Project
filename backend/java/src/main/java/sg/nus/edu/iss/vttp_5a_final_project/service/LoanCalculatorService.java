package sg.nus.edu.iss.vttp_5a_final_project.service;

import java.text.DecimalFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.repository.LoanCalculatorRepository;

@Service
public class LoanCalculatorService {

    private final int NUMBER_OF_MONTHS_IN_A_QUARTER=3;
    
    @Autowired
    private LoanCalculatorRepository loanRepository;

    public String getLoanAmount(int loanAmount, double annualInterest, int duration, String paymentType){
        double payment = 0.00;

        switch (paymentType) {
            case "Monthly" -> payment = loanRepository.getMonthlyPayments(loanAmount, annualInterest, duration);

            case "Quarterly" -> payment = loanRepository.getQuarterlyPayments(loanAmount, annualInterest, duration);

            case "Yearly" -> payment = loanRepository.getYearlyPayments(loanAmount, annualInterest, duration);
        }
        // Format to 2 decimal places
        DecimalFormat df = new DecimalFormat("##.00");
        JsonObject object = Json.createObjectBuilder().add("payment", Double.parseDouble(df.format(payment))).build();

        return object.toString();
    }

    public String getNumberOfPeriods(int loanAmount, double annualInterest, double paymentPerPeriod, String paymentType){
        long numberOfPeriods = loanRepository.getNumberOfPeriods(loanAmount, annualInterest, paymentPerPeriod);
        LocalDate expectedRepaymentDate = LocalDate.now();

        switch (paymentType) {
            case "Monthly" -> expectedRepaymentDate = expectedRepaymentDate.plusMonths(numberOfPeriods);

            case "Quarterly" -> expectedRepaymentDate = expectedRepaymentDate.plusMonths(numberOfPeriods * NUMBER_OF_MONTHS_IN_A_QUARTER);

            case "Yearly" -> expectedRepaymentDate = expectedRepaymentDate.plusYears(numberOfPeriods);
        }

        JsonObject object = Json.createObjectBuilder().add("date_of_last_repayment", expectedRepaymentDate.toString()).build();
        return object.toString(); 
    }
}
