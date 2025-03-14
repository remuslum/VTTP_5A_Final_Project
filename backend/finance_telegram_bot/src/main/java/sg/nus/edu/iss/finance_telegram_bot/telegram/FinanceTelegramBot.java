package sg.nus.edu.iss.finance_telegram_bot.telegram;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidAmountOfArgumentsException;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.ADD_EXPENSE_MESSAGE;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.INVALID_RECORD;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.NOT_ENOUGH_ARGUMENTS;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.RECORD_ADDED;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.TOO_MANY_ARGUMENTS;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.WELCOME_MESSAGE;

@Component
public class FinanceTelegramBot extends TelegramLongPollingBot{
    private final String botUsername;
    private final Map<String, Boolean> userExpenseState = new HashMap<>();
    
    //Fields
    private final String NAME="name";
    private final String DATE="date";
    private final String AMOUNT="amount";
    private final String CATEGORY="category";
    private final String DESCRIPTION="description";
    private final String EMAIL="email";

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
                try {
                    if(areAllFieldsPresent(text)){
                        // save to database
                        sendTextMessage(chatId, RECORD_ADDED);
                    } else {
                        sendTextMessage(chatId, INVALID_RECORD);
                    }
                } catch (InvalidAmountOfArgumentsException ie){
                    sendTextMessage(chatId, ie.getMessage());
                }
                userExpenseState.put(chatId, false);
            } else {
                // Process commands manually
                if (text.equalsIgnoreCase("/start")) {
                    sendTextMessage(chatId, WELCOME_MESSAGE);
                } else if (text.equalsIgnoreCase("/help")) {
                    sendTextMessage(chatId, "Available commands:\n/start - Start the bot\n/help - Show commands");
                } else if (text.equalsIgnoreCase("/test")){
                    sendTextMessage(chatId, "testing");
                } else if (text.equalsIgnoreCase("/addexpense")){
                    userExpenseState.put(chatId, true);
                    sendTextMessage(chatId, ADD_EXPENSE_MESSAGE);
                } else {
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

    private boolean areAllFieldsPresent(String message){
        String[] expense = message.split(",");
        if(expense.length < 6){
            throw new InvalidAmountOfArgumentsException(NOT_ENOUGH_ARGUMENTS);
        } else if (expense.length > 6){
            throw new InvalidAmountOfArgumentsException(TOO_MANY_ARGUMENTS);
        } else {
            Map<String, String> fields = new HashMap<>();
            for(String ex : expense){
                String[] temp = ex.split(":");
                fields.put(temp[0],temp[1]);
            }
            return fields.containsKey(NAME) && fields.containsKey(DATE) && fields.containsKey(AMOUNT) && 
            fields.containsKey(CATEGORY) && fields.containsKey(DESCRIPTION) && fields.containsKey(EMAIL);
        }
    }
}
