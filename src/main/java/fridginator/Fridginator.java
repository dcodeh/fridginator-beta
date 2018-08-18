/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package fridginator;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import appl.Fridge;
import console.Command.ExitCode;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;
import ui.WebServer;

public final class Fridginator {
    public final static Logger log = Logger.getLogger(Fridginator.class.getName());
	
    // entry point for fridinator
	public static void main(String[] args) {
	    
	    initializeLogging();
	    
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
	    
	    // do all the fancy web initialization stuffs here
	    final TemplateEngine templateEngine = new FreeMarkerEngine();
	    final WebServer webServer = new WebServer(f, templateEngine);
	    final Fridginator fridginator = new Fridginator(webServer);
	    fridginator.start();
	    waitForWebServerStartup();
	    
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
		webServer.terminate(); // goodbye, jetty
	}
	
	/**
	 * Just block until Jetty dumps is garbage in stdout
	 */
	private static void waitForWebServerStartup() {
	    System.out.println("Welcome to Fridginator.");
	    System.out.println("Waiting for Jetty to initialize...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.err.println("Oi! I was sleeping!!1!");
        }
        System.out.println("Done.");
    }

    private static void initializeLogging() {
	    try {
	        ClassLoader classLoader = Fridginator.class.getClassLoader();
	        final InputStream logConfig = classLoader.getResourceAsStream("log.properties");
	        LogManager.getLogManager().readConfiguration(logConfig);
	    } catch (Exception e) {
	        System.err.println("Couldn't initialize the log manager!!");
	        e.printStackTrace();
	    }
    }

    // attribute of this class
	private final WebServer webServer;
	
	/**
	 * Seems gross, but this is how the WebServer gets started.
	 * @param webServer The WebServer object to use as a, well, WebServer
	 */
	private Fridginator(final WebServer webServer) {
	    this.webServer = webServer;
	}
	
	/**
	 * Spins up the web server
	 */
	private void start() {
	    webServer.initialize();
	}
	
}
