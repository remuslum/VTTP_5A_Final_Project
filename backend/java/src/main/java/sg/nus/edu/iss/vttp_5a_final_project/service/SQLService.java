package sg.nus.edu.iss.vttp_5a_final_project.service;

import java.io.StringReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_final_project.model.Expense;
import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;
import sg.nus.edu.iss.vttp_5a_final_project.model.LoanPayment;
import sg.nus.edu.iss.vttp_5a_final_project.model.User;
import sg.nus.edu.iss.vttp_5a_final_project.repository.FireStoreRepository;
import sg.nus.edu.iss.vttp_5a_final_project.repository.SQLRepository;

@Service
public class SQLService {
    
    @Autowired
    private SQLRepository sqlRepository;

    @Autowired
    private FireStoreRepository fireStoreRepository;

    @Transactional
    public boolean insertExpenses(String payload) throws ExecutionException, InterruptedException{
        // [{"name":"a","date":"2025-03-10","amount":100,"category":"needs","description":"aa"}]
        JsonArray arrayOfExpenses = Json.createReader(new StringReader(payload)).readArray();
        List<Expense> expenses = new ArrayList<>();
        
        for(int i = 0; i < arrayOfExpenses.size(); i++){
            JsonObject object = arrayOfExpenses.getJsonObject(i);
            expenses.add(Expense.convertJSONToExpense(object));
        }

        return sqlRepository.insertExpenses(expenses) && fireStoreRepository.addExpensesIntoCollection(expenses);
    }

    public boolean insertLoan(String payload) throws ExecutionException, InterruptedException {
        JsonObject loanObject = Json.createReader(new StringReader(payload)).readObject();
        Loan loan = Loan.convertJSONToLoan(loanObject);
        return sqlRepository.insertLoans(loan) && fireStoreRepository.addLoansIntoCollection(loan);

    }

    @Transactional
    public boolean insertLoanPayments(String payload){
        JsonArray arrayOfLoans = Json.createReader(new StringReader(payload)).readArray();
        List<LoanPayment> loanPayments = new ArrayList<>();

        for(int i = 0; i < arrayOfLoans.size(); i++){
            JsonObject object = arrayOfLoans.getJsonObject(i);
            loanPayments.add(LoanPayment.convertJSONToLoanPayment(object));
        }
        
        return sqlRepository.insertLoanPayments(loanPayments) && sqlRepository.updateLoan(loanPayments);
    }

    public JsonArray getAllLoans(String email) throws SQLException{
        List<Loan> loansList = sqlRepository.getAllLoans(email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        loansList.forEach(loan -> {
            JsonObject object = Json.createObjectBuilder().add("id",loan.getId()).add("amount",loan.getAmount()).add("description",loan.getDescription()).build();
            arrayBuilder.add(object);
        });
        return arrayBuilder.build();
    }

    public boolean checkValidEmail(String email){
        Optional<Integer> emailCount = sqlRepository.checkValidEmail(email);
        return emailCount.isPresent();
    }

    public boolean checkValidLoanId(String loanId){
        Optional<Integer> loanCount = sqlRepository.checkValidLoanId(loanId);
        return loanCount.isPresent();
    }

    public Optional<Integer> getCurrentSpending(String email){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        return sqlRepository.getCurrentSpending(year, month, email);
    }

    public String getSpendingByCategory(String email) throws SQLException{
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        return sqlRepository.getSpendingByCategory(year, email).toString();
    }

    public String getSpendingByMonthAndYear(String email) throws SQLException{
        int year = LocalDate.now().getYear();
        return sqlRepository.getSpendingByMonthAndYear(year, email).toString();
    }

    public String getMaxSpending(String email) throws SQLException {
        return sqlRepository.getMaxSpending(email).toString();
    }

    public JsonArray getAllExpenses(String email) throws SQLException{
        List<Expense> expenses = sqlRepository.getAllExpenses(email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        expenses.forEach(e -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add(Expense.ID, e.getId());
            builder.add(Expense.NAME, e.getName());
            builder.add(Expense.DATE, e.getDate().toString());
            builder.add(Expense.AMOUNT, e.getAmount());
            builder.add(Expense.CATEGORY, e.getCategory());
            builder.add(Expense.DESCRIPTION, e.getDescription());
            builder.add(Expense.EMAIL, e.getEmail());
            
            arrayBuilder.add(builder.build());
        });

        return arrayBuilder.build();
    }

    public JsonArray getSumOfLoanPayments(String email) throws SQLException {
        List<JsonObject> loanPayments = sqlRepository.getAllLoanPayments(email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        loanPayments.forEach(l -> arrayBuilder.add(l));
        return arrayBuilder.build();
    }

    public String getLatestLoanPayment(String email) throws SQLException {
        return sqlRepository.getLatestLoanPayment(email).toString();
    }

    public JsonArray getLatestLoanPaymentsTable(String email) throws SQLException {
        List<JsonObject> loanPayments = sqlRepository.getAllLoanPaymentsTable(email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        loanPayments.forEach(p -> arrayBuilder.add(p));
        return arrayBuilder.build();
    }

    public boolean updateExpense(String payload) throws DataAccessException{
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        Expense expense = Expense.convertJSONToExpense(object);
        expense.setId(object.getInt(Expense.ID));
        return sqlRepository.updateExpense(expense);
    }

    public boolean insertUser(User user){
        return sqlRepository.insertUser(user);
    }
}
