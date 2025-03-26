package sg.nus.edu.iss.vttp_5a_final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Service
public class FirebaseAuthService {
    
    @Autowired
    private FirebaseAuth firebaseAuth;

    public String verifyToken(String idToken){
        try {
            // Verify ID token using Firebase Admin SDK
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            return decodedToken.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String logOut(String idToken){
        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            firebaseAuth.revokeRefreshTokens(decodedToken.getUid());
            return decodedToken.toString();
        } catch (Exception e){
            return "";
        }
    }
        
}
