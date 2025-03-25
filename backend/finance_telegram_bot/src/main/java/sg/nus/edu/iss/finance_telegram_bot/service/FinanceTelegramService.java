package sg.nus.edu.iss.finance_telegram_bot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.finance_telegram_bot.repository.EmailRepository;
import sg.nus.edu.iss.finance_telegram_bot.repository.ExpenseRepository;
import sg.nus.edu.iss.finance_telegram_bot.repository.LoanPaymentRepository;
import sg.nus.edu.iss.finance_telegram_bot.repository.LoanRepository;

@Service
public class FinanceTelegramService {
   
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanPaymentRepository loanPaymentRepository;

    @Autowired
    private EmailRepository emailRepository;

    public String sendExpense(Map<String, String> fields){
        return expenseRepository.sendExpenseToBackend(fields).getBody();
    }

    public String getAllLoans(String email){
        return loanRepository.getAllLoans(email);
    }

    public String sendLoanPayment(Map<String, String> fields){
        return loanPaymentRepository.sendLoanPaymentToBackend(fields).getBody();
    }

    public ResponseEntity<String> checkEmail(String email){
        return emailRepository.checkEmail(email);
    }
}
