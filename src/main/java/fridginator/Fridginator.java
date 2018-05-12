package fridginator;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Logger;

import appl.Fridge;
import console.Command.ExitCode;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

public final class Fridginator {
    public final static Logger log = Logger.getLogger(Fridginator.class.getName());
    
    // for rendering Views
    final TemplateEngine templateEngine = new FreeMarkerEngine();
	
    // entry point for fridinator
	public static void main(String[] args) {
	    
	    Fridge f;
	    String fridginatorPath = "/opt/fridginator/fridge.ser";
	    // load the state of the fridge
	    FileInputStream fileIn = null;
	    ObjectInputStream objIn = null;
	    
	    try {
	        fileIn = new FileInputStream(fridginatorPath);
	        objIn = new ObjectInputStream(fileIn);
	        
	        f = (Fridge) objIn.readObject();
	        
	    } catch (ClassNotFoundException cnfe) {
	        log.warning("What is this garbage stream you gave me!?");
	        log.config("Starting a fresh fridge");
	        log.warning("If you see Cody, tell him " + cnfe.getMessage());
	        f = new Fridge();
	    } catch (IOException ioe) {
	        log.warning("Couldn't load the state of the fridge in /opt/fridginator/frdge.ser!");
	        log.config("Starting a fresh fridge");
	        log.warning("If you see Cody, tell him" + ioe.getMessage());
	        f = new Fridge();
	    } finally {
	        try {
	            fileIn.close();
	        } catch (Exception e) {
	            log.finest("tried closing a resource that was null. The situation is under control");
	        }
	        
	        try {
	            objIn.close();
	        } catch (Exception e) {
	            log.finest("tried closing a resource that was null. No need to panic");
	        }
	    }	    
	    
		System.out.println("Enter commands:");
		
		Scanner stdin = new Scanner(System.in);

		while(true) { // mature
	        System.out.print("] ");
	        if(f.interpretCommands(stdin.nextLine()) == ExitCode.QUIT) {
	            break;
	        }
	    }
	    
	    // save the state of the fridge
	    FileOutputStream fileOut = null;
	    ObjectOutputStream objOut = null;
	    try {
	        fileOut = new FileOutputStream(fridginatorPath);
	        objOut = new ObjectOutputStream(fileOut);
	        
	        objOut.writeObject(f);
	        
	    } catch (IOException ioe) {
	        log.warning("Couldn't save the state of the fridge!");
	        log.warning("If you see Cody, tell him " + ioe.getMessage());
	    } finally {
	        try { 
                objOut.close(); 
	        } catch (Exception e) {
	            log.finest("tried closing a resource that was null. Nothing to see here.");
	        }
	        
	        try {
	            fileOut.close();
	        } catch (Exception e) {
	            log.finest("tried closing a resource that was null. Nothing exciting here, move on with your day");
	        }
            
	    }
		
		stdin.close();
	}
	
}
