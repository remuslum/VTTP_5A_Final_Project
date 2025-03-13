package sg.nus.edu.iss.vttp_5a_final_project.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.model.Expense;
import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;
import sg.nus.edu.iss.vttp_5a_final_project.model.LoanPayment;
import sg.nus.edu.iss.vttp_5a_final_project.repository.SQLRepository;

@Service
public class SQLService {
    
    @Autowired
    private SQLRepository sqlRepository;

    public boolean insertExpenses(String payload){
        JsonArray arrayOfExpenses = Json.createReader(new StringReader(payload)).readArray();
        List<Expense> expenses = new ArrayList<>();
        
        for(int i = 0; i < arrayOfExpenses.size(); i++){
            JsonObject object = arrayOfExpenses.getJsonObject(i);
            expenses.add(Expense.convertJSONToExpense(object));
        }

        return sqlRepository.insertExpenses(expenses);
    }

    public boolean insertLoan(String payload){
        JsonArray arrayOfLoans = Json.createReader(new StringReader(payload)).readArray();
        List<Loan> loans = new ArrayList<>();

        for(int i = 0; i < arrayOfLoans.size(); i++){
            JsonObject object = arrayOfLoans.getJsonObject(i);
            loans.add(Loan.convertJSONToLoan(object));
        }

        return sqlRepository.insertLoans(loans);
    }

    public boolean insertLoanPayments(String payload){
        JsonArray arrayOfLoans = Json.createReader(new StringReader(payload)).readArray();
        List<LoanPayment> loanPayments = new ArrayList<>();

        for(int i = 0; i < arrayOfLoans.size(); i++){
            JsonObject object = arrayOfLoans.getJsonObject(i);
            loanPayments.add(LoanPayment.convertJSONToLoanPayment(object));
        }

        return sqlRepository.insertLoanPayments(loanPayments);
    }
}
