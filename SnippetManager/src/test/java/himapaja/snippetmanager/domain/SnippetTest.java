package himapaja.snippetmanager.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karpo
 */
public class SnippetTest {
    
    public SnippetTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void snippetDataOutputRight() {
         Snippet tester = new Snippet(3, "testing", "best code ever");
         assertEquals("3-,-testing-,-best code ever", tester.data());
     }
}
