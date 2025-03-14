package sg.nus.edu.iss.vttp_5a_final_project.telegram;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.AMOUNT;
import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.CATEGORY;
import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.DATE;
import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.DESCRIPTION;
import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.EMAIL;
import static sg.nus.edu.iss.vttp_5a_final_project.model.Expense.NAME;
import sg.nus.edu.iss.vttp_5a_final_project.model.exceptions.InvalidAmountOfArgumentsException;

@Component
public class FinanceTelegramBot extends TelegramLongPollingBot{
    
    private final String botUsername;
    private final Map<String, Boolean> userExpenseState = new HashMap<>();
    // Messages
    private final String WELCOME_MESSAGE =
    """
        Welcome to financehub bot! Type /help for available commands.
    """;

    private final String ADD_EXPENSE_MESSAGE =
    """
        Please add expense in the format name:<name>, date(separate with dashes or slashes):<date>,
        amount(up to 8 digits with 2 decimal places):<amount>, category:<category>, description:<description>, email:<email>      
    """;

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

            }

            // Process commands manually
            if (text.equalsIgnoreCase("/start")) {
                sendTextMessage(chatId, "Welcome to the bot! Type /help for available.");
            } else if (text.equalsIgnoreCase("/help")) {
                sendTextMessage(chatId, "Available commands:\n/start - Start the bot\n/help - Show commands");
            } else if (text.equalsIgnoreCase("/test")){
                sendTextMessage(chatId, "testing");
            } else if (text.equalsIgnoreCase("/addexpense")){
                userExpenseState.put(chatId, true);
            }
            else {
                sendTextMessage(chatId, "Unknown command. Type /help to see available commands.");
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

    private boolean isValidExpense(String message){
        String[] expense = message.split(",");
        if(expense.length < 6){
            throw new InvalidAmountOfArgumentsException("You seem to be missing a field");
        } else if (expense.length > 6){
            throw new InvalidAmountOfArgumentsException("You seem to have too many fields present");
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
