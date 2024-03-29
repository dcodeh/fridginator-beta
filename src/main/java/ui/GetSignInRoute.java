/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.HashMap;
import java.util.Map;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Why didn't I start with this one?
 * @author dcodeh
 *
 */
public class GetSignInRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VERSION_ATTR = "version";
    public static final String VIEW_NAME = "signIn.ftl"; 
    
    private static final String TITLE = "Sign In";
    private static final String VERSION = "Version 0.1";
    
    private final TemplateEngine templateEngine;
    
    public GetSignInRoute(TemplateEngine te) {
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
        
        final Map<String, Object> vm = new HashMap<String, Object>();
        vm.put(TITLE_ATTR, TITLE);
        vm.put(VERSION_ATTR, VERSION);
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person has already signed in, and has an active session
            SessionMessageHelper.addSessionMessage(session, "Welcome back!", MessageType.info);
            response.redirect(WebServer.LIST_URL); // whooooosh...go to the list page
            return null; // shhhhh, compiler!
        } else {
            // render the login form
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }

}
