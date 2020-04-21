package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.logic.LanguageService;
import himapaja.snippetmanager.dao.FileLanguageDao;
import java.util.ArrayList;
import java.util.List;
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

    private String file = "testitiedosto.txt"; //snippets
    private String file2 = "testitiedosto2.txt"; //languages

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
    public void snippetDataOutputCorrect() {
        Snippet tester = new Snippet(3, 256,"basic", "testing", "best code ever");
        assertEquals("3-,-256-,-testing-,-best code ever-,-[]", tester.data());
    }

    @Test
    public void snippetFileConstructorIdNameLIdCode() throws Exception {
        String rivi = "0-,-0-,-hello world-,-System.out.println(\"hello world!\");-,-[hello, world]";
        Snippet testeri = new Snippet(rivi, new LanguageService(new FileLanguageDao(file2)));
        assertEquals(0, testeri.getId());
        assertEquals(0, testeri.getLanguageId());
        assertEquals("hello world", testeri.getName());
        assertEquals("System.out.println(\"hello world!\");", testeri.getCode());
    }
    
    @Test
    public void snippetToTextUIFormatRight() {
        Snippet tester = new Snippet(3, 256,"basic", "testing", "best code ever");
        String tulostus = tester.textUIString();
        assertEquals("Name: testing", tulostus.substring(0, 13));
        assertEquals("     Code: best code ever", tulostus.substring(14)); //6 tyhjää?
    }
    //ensin hupia testi saanko tagi listan oikein ulos, helpompi löytää vika kahdessa seuraavassa
    @Test
    public void snippetGivesTags() {
        List<String> lista = new ArrayList<>();
        lista.add("hello");
        lista.add("world");
        Snippet tester = new Snippet(3, 256, "basic","testing", "best code ever", lista);
        assertEquals(2, tester.getTags().size());
        assertEquals("hello", tester.getTags().get(0));
        assertEquals("world", tester.getTags().get(1));
    }
    
    @Test
    public void snippetCanAddSingleTag() {
        List<String> lista = new ArrayList<>();
        lista.add("hello");
        lista.add("world");
        Snippet tester = new Snippet(3, 256, "basic","testing", "best code ever", lista);
        tester.addTag("bye");
        assertEquals("bye", tester.getTags().get(2));
    }
    
    @Test
    public void snippetCanRemoveSingleTag() {
        List<String> lista = new ArrayList<>();
        lista.add("hello");
        lista.add("world");
        Snippet tester = new Snippet(3, 256, "basic", "testing", "best code ever", lista);
        tester.removeTag("hello");
        assertEquals(1, tester.getTags().size());
        assertEquals("world", tester.getTags().get(0));
    }
    
    @Test
    public void snippetCantAddDuplicateTag() {
        List<String> lista = new ArrayList<>();
        lista.add("hello");
        lista.add("world");
        Snippet tester = new Snippet(3, 256, "basic", "testing", "best code ever", lista);
        tester.addTag("hello");
        assertEquals(2, tester.getTags().size());
    }
}
