/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.LinkedHashMap;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import model.ShoppingList;
import model.User;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Handles saving a user's personal list
 * @author dcodeh
 */
public class PostEditListRoute implements Route {

    private static final String SAVE_ACTION = "save";
    private static final String EXIT_ACTION = "exit";
    
    private static final String LIST_TEXT = "listText";
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final User user = (User) session.attribute(WebServer.SESSION_USER);
        
        if(user != null) {
            ShoppingList shoppingList = user.getShoppingList();
            LinkedHashMap<String, Boolean> oldPersonalList = shoppingList.getPersonalList();
            LinkedHashMap<String, Boolean> newPersonalList = new LinkedHashMap<>();
            
            if(request.queryParams(SAVE_ACTION) != null) {
                response.redirect(WebServer.EDIT_LIST_URL);
            } else if(request.queryParams(EXIT_ACTION) != null) {
                response.redirect(WebServer.LIST_URL);
            }
            
            String listText = request.queryParams(LIST_TEXT);
            if(listText != null) {
                String[] lines = listText.split("\n");
                for(String line : lines) {
                    if(!line.isEmpty()) {
                        Boolean alreadyChecked = oldPersonalList.get(line.trim());
                        
                        if(alreadyChecked != null) {
                            newPersonalList.put(line.trim(), alreadyChecked);
                        } else {
                            newPersonalList.put(line.trim(), false /* value */);
                        }
                    }
                }
                
                SessionMessageHelper.addSessionMessage(session, "Your list was updated.", MessageType.info);
                shoppingList.setPersonalList(newPersonalList);
            } else {
                SessionMessageHelper.addSessionMessage(session, "Your list has been cleared.", MessageType.info);
                user.clearPersonalList();
            }
            
        } else {
            // kick them back to the login page
            SessionMessageHelper.addSessionMessage(session, "You're not signed in!", MessageType.error);
        }
        return null;
    }

}
