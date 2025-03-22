package sg.nus.edu.iss.vttp_5a_final_project.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class Firebase {
    
   @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader()
                .getResourceAsStream("firebase.config.json");

        if (serviceAccount == null) {
            throw new IllegalStateException("Firebase serviceAccountKey.json file not found in resources.");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(app);
    }
    
    
}
