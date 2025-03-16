package sg.nus.edu.iss.finance_telegram_bot.models.exception;

public class InvalidFieldValueException extends RuntimeException{
    
    public InvalidFieldValueException(String message){
        super(message);
    }
}
