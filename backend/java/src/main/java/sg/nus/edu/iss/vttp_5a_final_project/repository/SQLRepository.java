package sg.nus.edu.iss.vttp_5a_final_project.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_final_project.model.Expense;
import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;
import sg.nus.edu.iss.vttp_5a_final_project.model.LoanPayment;

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

    public boolean insertExpenses(List<Expense> expenses){
        List<Object[]> params = expenses.stream().map(e -> new Object[]{e.getName(),e.getDate(),e.getAmount(),
            e.getCategory(),e.getDescription(),e.getEmail()}).collect(Collectors.toList());
        int added[] = jdbcTemplate.batchUpdate(INSERT_EXPENSE,params);

        return added.length == params.size();
    }

    public boolean insertLoans(List<Loan> loans){
        List<Object[]> params = loans.stream().map(l -> new Object[]{l.getId(),l.getAmount(),l.getDescription(),l.getEmail()})
        .collect(Collectors.toList());
        int added[] = jdbcTemplate.batchUpdate(INSERT_LOAN,params);
        
        return added.length == params.size();
    }

    public boolean insertLoanPayments(List<LoanPayment> loanPayments){
        List<Object[]> params = loanPayments.stream().map(p -> new Object[]{p.getAmount(),p.getDate(),p.getLoanId()})
        .collect(Collectors.toList());
        int added[] = jdbcTemplate.batchUpdate(INSERT_LOAN_PAYMENT,params);

        return added.length == params.size();
    }
}
