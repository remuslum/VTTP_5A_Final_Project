package sg.nus.edu.iss.finance_telegram_bot.util;

public class Messages {
    // Messages
    public static final String WELCOME_MESSAGE =
    """
        Welcome to financehub bot!
        Please enter your registered email.
    """;

    public static final String ADD_EXPENSE_MESSAGE =
    """
        Please add expense in the following format and separate each field in a new line \n
        name:<name>,
        date(separate with dashes or slashes):<date>,
        amount(up to 8 digits with 2 decimal places):<amount>,
        category:<category>,
        description:<description>
    """;

    public static final String ADD_LOAN_PAYMENT_MESSAGE =
    """
        Please add loan payment in the following format and separate each field in a new line:
        amount: <amount>,
        date: <date>,
        loanId: <loanId>
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

    public static final String NOT_REGISTERD = 
    """
        You seem to not have a registered email with us. Please enter /start and follow the steps to see if your email is registered.        
    """;

    public static final String ALREADY_REGISTERED =
    """
        You have already verified your email.        
    """;

    public static final String WELCOME_TO_CHATBOT = 
    """
        Welcome to our chatbot. How may I assist you?        
    """;

    public static final String EXIT_CHATBOT =
    """
        Thanks for using our service and we hope you have a great day.        
    """;

    public static final String CHATBOT_NOT_STARTED = 
    """
        You have not started a chatbot session. Start one by typing /startchat.        
    """;
}
