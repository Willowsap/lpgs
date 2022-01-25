package tests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

import game.LicensePlateGameSolver;

public class LicensePlateGameSolverTest {

    /**
     * Location of the default answer file.
     */
    public static final String DEFAULT_ANSWER_FILE = "answers.txt";

    /**
     * Test answers used in tests.
     */
    public static final String[] TEST_ANSWERS = {
        "answer1",    
        "answer2",    
        "answer3",    
        "answer4",    
        "answer5",    
    };

    LicensePlateGameSolver lpgs;
    
    /*******************
     * LIFECYCLE HOOKS.
     *******************
     * it is convention to name lifecycle hooks the same
     * as their annotation (beforeEach, afterEach, etc)
     * but this is NOT required. Junit only knows what the 
     * function is due to the annotation, not the function name.
     */
    
    /**
     * Create an instance of the solver to be used by each test.
     */
    @BeforeEach
    public void beforeEach() {
        lpgs = new LicensePlateGameSolver();
    }
    
    /**
     * Delete the answer file if a test created one.
     * Only delete the default answer file.
     * If a test creates an answer file somewhere else,
     * it will be responsible for deleting it.
     */
    @AfterEach
    public void afterEach() {
        File answerFile = new File(DEFAULT_ANSWER_FILE);
        if (answerFile.exists()) {
            answerFile.delete();
        }
    }
    
    /***************
     * JUNIT TESTS
     ***************/
    
    /**
     * Tests the noStart setter function.
     * Also tangentially tests getNoStart and part
     * of the default constructor.
     */
    @Test
    public void testSetNoStart() {
        // default constructor should set noStart to false
        assertFalse(lpgs.getNoStart());
        // do something with a solver
        lpgs.setNoStart(true);
        // assert something about what it did
        assertTrue(lpgs.getNoStart());
    }

    /**
     * Tests the writeAnswers method.
     * Writes the test answers to a file,
     * then reads in that file and check that the
     * answers were correctly writte.
     */
    @Test
    public void testWriteAnswers() {
        lpgs.writeAnswers(new ArrayList<String>(Arrays.asList(TEST_ANSWERS)));
        try {
            Scanner s = new Scanner(new File(DEFAULT_ANSWER_FILE));
            for (String a : TEST_ANSWERS) {
                // If it runs out of lines before all the answers have
                // been checked, there are not enough lines
                if (!s.hasNext()) {
                    fail("not enough answers in the file");
                }
                // the ith line should equal the ith answer
                assertEquals(a, s.next());
            }
            // there should not not be any lines in the answer file
            // after the 5 answers
            assertFalse(s.hasNext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This test is mediocre
     * It doesn't fully test the solve method
     * It could easily pass while the solve method still has bugs
     * That being said, tests like this can be a good starting point
     */
    @Test
    public void testOneSolveScenario() {
        ArrayList<String> answers = lpgs.solve("glw");
        assertTrue(answers.contains("GLOW"));
        assertFalse(answers.contains("HELLO"));
    }
}
