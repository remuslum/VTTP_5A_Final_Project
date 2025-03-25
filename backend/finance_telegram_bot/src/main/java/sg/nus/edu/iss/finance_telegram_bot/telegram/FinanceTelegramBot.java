package sg.nus.edu.iss.finance_telegram_bot.telegram;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import sg.nus.edu.iss.finance_telegram_bot.service.DeepSeekService;
import sg.nus.edu.iss.finance_telegram_bot.service.FinanceTelegramService;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.ADD_EXPENSE_MESSAGE;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.ADD_LOAN_PAYMENT_MESSAGE;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.ALREADY_REGISTERED;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.CHATBOT_NOT_STARTED;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.EXIT_CHATBOT;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.NOT_REGISTERD;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.WELCOME_MESSAGE;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.WELCOME_TO_CHATBOT;

@Component
public class FinanceTelegramBot extends TelegramLongPollingBot{

    private final String botUsername;
    private final Map<String, Boolean> userExpenseState = new HashMap<>();
    private final Map<String, Boolean> userLoanPaymentState = new HashMap<>();
    private final Map<String, Boolean> userCheckEmailState = new HashMap<>();
    private final Map<String, Boolean> userChatBotState = new HashMap<>();

    private String userEmail = "";
    private boolean isUserRegistered = false;

    @Autowired
    private FinanceTelegramService telegramService;

    @Autowired
    private DeepSeekService deepSeekService;

    public FinanceTelegramBot(@Value("${telegram.bot.username}")String botUsername,
    @Value("${telegram.bot.token}")String botToken){
        super(botToken);
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Check if the update contains a message and has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String text = message.getText();

            // Process add expense
            if (userExpenseState.getOrDefault(chatId, false)){
                Map<String, String> expenseFields = convertFieldsToMap(text);
                // Add email to the expense fields
                expenseFields.put("email",userEmail);
                sendTextMessage(chatId, telegramService.sendExpense(expenseFields));
                userExpenseState.put(chatId, false);

            } else if (userLoanPaymentState.getOrDefault(chatId, false)){
                Map<String, String> loanPaymentFields = convertFieldsToMap(text);
                sendTextMessage(chatId, telegramService.sendLoanPayment(loanPaymentFields));
                userLoanPaymentState.put(chatId, false);

                // Verify if the user has a registered email
            } else if (userCheckEmailState.getOrDefault(chatId, false)){
                ResponseEntity<String> response = telegramService.checkEmail(text);

                if(response.getStatusCode().equals(HttpStatusCode.valueOf(200))){
                    userEmail = text;
                    isUserRegistered = !isUserRegistered;
                    userCheckEmailState.put(chatId, false);
                } 
                sendTextMessage(chatId, response.getBody());
            } else if (userChatBotState.getOrDefault(chatId, false)){
                // Send chat bot response
                sendTextMessage(chatId, deepSeekService.getChatResponse(text));
            } else {
                // Process commands manually
                if (text.equalsIgnoreCase("/start")) {
                    if(userEmail.isBlank()){
                        sendTextMessage(chatId, WELCOME_MESSAGE);
                        userCheckEmailState.put(chatId, true);
                    } else {
                        sendTextMessage(chatId, ALREADY_REGISTERED);
                    }
                    
                } else if (text.equalsIgnoreCase("/help")) {
                    sendTextMessage(chatId, "Available commands:\n/start - Start the bot\n/help - Show commands");
                } else if (text.equalsIgnoreCase("/addloanpayment")){
                    if(isUserRegistered){
                        userLoanPaymentState.put(chatId, true);
                        sendTextMessage(chatId, ADD_LOAN_PAYMENT_MESSAGE);
                    } else {
                        sendTextMessage(chatId, NOT_REGISTERD);
                    }
                } else if (text.equalsIgnoreCase("/addexpense")){
                    if(isUserRegistered){
                        userExpenseState.put(chatId, true);
                        sendTextMessage(chatId, ADD_EXPENSE_MESSAGE);
                    } else {
                        sendTextMessage(chatId, NOT_REGISTERD);
                    }
                } else if (text.equalsIgnoreCase("/listloans")){
                    if(isUserRegistered){
                        sendTextMessage(chatId, telegramService.getAllLoans(userEmail));
                    } else {
                        sendTextMessage(chatId,NOT_REGISTERD);
                    }
                } else if (text.equalsIgnoreCase("/startchat")){
                    if(isUserRegistered){
                        userChatBotState.put(chatId, true);
                        sendTextMessage(chatId, WELCOME_TO_CHATBOT);
                    } else {
                        sendTextMessage(chatId,NOT_REGISTERD); 
                    } 
                } else if (text.equalsIgnoreCase("/exitchat")){
                    if(userChatBotState.get(chatId)){
                        userChatBotState.put(chatId, false);
                        sendTextMessage(chatId, EXIT_CHATBOT);
                    } else {
                        sendTextMessage(chatId, CHATBOT_NOT_STARTED);
                    }
                }
                else {
                    sendTextMessage(chatId, "Unknown command. Type /help to see available commands.");
                }
            }
        }
    }
    
    private void sendTextMessage(String chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    
    private Map<String, String> convertFieldsToMap(String message){
        String[] expenseFields = message.split(",");
        Map<String, String> fields = new HashMap<>();
        for(String ex : expenseFields){
            String[] temp = ex.split(":");
            fields.put(temp[0].trim(),temp[1].trim());
        }
        return fields;
    }



    
}
