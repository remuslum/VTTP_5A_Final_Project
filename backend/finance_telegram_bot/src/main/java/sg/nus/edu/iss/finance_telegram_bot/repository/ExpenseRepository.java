package sg.nus.edu.iss.finance_telegram_bot.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidAmountOfArgumentsException;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidFieldValueException;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.NOT_ENOUGH_ARGUMENTS;
import static sg.nus.edu.iss.finance_telegram_bot.util.Messages.TOO_MANY_ARGUMENTS;

@Repository
public class ExpenseRepository {
    //Fields
    private final String NAME="name";
    private final String DATE="date";
    private final String AMOUNT="amount";
    private final String CATEGORY="category";
    private final String DESCRIPTION="description";
    private final String EMAIL="email";

    private final double AMOUNTLIMIT = 10000000;
    private final List<String> categories = List.of("shopping","dining","entertainment","utilities","transport");
    
    @Value("{$addexpense.url}")
    private String addExpenseUrl;

    public ResponseEntity<String> sendExpenseToBackend(Map<String, String> fields){
        try {
            if(areAllFieldsPresent(fields) && processExpense(fields)){
                sendToBackend(convertToJSONObject(fields).toString());
            }
        } catch (InvalidAmountOfArgumentsException | InvalidFieldValueException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>("Expense successfully added", HttpStatusCode.valueOf(201));
    }

    private boolean areAllFieldsPresent(Map<String, String> fields){
        int lengthOfKeys = fields.keySet().size();
        if(lengthOfKeys< 6){
            throw new InvalidAmountOfArgumentsException(NOT_ENOUGH_ARGUMENTS);
        } else if (lengthOfKeys > 6){
            throw new InvalidAmountOfArgumentsException(TOO_MANY_ARGUMENTS);
        } else {
            if(fields.containsKey(NAME) && fields.containsKey(DATE) && fields.containsKey(AMOUNT) && 
            fields.containsKey(CATEGORY) && fields.containsKey(DESCRIPTION) && fields.containsKey(EMAIL)){
                throw new InvalidAmountOfArgumentsException("The keys provided are not valid");
            }
        }
        return true;
    }

    private boolean processExpense(Map<String, String> fields){
        String nameField = fields.get(NAME);
        String dateField = fields.get(DATE);
        String amountField = fields.get(AMOUNT);
        String categoryField = fields.get(CATEGORY);
        String descriptionField = fields.get(DESCRIPTION);

        return validateName(nameField) && validateDate(dateField) && validateAmount(amountField)
        && validateCategory(categoryField) && validateDescription(descriptionField);
    }

    private JsonObject convertToJSONObject(Map<String, String> fields){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for(Entry<String, String> entry : fields.entrySet()){
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private ResponseEntity<String> sendToBackend(String payload){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> requestEntity = RequestEntity.post(addExpenseUrl).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).body(payload);

        return restTemplate.exchange(requestEntity, String.class);
    }

    // Validation methods
    private boolean validateName(String name){
        if(name.isBlank() || name.length() <= 2){
            throw new InvalidFieldValueException("Name of expense must have a length greater than 2");
        }
        return true;
    }

    private boolean validateDate(String date){
        LocalDate toDate = LocalDate.parse(date);
        LocalDate presentDate = LocalDate.now();
        if(toDate.isAfter(presentDate)){
            throw new InvalidFieldValueException("Date cannot be in the future");
        }
        return true;
    }

    private boolean validateAmount(String amount){
        try {
            Double toDoubleAmount = Double.valueOf(amount);
            if(toDoubleAmount > AMOUNTLIMIT){
                throw new InvalidFieldValueException("Amount entered is too big");
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldValueException("Amount must contain numbers only");
        }
        return true;
    }

    private boolean validateCategory(String category){
        if(!categories.contains(category.toLowerCase())){
            throw new InvalidFieldValueException("Category must be in the stated list");
        }
        return true;
    }

    private boolean validateDescription(String description){
        if(description.length() <= 5){
            throw new InvalidFieldValueException("Description of expense must be longer than 5 characters");
        }
        return true;
    }

}
