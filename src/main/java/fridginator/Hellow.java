package fridginator;

import java.util.Scanner;

import appl.Fridge;

// import static spark.Spark.*;

public class Hellow {
	
	public static void main(String[] args) {
		System.out.println("Enter commands:");
		
		Scanner stdin = new Scanner(System.in);
		
		Fridge f = new Fridge();
		
		for(int i = 0; i < 5; i++) {
		    System.out.print("\n$ ");
		    f.interpretCommands(stdin.nextLine());
		}
		
		stdin.close();
	}
	
}
