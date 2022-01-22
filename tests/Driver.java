package tests;

import java.util.ArrayList;

import game.LicensePlateGameSolver;

public class Driver {
	
	public static final boolean NO_START = true;
	public static final boolean NO_END = true;
	public static final boolean SPACE_BETWEEN = true;
	
	public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must provide letters");
        } else {
    		LicensePlateGameSolver lpgs = new LicensePlateGameSolver();
    		ArrayList<String> answers = lpgs.solve(args[0]);
            System.out.println(args[1] + ", " + args[1].length());
            if (args.length >= 2 && args[1].equals("print"))
                for(String answer: answers) System.out.println(answer);
        }
	}
}
