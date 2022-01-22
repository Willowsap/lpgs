package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solver for the classic License Plate Game.
 * 
 * @author Willow Sapphire
 * @version 01/22/2022
 *
 */
public class LicensePlateGameSolver {
	
	/**
	 * Tournament Scrabble Dictionary Text File.
	 */
	public static final String DEFAULT_DICT_FILE = "game/dictionary.txt";
	
	public static final String DEFAULT_ANSWER_FILE = "answers.txt";
	/**
	 * Tournament Scrabble Dictionary ArrayList.
	 * Constructed from file.
	 */
	private ArrayList<String> dictionary;
	
	/**
	 * The file for the current dictionary.
	 */
	private String dictionaryFile;
	
	/**
	 * The file for the answers.
	 */
	private String answerFile;
	
	/**
	 * True requires that the first letter in the pattern not be the first letter in the word.
	 */
	private boolean noStart;
	
	/**
	 * True requires that the last latter come before the end of the word.
	 */
	private boolean noEnd;
	
	/**
	 * True requires that the letters not appear next to each other 
	 */
	private boolean spaceBetween;
	
	/**
	 * The first and last letters could still match the first and last lettesr in the pattern, as long as
	 * The word would still match the pattern with those letters removed
	 * Ex: "VANQUISHES" matches aqs even though it ends in an s since there is another s before it,
	 * ie It would still match if the word was "VANQUISHE" 
	 */
	
	
	/**
	 * No Arg Constructor
	 * Reads in the default dictionary and initializes the ArrayList
	 */
	public LicensePlateGameSolver() {
		this.dictionaryFile = DEFAULT_DICT_FILE;
		this.answerFile = DEFAULT_ANSWER_FILE;
		this.dictionary = readDictionary(DEFAULT_DICT_FILE);
		this.noStart = false;
		this.noEnd = false;
		this.spaceBetween = false;
	}
	
	/**
	 * Constructor
	 * Reads in a provided dictionary and initializes the ArrayList
	 */
	public LicensePlateGameSolver(String dictFile) {
		this.dictionaryFile = dictFile;
		this.answerFile = DEFAULT_ANSWER_FILE;
		this.dictionary = readDictionary(dictFile);
		this.noStart = false;
		this.noEnd = false;
		this.spaceBetween = false;
	}
	
	/**
	 * Constructor
	 * Reads in the dictionary and initializes the ArrayList
	 * Sets the optional variables
	 */
	public LicensePlateGameSolver(String dictFile, String answerFile, 
			boolean noStart, boolean noEnd, boolean spaceBetween) {
		this.dictionaryFile = dictFile;
		this.answerFile = DEFAULT_ANSWER_FILE;
		this.dictionary = readDictionary(dictFile);
		this.noStart = noStart;
		this.noEnd = noEnd;
		this.spaceBetween = spaceBetween;
	}
	
	/**
	 * Finds a word that contains 3 given letters in order anywhere in the word
	 * Example: "AQS" would match "antiques" (and more)
	 * 			"HVL" would match "chivalry" (and more)
	 * @param lettersWord - A string of at least 3 letters for which to find a matching word
	 * @return the list of answers
	 */
	public ArrayList<String> solve(String lettersWord) {
		if (lettersWord.length() < 3) {
			System.out.println("Search term must be at least 3 letters long");
			return null;
		}
		char[] letters = lettersWord.toCharArray();
		Pattern pattern = Pattern.compile(
				genPattern(letters),
				Pattern.CASE_INSENSITIVE);
		ArrayList<String> answers = new ArrayList<String>();
		for(String word: dictionary) {
			Matcher matcher = pattern.matcher(word);
			if(matcher.find()) answers.add(word);
		}
		if (answers.size() == 0) answers.add("No Solution");
		writeAnswers(answers, this.answerFile);
		return answers;
	}
	
