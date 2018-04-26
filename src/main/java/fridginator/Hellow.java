package fridginator;

import java.util.Scanner;

import appl.Fridge;
import console.Command.ExitCode;

// import static spark.Spark.*;

public class Hellow {
	
	public static void main(String[] args) {
		System.out.println("Enter commands:");
		
		Scanner stdin = new Scanner(System.in);
		
		Fridge f = new Fridge();
		
	    
	    while(true) { // mature
	        System.out.print("] ");
	        if(f.interpretCommands(stdin.nextLine()) == ExitCode.QUIT) {
	            break;
	        }
	    }
		
		stdin.close();
	}
	
}
