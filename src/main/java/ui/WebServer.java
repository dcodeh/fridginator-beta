package ui;

import java.util.Objects;
import appl.Fridge;
import spark.TemplateEngine;
import java.util.logging.Logger;

import static spark.Spark.staticFiles;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Initializes the set of GET/POST Route handlers needed for Fridginator's
 * Web Interface to work.
 * @author dcodeh
 *
 */
public class WebServer {
    private final static Logger log = Logger.getLogger(WebServer.class.getName());

    // session attributes
    public final static String SESSION_USER = "User";
    public final static String SESSION_MESSAGE = "Message";
    
    // GET URL Patterns
    public static final String HOME_URL = "/";
    public static final String SIGNIN_URL = "signIn";
    public static final String LIST_URL = "/list";
    public static final String EDIT_LIST_URL = "/editList";
    
    private final Fridge fridge;
    private final TemplateEngine templateEngine;

    /**
      * Construct a new Fridginator WebServer
      * @param fridge The Fridge to run this server off of 
      */
    public WebServer(Fridge fridge, TemplateEngine te) {
        Objects.requireNonNull(fridge);
        Objects.requireNonNull(te);
        this.fridge = fridge;
        this.templateEngine = te;
    }

    /**
      * Set up the HTTP routes that make this work
      */
    public void initialize() {
        staticFiles.location("/public");

        // jetty will start automatically as soon as routes are configured
//        get(HOME_URL, new GetHomeRoute(gameCenter, templateEngine));
        get(SIGNIN_URL, new GetSignInRoute(templateEngine));
        log.config("WebServer initialization complete");
    }
    
}
