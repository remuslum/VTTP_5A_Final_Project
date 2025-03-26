package sg.nus.edu.iss.vttp_5a_final_project.model;

import java.io.StringReader;
import java.time.LocalDate;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateofBirth;

    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String EMAIL = "email";


    public User() {
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateofBirth() {
        return dateofBirth;
    }
    public void setDateofBirth(LocalDate dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public static User convertJsonToUser(String payload){
        User user = new User();
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();

        user.setFirstName(object.getString(FIRSTNAME));
        user.setLastName(object.getString(LASTNAME));
        user.setEmail(object.getString(EMAIL));
        user.setDateofBirth(LocalDate.parse(object.getString(DATE_OF_BIRTH)));

        return user;
    }
}
