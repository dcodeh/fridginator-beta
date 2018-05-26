package console;

import appl.Fridge;

/**
 * This command triggers list generation, which will add items to users' lists
 * @author dcodeh
 *
 */
public class ListGenCommand extends Command {

    private static final String help = "Automatically add shared items to users' lists";
    public static final String keyword = "listgen";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public ListGenCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        fridge.generateSharedLists();
        System.out.println("Done.");
        return ExitCode.SUCCESS;
    }

}
