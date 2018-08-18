/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.Objects;
import appl.Fridge;
import spark.TemplateEngine;
import java.util.logging.Logger;

import static spark.Spark.staticFiles;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.stop;

/**
 * Initializes the set of GET/POST Route handlers needed for Fridginator's
 * Web Interface to work.
 * @author dcodeh
 *
 */
public class WebServer {
    private final static Logger log = Logger.getLogger(WebServer.class.getName());

    // session attributes
    public final static String SESSION_USER = "user";
    public final static String SESSION_MESSAGE = "message";
    public final static String MESSAGE_TYPE = "messageType";
    
    // GET URL Patterns
    public static final String HOME_URL = "/";
    public static final String LIST_URL = "/list";
    public static final String SIGNIN_URL = "/signIn";
    public static final String SIGNOUT_URL = "/signOut";
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
        
        // point it to the stuff that doesn't change
        staticFiles.location("/public");
        
        // TODO change the default port from :4567
        port(4567);

        // jetty will start automatically as soon as routes are configured
        
        // 
        // Routes related to signing in and out
        //
        get(HOME_URL, new GetSignInRoute(templateEngine));
        post(SIGNIN_URL, new PostSignInRoute(templateEngine, fridge));
        post(SIGNOUT_URL, new PostSignOutRoute(templateEngine));

        //
        // Routes related to using the thing once logged in
        //
        get(LIST_URL, new GetListRoute(templateEngine));
        
        log.config("WebServer initialization complete");
    }
    
    public void terminate() {
        stop(); // hah, nice
    }
    
}
