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
 * Used by students in CS3667 to practice writing Junit Tests.
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

  /**
   * Default destination file for the answers.
   */
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
  private String answersFileName;

  /****************************************************************************
   * Option variables
   ****************************************************************************/

  /**
   * True requires that the first letter in the pattern not be
   * the first letter in the word.
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

  /****************************************************************************
   * ==== NOTE ABOUT noStart, noEnd, and spaceBetween ====
   *
   * The first and last letters could still match the first and last letters
   * in the pattern, as long as the word would still match the pattern with
   * those letters removed.
   *
   * Ex: "VANQUISHES" matches aqs even though it ends in an s since there is
   * another s before it. ie It would still match if the word was "VANQUISHE"
   ****************************************************************************/

  /**
   * No Arg Constructor
   * Reads in the default dictionary and initializes the ArrayList
   * Sets the answer file to the default answer file
   * Sets the option variables to false
   */
  public LicensePlateGameSolver() {
    this.dictionaryFile = DEFAULT_DICT_FILE;
    readDictionary();
    this.answersFileName = DEFAULT_ANSWER_FILE;
    this.noStart = this.noEnd = this.spaceBetween = false;
  }

  /**
   * Constructor
   * Reads in a provided dictionary and initializes the ArrayList
   * Sets the answer file to the default answer file
   * Sets the option variables to false
   * 
   * @param dictFile - The location of the dictionary file to use
   */
  public LicensePlateGameSolver(String dictFile) {
    this.dictionaryFile = dictFile;
    readDictionary();
    this.answersFileName = DEFAULT_ANSWER_FILE;
    this.noStart = this.noEnd = this.spaceBetween = false;
  }

  /**
   * Constructor
   * Reads in a provided dictionary and initializes the ArrayList
   * Sets the answer file to the default provided file
   * Sets the option variables to false
   * 
   * @param dictFile        - The location of the dictionary file to use
   * @param answersFileName - The location of the answers file to write to
   */
  public LicensePlateGameSolver(String dictFile, String answersFileName) {
    this.dictionaryFile = dictFile;
    readDictionary();
    this.answersFileName = answersFileName;
    this.noStart = this.noEnd = this.spaceBetween = false;
  }

  /**
   * Constructor
   * Reads in the dictionary provided and initializes the ArrayList
   * Sets the answer file to the provided filename
   * Sets the optional variables to the provided values
   * 
   * @param dictFile        - The location of the dictionary file to use
   * @param answersFileName - The location of the answers file to write to
   * @param noStart         - True to enable the no start option
   * @param noEnd           - True to enable the no end option
   * @param spaceBetween    - True to enable the spaceBetween option
   */
  public LicensePlateGameSolver(String dictFile, String answersFileName,
      boolean noStart, boolean noEnd, boolean spaceBetween) {
    this.dictionaryFile = dictFile;
    readDictionary();
    this.answersFileName = DEFAULT_ANSWER_FILE;
    this.noStart = noStart;
    this.noEnd = noEnd;
    this.spaceBetween = spaceBetween;
  }

  /**
   * Finds a word that contains 3 given letters in order anywhere in the word.
   * Example: "AQS" would match "antiques" (and more)
   *          "HVL" would match "chivalry" (and more)
   * 
   * @param lettersWord - A string of at least 3 letters for which to
   *                      find a matching word
   * @return the list of answers
   */
  public ArrayList<String> solve(String lettersWord) {
    if (lettersWord.length() < 3) {
      System.out.println("Search term must be at least 3 letters long");
      return null;
    }
    ArrayList<String> answers = new ArrayList<String>();
    Matcher matcher;
    Pattern pattern = Pattern.compile(
        genPattern(lettersWord.toCharArray()),
        Pattern.CASE_INSENSITIVE);
    for (String word : dictionary) {
      matcher = pattern.matcher(word);
      if (matcher.find()) answers.add(word);
    }
    if (answers.size() == 0) answers.add("No Solution");
    writeAnswers(answers);
    return answers;
  }

  /**
   * Creates a regular expression for the provided letters and conditions.
   * Uses noStart, noEnd, and spaceBetween fields.
   * 
   * @param letters - The letters which much exist in the word
   * @return - A regular expression as a string.
   */
  public String genPattern(char[] letters) {
    String pattern = noStart ? "^[a-z]+" : "^[a-z]*";
    for (char c : letters)
      pattern += (c + (spaceBetween ? "[a-z]+" : "[a-z]*"));
    return pattern += noEnd ? "[a-z]+$" : "$";
  }

  /**
   * Reads in the dictionary file and returns it as an ArrayList.
   * Uses the file stored in dictFile.
   */
  public void readDictionary() {
    this.dictionary = new ArrayList<String>();
    try {
      Scanner d = new Scanner(new File(this.dictionaryFile));
      while (d.hasNext()) this.dictionary.add(d.next());
    } catch (FileNotFoundException e) {
      System.out.println("Exception while creating dictionary");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Writes the answers to a file.
   * 
   * @param answers - the words to write to the file
   * Uses the output filename in answersFile
   */
  public void writeAnswers(ArrayList<String> answers) {
    try {
      File answersFile = new File(this.answersFileName);
      answersFile.createNewFile();
      FileWriter answersFileWriter = new FileWriter(answersFile);
      for (String answer : answers) answersFileWriter.append(answer + "\n");
      answersFileWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Getter method for the dictionary.
   * 
   * @return the ArrayList dictionary
   */
  public ArrayList<String> getDictionary() {
    return this.dictionary;
  }

  /**
   * Getter method for noStart.
   * 
   * @return noStart
   */
  public boolean getNoStart() {
    return this.noStart;
  }

  /**
   * Getter method for noEnd.
   * 
   * @return noEnd
   */
  public boolean getNoEnd() {
    return this.noEnd;
  }

  /**
   * Getter method for spaceBetween.
   * 
   * @return spaceBetween
   */
  public boolean getSpaceBetween() {
    return this.spaceBetween;
  }

  /**
   * Getter method for the dictionary file.
   * 
   * @return dictionaryFile
   */
  public String getDictionaryFile() {
    return this.dictionaryFile;
  }

  /**
   * Getter method for the answersFileName.
   * 
   * @return answersFileName
   */
  public String getanswersFileName() {
    return this.answersFileName;
  }

  /**
   * Setter method for the dictionary.
   * Updates the dictionary file and reads in the new one.
   * 
   * @param newDictFile - the new dictionary file
   */
  public void setDictionary(String newDictFile) {
    this.dictionaryFile = newDictFile;
    readDictionary();
  }

  /**
   * Setter method for noStart.
   * 
   * @param noStart - whether or not to require no start
   */
  public void setNoStart(boolean noStart) {
    this.noStart = noStart;
  }

  /**
   * Setter method for noEnd.
   * 
   * @param noEnd - whether or not to require no end
   */
  public void setNoEnd(boolean noEnd) {
    this.noEnd = noEnd;
  }

  /**
   * Setter method for spaceBetween.
   * 
   * @param spaceBetween - whether or not to require space between
   */
  public void setSpaceBetween(boolean spaceBetween) {
    this.spaceBetween = spaceBetween;
  }

  /**
   * Setter method for answersFileName.
   * 
   * @param answersFileName - the new answer file
   */
  public void setanswersFileName(String answersFileName) {
    this.answersFileName = answersFileName;
  }
}
