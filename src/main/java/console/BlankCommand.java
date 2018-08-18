/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;

/**
 * This does nothing, but it makes it so an empty string command is ignored...I'm so lazy!
 * @author dcodeh
 *
 */
public class BlankCommand extends Command {

    private static final String help = "Enter nothing...see what happens";
    public static final String keyword = "";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public BlankCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        return ExitCode.SUCCESS; // what a waste...
    }

}
