package sg.nus.edu.iss.vttp_5a_final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_final_project.repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    private final String FIREBASE_AUTH_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    private final String FIREBASE_SIGN_UP_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp";
    
    public String logUserInAndGetToken(String email, String password){
        return loginRepository.getTokenFromRequest(email, password, FIREBASE_AUTH_URL);
        
    }

    public String createAnAccount(String email, String password){
        return loginRepository.getTokenFromRequest(email, password, FIREBASE_SIGN_UP_URL);
    } 

    
}
