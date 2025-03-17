package sg.nus.edu.iss.finance_telegram_bot.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidAmountOfArgumentsException;
import sg.nus.edu.iss.finance_telegram_bot.models.exception.InvalidFieldValueException;
import sg.nus.edu.iss.finance_telegram_bot.util.FieldValidator;

@Repository
public class LoanPaymentRepository {

    private final String AMOUNT="amount";
    private final String DATE="date";
    private final String LOAN_ID="loanId";

    private final String LOAN_ID_JSON="loan_id";

    private final int NUM_OF_FIELDS=3;
    
    @Autowired
    private FieldValidator fieldValidator;

    @Value("${add.loan.payment.url}")
    private String addLoanPaymentUrl;

    @Value("${check.loanId.url}")
    private String checkLoanIdUrl;

    public ResponseEntity<String> sendLoanPaymentToBackend(Map<String, String> fields){
        try {
            if(areAllFieldsPresent(fields) && areAllFieldsValid(fields)){
                return sendToBackend(convertToJSONObject(fields));
            }
        } catch (InvalidAmountOfArgumentsException | InvalidFieldValueException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Unable to add loan payment.");
    }

    private boolean areAllFieldsPresent(Map<String, String> fields){
        System.out.println(fields.keySet().size() == NUM_OF_FIELDS);
        if(fieldValidator.checkNumberOfFields(fields, NUM_OF_FIELDS)){
            if(fields.containsKey(LOAN_ID) && fields.containsKey(DATE) && fields.containsKey(AMOUNT)){
                return true;
            } else {
                throw new InvalidAmountOfArgumentsException("The keys provided are not valid");
            }
        }
        return false;
    }

    private boolean areAllFieldsValid(Map<String, String> fields){
        String amountField = fields.get(AMOUNT);
        String dateField = fields.get(DATE);
        String loanIdField = fields.get(LOAN_ID);

        return fieldValidator.validateAmount(amountField) && fieldValidator.validateDate(dateField) && validateLoanId(loanIdField);
    }

     private String convertToJSONObject(Map<String, String> fields){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        
        arrayBuilder.add(builder.add(AMOUNT, Double.parseDouble(fields.get(AMOUNT))).add(DATE, fields.get(DATE)).add(LOAN_ID_JSON, fields.get(LOAN_ID)).build());
        return arrayBuilder.build().toString();
    }

     private ResponseEntity<String> sendToBackend(String payload){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> requestEntity = RequestEntity.post(addLoanPaymentUrl).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).body(payload);

        return restTemplate.exchange(requestEntity, String.class);
    }

    private boolean validateLoanId(String loanId){
        String finalUrl = UriComponentsBuilder.fromUriString(checkLoanIdUrl + "/{loanId}").buildAndExpand(loanId).toUriString();
        RequestEntity<Void> request = RequestEntity.get(finalUrl).build();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getStatusCode().equals(HttpStatusCode.valueOf(200));
    }
}
