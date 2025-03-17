package sg.nus.edu.iss.finance_telegram_bot.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidAmountOfArgumentsException;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidFieldValueException;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.NOT_ENOUGH_ARGUMENTS;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.TOO_MANY_ARGUMENTS;

@Component
public class FieldValidator {
    
    private final double AMOUNTLIMIT = 10000000;
    private final List<String> categories = List.of("shopping","dining","entertainment","utilities","transport");

    // Validation methods
    public boolean validateName(String name){
        if(name.isBlank() || name.length() <= 2){
            throw new InvalidFieldValueException("Name of expense must have a length greater than 2");
        }
        return true;
    }

    public boolean validateDate(String date){
        LocalDate toDate = LocalDate.parse(date);
        LocalDate presentDate = LocalDate.now();
        if(toDate.isAfter(presentDate)){
            throw new InvalidFieldValueException("Date cannot be in the future");
        }
        return true;
    }

    public boolean validateAmount(String amount){
        System.out.println(amount);
        try {
            Double toDoubleAmount = Double.valueOf(amount);
            if(toDoubleAmount > AMOUNTLIMIT){
                throw new InvalidFieldValueException("Amount entered is too big");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new InvalidFieldValueException("Amount must contain numbers only");
        }
        return true;
    }

    public boolean validateCategory(String category){
        if(!categories.contains(category.toLowerCase())){
            throw new InvalidFieldValueException("Category must be in the stated list");
        }
        return true;
    }

    public boolean validateDescription(String description){
        if(description.length() <= 5){
            throw new InvalidFieldValueException("Description of expense must be longer than 5 characters");
        }
        return true;
    }

    public boolean checkNumberOfFields(Map<String, String> fields, int numOfFields){
        int lengthOfKeys = fields.keySet().size();
        System.out.println("Number of keys: " + lengthOfKeys);
        if(lengthOfKeys < numOfFields){
            throw new InvalidAmountOfArgumentsException(NOT_ENOUGH_ARGUMENTS);
        } else if (lengthOfKeys > numOfFields){
            throw new InvalidAmountOfArgumentsException(TOO_MANY_ARGUMENTS);
        } else {
            return true;
        }
    }
}
