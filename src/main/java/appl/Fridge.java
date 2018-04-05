package appl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import console.BlankCommand;
import console.Command;
import console.HelpCommand;
import console.InventoryCommand;
import console.ItemCommand;
import console.ItemDeleteCommand;
import console.ItemsCommand;
import console.PasswdCommand;
import console.ShareItemCommand;
import console.SharedCommand;
import console.SharingCommand;
import console.UnshareItemCommand;
import console.Command.ExitCode;
import console.ExitCommand;
import console.UserAddCommand;
import console.UserRemoveCommand;
import console.UserReportCommand;
import model.Item;
import model.User;

/**
 * Stores and tracks all of the shared and unshared items.
 * @author dcodeh
 */
public class Fridge {

    // huh it's empty
    
    /**
     * Holds all of the items shared between users 
     */
    private HashSet<Item> fridge;
    
    /**
     * Holds all of the participating users
     */
    private HashMap<String, User> users;
    
    /**
     * Holds all of the acceptable console commands, for easy extensibility
     * :w
     * dang it eclipse!
     */
    private HashMap<String, Command> commands;

    public Fridge() {
        fridge = new HashSet<Item>();
        users = new HashMap<String, User>();
        
        initCommands();
    }
    
    public void addItem(Item i) {
        fridge.add(i);
    }
    
    public void removeItem(Item i) {
        fridge.remove(i);
    }
    
    public void addUser(User u) {
        users.put(u.getUsername(), u);
    }
    
    public void removeUser(String username) {
        users.remove(username);
    }
    
    public User getUser(String username) {
        return users.get(username);
    }
    
    public Collection<User> getAllUsers() {
        return users.values();
    }
    
    /**
     * Sees if the garbage the user entered into the console is intelligible.
     * If it is, then do the thing.
     * 
     * Otherwise, spit in their face.
     * @param commandLine The full line from stdin
     */
    public ExitCode interpretCommands(String commandLine) {
        
        // figure out what the user is talking about
        String[] arguments = commandLine.split(" ");
        String keyword = arguments[0];
        
        Command command = commands.get(keyword);
        
        // if it doesn't make sense, kick 'em out
        if(command == null) {
            System.out.println("Unkown command!");
            return ExitCode.FAILURE;
        }
        
        // if wrong number of arguments, kick 'em out
        if(arguments.length != command.getNumArguments()) {
            System.out.println("Wrong number of arguments!");
            System.out.println(command.getHelp(true /* showUsage */));
            return ExitCode.FAILURE;
        }
        
        // found a matching command, and number of arguments, so let them in to the party
        String[] passedArgs = new String[command.getNumArguments() - 1];
        for(int i = 1; i < arguments.length; ++i) {
            passedArgs[i - 1] = arguments[i];
        }
                
        return command.doAction(passedArgs, this);
    }
    
    /**
     * Sets up all of the commands to be interpreted in this session.
     */
    private void initCommands() {
        commands = new HashMap<String, Command>();

        // sets up all of the commands accepted by the admin terminal
        commands.put(UserAddCommand.keyword, new UserAddCommand());
        commands.put(UserRemoveCommand.keyword, new UserRemoveCommand());
        commands.put(HelpCommand.keyword, new HelpCommand());
        commands.put(ExitCommand.keyword, new ExitCommand());
        commands.put(PasswdCommand.keyword, new PasswdCommand());
        commands.put(UserReportCommand.keyword, new UserReportCommand());
        commands.put(SharedCommand.keyword, new SharedCommand());
        commands.put(ItemCommand.keyword, new ItemCommand());
        commands.put(ItemDeleteCommand.keyword, new ItemDeleteCommand());
        commands.put(BlankCommand.keyword, new BlankCommand());
        commands.put(SharingCommand.keyword, new SharingCommand());
        commands.put(ShareItemCommand.keyword, new ShareItemCommand());
        commands.put(UnshareItemCommand.keyword, new UnshareItemCommand());
        commands.put(ItemsCommand.keyword, new ItemsCommand());
        commands.put(InventoryCommand.keyword, new InventoryCommand());
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public HashSet<Item> getItems() {
        return fridge;
    }
    
    /**
     * Find an Item in the fridge by its name. Note: This method will shamelessly
     * return null if it's not found.
     * 
     * TODO dcodeh consider adding another hashmap for itemname to item for faster
     * lookup...although RAM may not be plentiful
     * 
     * @param itemName the name of the Item to search for
     * @return The Item if found, null otherwise
     */
    public Item getItemByName(String itemName) {
        // we gotta search...UGH

        for(Item i : fridge) {
            if(i.getName().equals(itemName)) {
                // just get outta here!
                return i;
            }
        }
        
        // welp, didn't find it
        return null;
    }
    
}
