package sg.nus.edu.iss.vttp_5a_final_project.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_final_project.model.Expense;
import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;
import sg.nus.edu.iss.vttp_5a_final_project.model.LoanPayment;
import sg.nus.edu.iss.vttp_5a_final_project.model.User;

@Repository
public class SQLRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_EXPENSE = 
    """
        INSERT INTO expenses (name, date, amount, category, description, email) VALUES (?,?,?,?,?,?);    
    """;

    private final String INSERT_LOAN = 
    """
        INSERT INTO loans (id, amount, description, email) VALUES (?,?,?,?);   
    """;

    private final String INSERT_LOAN_PAYMENT = 
    """
        INSERT INTO loan_payments (amount, date, loan_id) VALUES (?, ?, ?);        
    """;

    private final String UPDATE_LOAN_PAYMENT =
    """
        UPDATE loans SET amount = ? WHERE id = ?;        
    """;

    private final String SELECT_LOAN =
    """
        SELECT amount FROM loans WHERE id = ?;        
    """;

    private final String SELECT_EMAIL =
    """
        SELECT COUNT(*) as count FROM users WHERE email = ?;        
    """;
    private final String GET_LOANS =
    """
        SELECT id,amount,description FROM loans WHERE email = ?;         
    """;
    private final String GET_LOAN =
    """
        SELECT COUNT(*) from loans WHERE id = ?;        
    """;
    private final String GET_ALL_EXPENSES =
    """
        SELECT id,name,date,amount,category,description,email FROM expenses WHERE email = ?;
    """;
    private final String GET_ALL_LOAN_PAYMENTS =
    """
        SELECT p.id, p.amount, p.date, l.description FROM loan_payments p
        INNER JOIN loans l ON p.loan_id = l.id 
        WHERE l.email = ?;         
    """;
    private final String GET_CURRENT_SPENDING =
    """
        SELECT SUM(amount) AS total_spend FROM expenses
        WHERE YEAR(date) = ? AND MONTH(date) = ? AND email = ?
        GROUP BY YEAR(date),MONTH(date);              
    """;
    private final String GET_AVG_SPENDING_BY_CATEGORY_AND_MONTH =
    """
        SELECT AVG(amount) as avg_spending, MONTH(date) AS month, category FROM expenses
        WHERE YEAR(date) = ? AND email = ?
        GROUP BY category,MONTH(date)
        ORDER BY MONTH(date);     
    """;
    private final String GET_AVG_SPENDING_BY_MONTH_AND_YEAR =
    """
        SELECT AVG(amount) as avg_spending, YEAR(date) AS year, MONTH(date) AS month FROM expenses
        WHERE YEAR(date) = ? AND email = ?
        GROUP BY YEAR(date),MONTH(date)
        ORDER BY YEAR(date) DESC,MONTH(date);        
    """;
    private final String GET_HIGHEST_SPENDING =
    """
        SELECT MAX(amount) as spending, category FROM expenses
        WHERE email = ?
        GROUP BY category
        ORDER BY spending  DESC
        LIMIT 1;        
    """;
    private final String GET_SUM_LOAN_PAYMENTS = 
    """
        SELECT SUM(p.amount) AS total_sum, p.loan_id, l.amount, l.description FROM loan_payments p
        INNER JOIN loans l ON l.id = p.loan_id
        WHERE l.email = ?
        GROUP BY p.loan_id;        
    """;
    private final String GET_LATEST_LOAN_PAYMENT = 
    """
        SELECT p.amount, l.description, p.date FROM loan_payments p
        INNER JOIN loans l ON p.loan_id = l.id
        WHERE l.email = ?
        ORDER BY p.date DESC
        LIMIT 1;        
    """;
    private final String UPDATE_EXPENSE =
    """
        UPDATE expenses SET name = ?, date = ?, amount = ?, category = ?, description = ?, email = ? WHERE id = ?;        
    """;
    private final String INSERT_USER =
    """
        INSERT INTO users (email,first_name, last_name, dob) VALUES (?,?,?,?);        
    """;

    public boolean insertExpenses(List<Expense> expenses){
        List<Object[]> params = expenses.stream().map(e -> new Object[]{e.getName(),e.getDate(),e.getAmount(),
            e.getCategory(),e.getDescription(),e.getEmail()}).collect(Collectors.toList());
        int added[] = jdbcTemplate.batchUpdate(INSERT_EXPENSE,params);

        return added.length == params.size();
    }

    public boolean insertLoans(Loan loan){
        int added = jdbcTemplate.update(INSERT_LOAN,loan.getId(), loan.getAmount(), loan.getDescription(), loan.getEmail());
        return added > 0;
    }

    public boolean insertLoanPayments(List<LoanPayment> loanPayments){
        List<Object[]> params = loanPayments.stream().map(p -> new Object[]{p.getAmount(),p.getDate(),p.getLoanId()})
        .collect(Collectors.toList());
        int added[] = jdbcTemplate.batchUpdate(INSERT_LOAN_PAYMENT,params);

        return added.length == params.size();
    }

    public boolean updateLoan(List<LoanPayment> loanPayments){
        String loanId = loanPayments.get(0).getLoanId();
        Optional<Integer> loanAmount = Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_LOAN, Integer.class, loanId));
        loanAmount.map(loan -> {
            for(LoanPayment l:loanPayments){
                loan -= (int) l.getAmount();
            } 

            return jdbcTemplate.update(UPDATE_LOAN_PAYMENT, loan,loanId) > 0;
        }).orElseGet(() -> {
            return false;
        });
        return true;
    }

    public Optional<Integer> checkValidEmail(String email){
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_EMAIL, Integer.class,email));
    }

    public Optional<Integer> checkValidLoanId(String loanId){
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_LOAN, Integer.class,loanId));
    }

    public List<Loan> getAllLoans(String email) throws SQLException{
        return jdbcTemplate.query(GET_LOANS,(rs, rowNum) -> {
            return Loan.convertResultSetToLoan(rs);
        }, email);
    }

    public Optional<Integer> getCurrentSpending(int year, int month, String email){
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_CURRENT_SPENDING,  Integer.class,year, month, email));
    }

    public JsonArray getSpendingByCategory(int year, String email) throws SQLException{
        List<JsonObject> spendingList = jdbcTemplate.query(GET_AVG_SPENDING_BY_CATEGORY_AND_MONTH, (rs, rowNum) -> {
            JsonObjectBuilder object = Json.createObjectBuilder();
            object.add(rs.getString("category"),rs.getDouble("avg_spending"));
            object.add("month",rs.getInt("month"));
        return object.build();
        },year,email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        spendingList.forEach(s -> arrayBuilder.add(s));
        return arrayBuilder.build();
    }

    public JsonArray getSpendingByMonthAndYear(int year, String email) throws SQLException{
        List<JsonObject> spendingList = jdbcTemplate.query(GET_AVG_SPENDING_BY_MONTH_AND_YEAR, (rs, rowNum) -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("spending", rs.getDouble("avg_spending"));
            builder.add("month",rs.getInt("month"));
            return builder.build();
        },year,email);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        spendingList.forEach(s -> arrayBuilder.add(s));
        return arrayBuilder.build(); 
    }

    public JsonObject getMaxSpending(String email) throws SQLException {
        return jdbcTemplate.queryForObject(
        GET_HIGHEST_SPENDING, (rs, rowNum) -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("category", rs.getString("category"));
            builder.add("spending", rs.getDouble("spending"));
            return builder.build();
        },email);
    }

    public List<Expense> getAllExpenses(String email) throws SQLException {
        return jdbcTemplate.query(GET_ALL_EXPENSES, (rs, rowNum) -> {
            return Expense.convertRsToExpense(rs);
        },email);
    }

    public List<JsonObject> getAllLoanPayments(String email) throws SQLException {
        return jdbcTemplate.query(GET_SUM_LOAN_PAYMENTS, (rs, rowNum) -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("loan_id",rs.getString("loan_id"));
            builder.add("amount",rs.getDouble("amount"));
            builder.add("total_sum",rs.getDouble("total_sum"));
            builder.add("description",rs.getString("description"));
            return builder.build();
        },email);
    }

    public JsonObject getLatestLoanPayment(String email) throws SQLException {
        return jdbcTemplate.queryForObject(GET_LATEST_LOAN_PAYMENT, (rs, rowNum) -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("amount", rs.getDouble("amount"));
            builder.add("description", rs.getString("description"));
            builder.add("date", rs.getString("date"));
            return builder.build();
        },email);
    }

    public List<JsonObject> getAllLoanPaymentsTable(String email) throws SQLException{
        return jdbcTemplate.query(GET_ALL_LOAN_PAYMENTS, (rs, rowNum) -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("id",rs.getInt("id"));
            builder.add("amount",rs.getDouble("amount"));
            builder.add("date",rs.getString("date"));
            builder.add("description",rs.getString("description"));
            return builder.build();
        }, email);
    }

    public boolean updateExpense(Expense expense) throws DataAccessException{
        return jdbcTemplate.update(UPDATE_EXPENSE, expense.getName(), expense.getDate(), expense.getAmount(), expense.getCategory()
        , expense.getDescription(), expense.getEmail(), expense.getId()) > 0;
    }

    public boolean insertUser(User user){
        return jdbcTemplate.update(INSERT_USER, user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateofBirth().toString()) > 0;
    }
    
}
