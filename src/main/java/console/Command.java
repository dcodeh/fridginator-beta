package console;

import java.util.Objects;

import appl.Fridge;
import fridginator.Constants;

/**
 * Represents a general console command that the administrator can enter to change the system.
 * @author dcodeh
 *
 */
public abstract class Command {
    
     /**
      * Let the interpreter know how running the command went
      */
    public enum ExitCode {
        FAILURE, /** Something blew up :( */ 
        SUCCESS, /** Nothing blew up! */
    }
    
    /**
     * The keyword used to invoke the command...for example 'ls' to list
     * the contents of a directory in unix
     */
    private String keyword;
    
    /**
     * The required command line arguments, separated by spaces
     */
    protected String[] arguments;
    
    /**
     * The help text to display if the user doesn't smash the right keys in the right order
     */
    private String help;
    
    public Command(String help, String keyword, String...arguments) {
        this.help = help;
        this.keyword = keyword;
        this.arguments = arguments;
    }
    
    /**
     * @return This command's keyword
     */
    public String getKeyword() {
        return this.keyword;
    }
    
    /**
     * @return This command's arguments
     */
    public String[] getArguments() {
        return this.arguments;
    }
    
    /**
     * @return this command's help message
     */
    public String getHelp() {
        
        String usage = Constants.GENERAL_USAGE_STRING + " ";
        for(String s : getArguments()) {
            usage.concat(" " + s + " ");
        }
        
        return this.help + "\n" + usage;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Command) {
            Command that = (Command) o;
            
            return this.getKeyword().equals(that.getKeyword()) &&
                   this.getArguments().equals(that.getArguments());
        } else {
            return false;
        }
    }
    
    @Override 
    public int hashCode() {
        return Objects.hash(keyword); // no collisions -- guaranteed!
    }
    
    /**
     * @return The total number of commands on the line (including keyword)
     * 
     * For example, the following command has 3 arguments
     * $ useradd picard engage
     */
    public int getNumArguments() {
        return arguments.length + 1;
    }
    
    public void replaceArguments(String[] arguments) {
        this.arguments = arguments;
    }
    
    /**
     * This method is called by the interpreter when the command has been invoked by
     * the user. 
     * 
     * It should do the stuff.
     * 
     * AThe proper number of arguments will be validated prior to calling this method.
     * However, argument validation will need to be handled here.
     * @param fridge The fridge to perform the actions on 
     */
    public abstract ExitCode doAction(String[] args, Fridge fridge);
    
}
