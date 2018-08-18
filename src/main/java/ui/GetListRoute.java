/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import model.PersonalItemObject;
import model.SharedItemObject;
import model.ShoppingList;
import model.User;
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
    public static final String SHARED_ITEMS_LIST = "sharedItems";
    public static final String PERSONAL_ITEMS_LIST = "personalItems";
    
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
            User user = (User) session.attribute(WebServer.SESSION_USER);
            
            ShoppingList list = user.getShoppingList();
            if(list != null) {
                List<PersonalItemObject> personalList = list.getPersonalListForTemplate();
                List<SharedItemObject> sharedList = list.getSharedListForTemplate();
                
                if(personalList != null && !personalList.isEmpty()) {
                    vm.put(PERSONAL_ITEMS_LIST, personalList);
                }
                
                if(sharedList != null && !sharedList.isEmpty()) {
                    vm.put(SHARED_ITEMS_LIST, sharedList);
                }
            }
            
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
