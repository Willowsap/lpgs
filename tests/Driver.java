package tests;

import java.util.ArrayList;

import game.LicensePlateGameSolver;

public class Driver {
	
	public static final boolean NO_START = true;
	public static final boolean NO_END = true;
	public static final boolean SPACE_BETWEEN = true;
	public static final String LETTERS = "hbd";
	
	public static void main(String[] args) {
		LicensePlateGameSolver lpgs = new LicensePlateGameSolver();
		ArrayList<String> answers = lpgs.solve(LETTERS);
		//for(String answer: answers) {
			//System.out.println(answer);
		//}
	}
}
