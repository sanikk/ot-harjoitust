package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.util.Scanner;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Samuli Nikkilä
 */
public class FileSnippetDaoTest {

    private FileSnippetDao fileSnippetDao;
    private String file = "testitiedosto.txt";

    @Before
    public void setUp() {
        fileSnippetDao = new FileSnippetDao(file);
    }

    @After
    public void tearDown() {
        File siivous = new File(file);
        siivous.delete();
    }

    @Test
    public void givenFileIsCreatedIfMissing() {
        fileSnippetDao.create(new Snippet(1024, "testisnippet", "leikkikoodi-on-tässä"));
        File test = new File(file);
        assertEquals(true, test.exists());
    }

    @Test
    public void classWritesToGivenFile() throws Exception {
        fileSnippetDao.create(new Snippet(1024, "testisnippet", "leikkikoodi-on-tässä"));
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertTrue(tiedostonlukija.hasNextLine());
    }

    @Test
    public void writingOneSnippetIncreasesNextIndex() throws Exception {
        fileSnippetDao.create(new Snippet(1024, "testisnippet", "leikkikoodi-on-tässä"));
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertEquals(1, fileSnippetDao.giveNextId());
    }

    @Test
    public void classWritesOneSnippetToFileCorrectly() throws Exception {
        fileSnippetDao.create(new Snippet(1024, "testisnippet", "leikkikoodi-on-tässä"));
        Scanner tiedostonlukija = new Scanner(new File("testitiedosto.txt"));
        assertEquals("1", tiedostonlukija.nextLine());
        assertEquals("0-,-1024-,-testisnippet-,-leikkikoodi-on-tässä-,-[]", tiedostonlukija.nextLine());
        assertTrue(!tiedostonlukija.hasNext());
    }

    @Test
    public void classWritesManySnippetsToFileCorrectly() throws Exception {
        for (int i = 1; i < 11; i++) {
            fileSnippetDao.create(new Snippet(1024, "testisnippet" + i, "leikkikoodi-on-tässä"));
        }
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertEquals("10", tiedostonlukija.nextLine());
        for (int i = 1; i < 11; i++) {
            String haetaan = (i - 1) + "-,-1024-,-testisnippet" + i + "-,-leikkikoodi-on-tässä-,-[]";
            assertEquals(haetaan, tiedostonlukija.nextLine());
        }
        assertTrue(!tiedostonlukija.hasNext());
    }

}
