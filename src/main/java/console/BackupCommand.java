/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import appl.Fridge;

/**
 * Write the state of the fridge to /opt/fridginator/{date}.ser
 * @author dcodeh
 */
public class BackupCommand extends Command {

    private static final String help = "Write the state of the fridge to /opt/fridginator/{date}.ser";
    public static final String keyword = "backup";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public BackupCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String fridginatorPath = "/opt/fridginator/";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("EST"));
        fridginatorPath += format.format(date) + ".ser";

        // save the state of the fridge
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        try {
            fileOut = new FileOutputStream(fridginatorPath);
            objOut = new ObjectOutputStream(fileOut);
            
            objOut.writeObject(fridge);
            System.out.println("Backed up as '" + fridginatorPath + "'");
            
        } catch (IOException ioe) {
            System.err.println("Couldn't save the state of the fridge!");
            System.err.println("If you see Cody, tell him " + ioe.getMessage());
        } finally {
            try { 
                objOut.close(); 
            } catch (Exception e) {
                System.err.println("tried closing a resource that was null. Nothing to see here.");
            }
            
            try {
                fileOut.close();
            } catch (Exception e) {
                System.err.println("tried closing a resource that was null. Nothing exciting here, move on with your day");
            }
            
        }
        return ExitCode.SUCCESS; // goobye
    }

}
