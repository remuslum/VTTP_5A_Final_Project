package sg.nus.edu.iss.finance_telegram_bot.models.exception;

public class InvalidAmountOfArgumentsException extends RuntimeException {
    public InvalidAmountOfArgumentsException(String message){
        super(message);
    }
}
