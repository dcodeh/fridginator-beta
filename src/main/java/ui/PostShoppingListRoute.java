/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package ui;

import java.util.HashMap;
import java.util.LinkedHashMap;

import fridginator.SessionMessageHelper;
import fridginator.SessionMessageHelper.MessageType;
import model.Item;
import model.ShoppingList;
import model.ShoppingListItem;
import model.User;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * This class saves the checked and unchecked items from the main list page.
 * @author dcodeh
 */
public class PostShoppingListRoute implements Route {
    
    private final static String SAVE_ACTION = "save";
    private final static String EDIT_ACTION = "edit";
    private final static String EXIT_ACTION = "exit";
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        final User user = (User) session.attribute(WebServer.SESSION_USER);
        
        if(user != null) {
            ShoppingList shoppingList = user.getShoppingList();
            LinkedHashMap<String, Boolean> personalList = shoppingList.getPersonalList();
            HashMap<Item, ShoppingListItem> sharedList = shoppingList.getSharedList();
            
            if(request.queryParams(SAVE_ACTION) != null) {
                response.redirect(WebServer.LIST_URL);
            } else if(request.queryParams(EDIT_ACTION) != null) {
                response.redirect(WebServer.EDIT_LIST_URL);
            } else if(request.queryParams(EXIT_ACTION) != null) {
                response.redirect(WebServer.SIGNOUT_URL);
            }
            
            for(String item : personalList.keySet()) {
                shoppingList.setPersonalItemCheckStatus(item, request.queryParams(item) != null);
            }
            
            for(Item item : sharedList.keySet()) {
                shoppingList.setSharedItemCheckStatus(item, request.queryParams(item.getName()) != null);
            }
            
            
        } else {
            // kick them back to the login page
            SessionMessageHelper.addSessionMessage(session, "You aren't logged in!", MessageType.error);
            response.redirect(WebServer.SIGNIN_URL);
        }
        
        
        return null;
    }

}
