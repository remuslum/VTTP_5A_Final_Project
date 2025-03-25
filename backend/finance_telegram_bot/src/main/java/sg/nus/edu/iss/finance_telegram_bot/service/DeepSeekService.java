package sg.nus.edu.iss.finance_telegram_bot.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Service
public class DeepSeekService {
    
    @Value("${deepseek.api.key}")
    private String DEEPSEEK_API_KEY;

    private final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private final String DEEPSEEK_MODEL = "deepseek-chat";

    //Fields
    private final String ROLE_USER = "user";

    public String getChatResponse(String userMessage){
        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + DEEPSEEK_API_KEY);

        // Request Body
        JsonObjectBuilder requestBody = Json.createObjectBuilder().add("model",DEEPSEEK_MODEL);

        JsonObject message = Json.createObjectBuilder().add("role",ROLE_USER).add("content",userMessage).build();

        JsonArray messages = Json.createArrayBuilder().add(message).build();
        requestBody.add("messages",messages);

        // API Call
        HttpEntity<String> entity = new HttpEntity<>(requestBody.build().toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(
            API_URL,
            HttpMethod.POST,
            entity,
            String.class
        );

        return filterChatResponse(response.getBody());
    }

    private String filterChatResponse(String response){
        JsonObject object = Json.createReader(new StringReader(response)).readObject();
        JsonArray choices = object.getJsonArray("choices");

        return choices.getJsonObject(0).getJsonObject("message").getString("content");
    }
}
