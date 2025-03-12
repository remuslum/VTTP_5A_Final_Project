package sg.nus.edu.iss.vttp_5a_final_project.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class APIPython {
    
    @Value("${python.api.ma.url}")
    private String pythonMAUrl;

    @Value("${python.api.rsi.url}")
    private String pythonRSIUrl;

    private final String SYMBOL_PARAM = "symbol";

    public String getMAUrl(String symbol){
        return UriComponentsBuilder.fromUriString(pythonMAUrl).queryParam(SYMBOL_PARAM, symbol).toUriString();
    }

    public String getRsiUrl(String symbol){
        return UriComponentsBuilder.fromUriString(pythonRSIUrl).queryParam(SYMBOL_PARAM, symbol).toUriString();
    }
}
