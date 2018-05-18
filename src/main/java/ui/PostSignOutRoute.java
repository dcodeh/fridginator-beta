package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import appl.Fridge;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

/**
 * The POST/signOut Route
 * @author dcodeh
 *
 */
public class PostSignOutRoute implements Route {
    
    private final TemplateEngine templateEngine;
    private final Fridge fridge;
    
    /**
     * @param te The TemplateEngine to use for this route handler
     */
    public PostSignOutRoute(TemplateEngine te, Fridge f) {
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
        
        User user = (User) session.attribute(WebServer.SESSION_USER);
        
        if(user != null) {
            session.removeAttribute(WebServer.SESSION_USER);
        }
        
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}
