package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import appl.Fridge;
import model.User;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The POST/signIn Route
 * @author dcodeh
 *
 */
public class PostSignInRoute implements Route {
    
    private final String USERNAME = "userName";
    private final String PASSWORD = "password";
    
    private final TemplateEngine templateEngine;
    private final Fridge fridge;
    
    /**
     * @param te The TemplateEngine to use for this route handler
     */
    public PostSignInRoute(TemplateEngine te, Fridge f) {
        Objects.requireNonNull(te);
        this.templateEngine = te;
        this.fridge = f;
    }

    /**
     * {@inheritDoc}
     * 
     * @param request the HTTP Request
     * @param response the HTTP Response
     * @return Renders either the list page or the sign in page again
     */
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        
        final Map<String, Object> vm = new HashMap<String, Object>();
        
        String username = request.queryParams(USERNAME);
        String password = request.queryParams(PASSWORD);
        
        User user = fridge.getUser(username);
        
        if(user == null) {
            // who dat?
            // TODO render signin page again with error
        }
        
        if(password.equals(user.getPassword())) {
            // they even know their own password, I'm touched
            
            // add the user to the session so their data can be retrieved later
            session.attribute(WebServer.SESSION_USER, user);
            response.redirect(WebServer.LIST_URL);
            
            // TODO redner /list
        } else {
            // sorry, you're not on the list
            // TODO render /signIn
        }
        
        // probably won't get to here
        return null;
    }

}
