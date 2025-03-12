package sg.nus.edu.iss.vttp_5a_final_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import sg.nus.edu.iss.vttp_5a_final_project.util.APIPython;

@Repository
public class PythonAPIRepo {

    @Autowired
    private APIPython apiPython;
    
    public ResponseEntity<String> getMAResults(String symbol){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity.get(apiPython.getMAUrl(symbol)).build();
        return restTemplate.exchange(request, String.class);
    }
}
