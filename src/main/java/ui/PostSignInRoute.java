/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.Objects;

import appl.Fridge;
import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
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
    
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String LOGIN_UNSUCCESSFUL = "Unknown username or password!";
    
    @SuppressWarnings("unused")
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
               
        String username = request.queryParams(USERNAME);
        String password = request.queryParams(PASSWORD);
        
        User user = fridge.getUser(username);
        
        if(user == null) {
            // who dat?
            SessionMessageHelper.addSessionMessage(session, LOGIN_UNSUCCESSFUL, MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        
        if(password.equals(user.getPassword())) {
            // they even know their own password, I'm touched
            
            // add the user to the session so their data can be retrieved later
            session.attribute(WebServer.SESSION_USER, user);
            response.redirect(WebServer.LIST_URL);
            return null;
        } else {
            // sorry, you're not on the list
            SessionMessageHelper.addSessionMessage(session, LOGIN_UNSUCCESSFUL, MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
    }

}
