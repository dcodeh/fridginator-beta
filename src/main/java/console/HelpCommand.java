/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;
import fridginator.Constants;

/**
 * Displays help information for all other commands
 * @author dcodeh
 *
 */
public class HelpCommand extends Command {

    private static final String help = "How meta!";
    public static final String keyword = "help";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public HelpCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        System.out.println(Constants.HELP_MESSAGE);

        for(Command c : fridge.getCommands()) {
            System.out.print(c.getKeyword() + "\t\t - " + c.getHelp(false /* showUsage */) + "\n");
        }
        return ExitCode.SUCCESS; // literally can't mess up
    }

}
