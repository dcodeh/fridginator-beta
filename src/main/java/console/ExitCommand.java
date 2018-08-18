/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;

/**
 * Closes stdin and ends the session
 * @author dcodeh
 *
 */
public class ExitCommand extends Command {

    private static final String help = "Fine. Leave.";
    public static final String keyword = "exit";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public ExitCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        return ExitCode.QUIT; // goobye
    }

}
