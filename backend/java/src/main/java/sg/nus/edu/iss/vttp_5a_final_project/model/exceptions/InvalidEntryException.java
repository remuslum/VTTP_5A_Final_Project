package sg.nus.edu.iss.vttp_5a_final_project.model.exceptions;

public class InvalidEntryException extends RuntimeException {
    public InvalidEntryException(String message){
        super(message);
    }
}
