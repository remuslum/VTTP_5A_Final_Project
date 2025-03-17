package sg.nus.edu.iss.finance_telegram_bot.repository;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidAmountOfArgumentsException;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidFieldValueException;
import sg.nus.edu.iss.finance_telegram_bot.util.FieldValidator;

@Repository
public class ExpenseRepository {
    //Fields
    private final String NAME="name";
    private final String DATE="date";
    private final String AMOUNT="amount";
    private final String CATEGORY="category";
    private final String DESCRIPTION="description";
    private final String EMAIL="email";

    private final int NUM_OF_FIELDS=6;
    
    @Value("${add.expense.url}")
    private String addExpenseUrl;

    @Autowired
    private FieldValidator fieldValidator;

    public ResponseEntity<String> sendExpenseToBackend(Map<String, String> fields){
        try {
            if(areAllFieldsPresent(fields) && areAllFieldsValid(fields)){
                return sendToBackend(convertToJSONObject(fields));
            }
        } catch (InvalidAmountOfArgumentsException | InvalidFieldValueException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Unable to add expense.");
    }

    private boolean areAllFieldsPresent(Map<String, String> fields){
        if(fieldValidator.checkNumberOfFields(fields, NUM_OF_FIELDS)){
            if(fields.containsKey(NAME) && fields.containsKey(DATE) && fields.containsKey(AMOUNT) && 
            fields.containsKey(CATEGORY) && fields.containsKey(DESCRIPTION) && fields.containsKey(EMAIL)){
                return true;
            } else {
                throw new InvalidAmountOfArgumentsException("The keys provided are not valid");
            }
        }
        return false;
    }

    private boolean areAllFieldsValid(Map<String, String> fields){
        String nameField = fields.get(NAME);
        String dateField = fields.get(DATE);
        String amountField = fields.get(AMOUNT);
        String categoryField = fields.get(CATEGORY);
        String descriptionField = fields.get(DESCRIPTION);

        return fieldValidator.validateName(nameField) && fieldValidator.validateDate(dateField) && fieldValidator.validateAmount(amountField)
        && fieldValidator.validateCategory(categoryField) && fieldValidator.validateDescription(descriptionField);
    }

    private String convertToJSONObject(Map<String, String> fields){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for(Entry<String, String> entry : fields.entrySet()){
            if(entry.getKey().equals(AMOUNT)){
                builder.add(entry.getKey(), Double.parseDouble(entry.getValue()));
            } else {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        arrayBuilder.add(builder.build());
        return arrayBuilder.build().toString();
    }

    private ResponseEntity<String> sendToBackend(String payload){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> requestEntity = RequestEntity.post(addExpenseUrl).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).body(payload);

        return restTemplate.exchange(requestEntity, String.class);
    }

    

}
