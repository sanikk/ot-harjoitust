package himapaja.snippetmanager;

import himapaja.snippetmanager.Dao.FileLanguageDao;
import himapaja.snippetmanager.domain.Language;
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
public class FileLanguageDaoTest {

    // class = FileLanguageDao, jota testataan
    // given file = tiedosto jonka nimen FileLanguageDao saa syötteenä
    private FileLanguageDao fileLanguageDao;

    @Before
    public void setUp() {
        this.fileLanguageDao = new FileLanguageDao("testitiedosto.txt");

    }

    @After
    public void tearDown() {
        File siivous = new File("testitiedosto.txt");
        siivous.delete();
    }

    @Test
    public void givenFileIsCreatedIfMissing() {
        fileLanguageDao.create(new Language("testikieli"));
        File test = new File("testitiedosto.txt");
        assertEquals(true, test.exists());
    }

    @Test
    public void classWritesToGivenFile() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File("testitiedosto.txt"));
        assertTrue(tiedostonlukija.hasNextLine());
    }

    @Test
    public void writingOneLanguageIncreasesNextIndex() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File("testitiedosto.txt"));
        assertEquals(1, fileLanguageDao.nextId());
    }

    @Test
    public void classWritesOneLanguageToFileCorrectly() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File("testitiedosto.txt"));
        assertEquals("1", tiedostonlukija.nextLine());
        assertEquals("testikieli,0", tiedostonlukija.nextLine());
        assertTrue(!tiedostonlukija.hasNext());
    }

    @Test
    public void classWritesManyLanguagesToFileCorrectly() throws Exception {
        for (int i = 1; i < 11; i++) {
            fileLanguageDao.create(new Language("testikieli" + i));
        }
        Scanner tiedostonlukija = new Scanner(new File("testitiedosto.txt"));
        assertEquals("10", tiedostonlukija.nextLine());
        for (int i = 1; i < 11; i++) {
            String haetaan = "testikieli"+i+","+(i-1);
            assertEquals(haetaan, tiedostonlukija.nextLine());
        }
        assertTrue(!tiedostonlukija.hasNext());
    }

}