	/**
	 * Creates a regular expression for the provided letters and conditions.
	 * @param letters - The letters which much exist in the word
	 * @param noStart - true requires that the first letter in the pattern not be the first letter in the word.
	 * @param noEnd - true requires that the last latter in the pattern not be the last letter in the word.
	 * @param spaceBetween - true requires no letters in the pattern can be adjacent.
	 * @return - A regular expression as a string.
	 */
	public String genPattern(char[] letters) {
		String allLetters = spaceBetween ? "[a-z]+" : "[a-z]*";
		String pattern = noStart ? "^[a-z]+" : "^[a-z]*";
		for(char c: letters) pattern += (c + allLetters);
		pattern += noEnd ? "[a-z]+$" : "$";
		return pattern;
	}
	
	/**
	 * Reads in the dictionary file and returns it as an ArrayList
	 * @return the ArrayList of words.
	 */
	public ArrayList<String> readDictionary(String dictFile) {
		ArrayList<String> dictionary = new ArrayList<String>();
		try {
			Scanner d = new Scanner(new File(dictFile));
			while(d.hasNext()) dictionary.add(d.next());
		} catch (FileNotFoundException e) {
			System.out.println("Exception while creating dictionary");
			e.printStackTrace();
		}
		return dictionary;
	}
	
	/**
	 * Writes the answers to a file
	 * @param answers - the words to write to the file
	 * @param answerFileName - the name of the file to write to
	 */
	public void writeAnswers(ArrayList<String> answers, String answerFileName) {
		try {
			File answerFile = new File(answerFileName);
			if(answerFile.createNewFile()) System.out.println(answerFile.getName());
		    FileWriter answerFileWriter = new FileWriter(answerFile);
		    for (String answer: answers) {
		    	answerFileWriter.append(answer + "\n");  
		    }
		    answerFileWriter.close();
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
		    e.printStackTrace();
	    }
	}
	
	/**
	 * Getter method for the dictionary
	 * @return the ArrayList dictionary
	 */
	public ArrayList<String> getDictionary() {
		return dictionary;
	}
	
	/**
	 * Getter method for noStart
	 * @return noStart
	 */
	public boolean getNoStart() {
		return noStart;
	}
	
	/**
	 * Getter method for noEnd
	 * @return
	 */
	public boolean getNoEnd() {
		return noEnd;
	}
	
	/**
	 * Getter method for spaceBetween
	 * @return spaceBetween
	 */
	public boolean getSpaceBetween() {
		return spaceBetween;
	}
	
	/**
	 * Getter method for the dictionary file
	 * @return dictionaryFile
	 */
	public String getDictionaryFile() {
		return this.dictionaryFile;
	}
	
	/**
	 * Getter method for the answerFile
	 * @return answerFile
	 */
	public String getAnswerFile() {
		return this.answerFile;
	}
	
	/**
	 * Setter method for the dictionary.
	 * Updates the dictionary file and reads in the new one
	 * @param newDictFile - the new dictionary file
	 */
	public void setDictionary(String newDictFile) {
		this.dictionaryFile = newDictFile;
		this.dictionary = readDictionary(newDictFile);
	}
	
	/**
	 * Setter method for noStart
	 * @param noStart - whether or not to require no start
	 */
	public void setNoStart(boolean noStart ) {
		this.noStart = noStart;
	}
	
	/**
	 * Setter method for noEnd
	 * @param noEnd - whether or not to require no end
	 */
	public void setNoEnd(boolean noEnd) {
		this.noEnd = noEnd;
	}
	
	/**
	 * Setter method for spaceBetween
	 * @param spaceBetween - whether or not to require space between
	 */
	public void setSpaceBetween(boolean spaceBetween) {
		this.spaceBetween = spaceBetween;
	}
	
	/**
	 * Setter method for answerFile
	 * @param answerFile - the new answer file
	 */
	public void setAnswerFile(String answerFile) {
		this.answerFile = answerFile;
	}
}
