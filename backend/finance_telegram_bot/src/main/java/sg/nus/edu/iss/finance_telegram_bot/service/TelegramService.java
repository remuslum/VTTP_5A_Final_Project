package sg.nus.edu.iss.finance_telegram_bot.service;

import java.util.Map;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.edu.iss.finance_telegram_bot.repository.ExpenseRepository;

@Service
public class TelegramService {
   
    @Autowired
    private ExpenseRepository expenseRepository;

    public String sendExpense(Map<String, String> fields){
        return expenseRepository.sendExpenseToBackend(fields).getBody();
    }
}
