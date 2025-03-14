package sg.nus.edu.iss.finance_telegram_bot.util;

public class Messages {
    // Messages
    public static final String WELCOME_MESSAGE =
    """
        Welcome to financehub bot! Type /help for available commands.
    """;

    public static final String ADD_EXPENSE_MESSAGE =
    """
        Please add expense in the format name:<name>, date(separate with dashes or slashes):<date>,
        amount(up to 8 digits with 2 decimal places):<amount>, category:<category>, description:<description>, email:<email>      
    """;

    public static final String RECORD_ADDED = 
    """
        Record successfully added        
    """;

    public static final String INVALID_RECORD = 
    """
        Invalid record, please try again        
    """;
    public static final String NOT_ENOUGH_ARGUMENTS =
    """
        You seem to be missing several fields, please enter the previous command and try again.         
    """;
    public static final String TOO_MANY_ARGUMENTS = 
    """
        You have too many fields present        
    """;
}
