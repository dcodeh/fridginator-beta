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
 * Get the list page (incomplete for now)
 * @author dcodeh
 *
 */
public class GetListRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "list.ftl"; 
    
    private static final String TITLE = "My List";
    
    private final TemplateEngine templateEngine;
    
    public GetListRoute(TemplateEngine te) {
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
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            // this person is signed in, and has an active session
            // show them their list
            // TODO make this actually a list
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            // You're kind isn't welcome here
            // kick 'em back to the login form
            SessionMessageHelper.addSessionMessage(session, "You are not logged in.", MessageType.error);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
    }

}
