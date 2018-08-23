/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.HashMap;
import java.util.Map;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import model.ShoppingList;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * This route renders the Edit Personal List Page
 * @author dcodeh
 */
public class GetEditListRoute implements Route {

    public static final String TITLE_ATTR = "title";
    public static final String VIEW_NAME = "edit.ftl"; 
    private static final String LIST_CONTENTS_ATTR = "listContents";
    
    private static final String TITLE = "Edit List";
    
    private final TemplateEngine templateEngine;
    
    public GetEditListRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<String, Object>();
        vm.put(TITLE_ATTR, TITLE);
        
        if(session.attribute(WebServer.SESSION_USER) != null) {
            String listText = "";
            User user = (User) session.attribute(WebServer.SESSION_USER);
            
            ShoppingList list = user.getShoppingList();
            HashMap<String, Boolean> personalList = list.getPersonalList();
            
            for(String item : personalList.keySet()) {
                listText += item + "\n";
            }
            
            vm.put(LIST_CONTENTS_ATTR, listText);
            SessionMessageHelper.displaySessionMessages(session, vm);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
            
        } else {
            // you're not on the list
            // kick them back to the login page
            SessionMessageHelper.addSessionMessage(session, "You need to log in to edit your list!", MessageType.error);
            response.redirect(WebServer.HOME_URL);
        }
        return null;
    }

}
