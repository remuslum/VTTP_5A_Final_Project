package sg.nus.edu.iss.finance_telegram_bot.repository;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Repository
public class LoanRepository {

    private final String AMOUNT_KEY="amount";
    private final String DESCRIPTION_KEY="description";
    private final String ID_KEY="id";
    private final String SEPARATOR=": ";

    @Value("${list.all.loans.url}")
    private String listAllLoansUrl;

    
    public String getAllLoans(String email){
        RestTemplate restTemplate = new RestTemplate();
        String listLoansUrl = UriComponentsBuilder.fromUriString(listAllLoansUrl).queryParam("email", email).toUriString();
        RequestEntity<Void> requestEntity = RequestEntity.get(listLoansUrl).accept(MediaType.APPLICATION_JSON).build();
        String response = restTemplate.exchange(requestEntity, String.class).getBody();
        return parseResponse(response);
    }

    private String parseResponse(String response){
        JsonArray loansJsonArray = Json.createReader(new StringReader(response)).readArray();
        StringBuilder loansString = new StringBuilder();
        loansString.append("Your loans:\n");

        for(int i = 0; i < loansJsonArray.size(); i++){
            JsonObject loansObject = loansJsonArray.getJsonObject(i);
            loansString.append(ID_KEY).append(SEPARATOR).append(loansObject.getString(ID_KEY)).append("\n");
            loansString.append(AMOUNT_KEY).append(SEPARATOR).append(loansObject.getJsonNumber(AMOUNT_KEY).doubleValue()).append("\n");
            loansString.append(DESCRIPTION_KEY).append(SEPARATOR).append(loansObject.getString(DESCRIPTION_KEY)).append("\n");
            // New line for readability
            loansString.append("\n");
        }
        return loansString.toString();
    }
}
