package fridginator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import appl.Fridge;
import console.Command.ExitCode;

// import static spark.Spark.*;

public class Hellow {
	
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
	        System.out.println("What is this garbage stream you're feeding me!?");
	        System.out.println("Starting fresh");
	        System.out.println("If you see Cody, tell him");
	        cnfe.printStackTrace();
	        f = new Fridge();
	    } catch (IOException ioe) {
	        System.out.println("Could not load the state of the last fridge!");
	        System.out.println("Starting fresh");
	        System.out.println("If you see Cody, tell him");
	        ioe.printStackTrace();
	        f = new Fridge();
	    } finally {
	        try {
	            fileIn.close();
	        } catch (Exception e) {
	            System.out.println("fileIn wasn't open");
	        }
	        
	        try {
	            objIn.close();
	        } catch (Exception e) {
	            System.out.println("objIn wasn't open");
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
	        System.out.println("Could not serialize the fridge!");
	        ioe.printStackTrace();
	    } finally {
	        try { 
                objOut.close(); 
	        } catch (Exception e) {
	            System.out.println("objOut wasn't open");
	        }
	        
	        try {
	            fileOut.close();
	        } catch (Exception e) {
	            System.out.println("fileOut wasn't open");
	        }
            
	    }
		
		stdin.close();
	}
	
}
