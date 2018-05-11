package ui;

/**
 * Initializes the set of GET/POST Route handlers needed for Fridginator's
 * Web Interface to work.
 * @author dcodeh
 *
 */
public class WebServer {

    // session attributes
    public final static String SESSION_USER = "User";
    public final static String SESSION_MESSAGE = "Message";
    
    // GET URL Patterns
    public static final String HOME_URL = "/";
    public static final String SIGNIN_URL = "signIn";
    public static final String LIST_URL = "/list";
    public static final String EDIT_LIST_URL = "/editList";
    
    // TODO eventually make this initialize jetty and all of that...
    
    
}
