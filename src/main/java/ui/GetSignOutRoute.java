/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.Objects;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import model.User;
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
public class GetSignOutRoute implements Route {
    
    @SuppressWarnings("unused")
    private final TemplateEngine templateEngine;
    
    /**
     * @param te The TemplateEngine to use for this route handler
     */
    public GetSignOutRoute(TemplateEngine te) {
        Objects.requireNonNull(te);
        this.templateEngine = te;
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
                
        User user = (User) session.attribute(WebServer.SESSION_USER);
        
        if(user != null) {
            session.removeAttribute(WebServer.SESSION_USER);
            SessionMessageHelper.addSessionMessage(session, 
                                                   "You've been logged out.", 
                                                   MessageType.info);
        } else {
            SessionMessageHelper.addSessionMessage(session, 
                                                   "Something is fishy here...You must log in before you can log out.", 
                                                   MessageType.error);
        }
        
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}
