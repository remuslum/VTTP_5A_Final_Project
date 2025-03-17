package sg.nus.edu.iss.finance_telegram_bot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Repository
public class EmailRepository {
    
    @Value("${check.email.url}")
    private String checkEmailUrl;

    public ResponseEntity<String> checkEmail(String email){
        String finalUrl = UriComponentsBuilder.fromUriString(checkEmailUrl + "/{email}").buildAndExpand(email).toUriString();
        RequestEntity<Void> request = RequestEntity.get(finalUrl).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(request, String.class);
    }
}
