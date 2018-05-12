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
    
    private final Fridge fridge;

    /**
      * Construct a new Fridginator WebServer
      * @param fridge The Fridge to run this server off of 
      */
    public WebServer(Fridge fridge) {
        Objects.requireNonNull(fridge);
        this.fridge = fridge;
    }

    /**
      * Set up the HTTP routes that make this work
      */
    public void initialize() {
        staticFiles.location("/public");

        // jetty will start automatically as soon as routes are configured
        // get(HOME_URL, new GetHomeRoute(gameCenter, templateEngine));

    }
    
}
