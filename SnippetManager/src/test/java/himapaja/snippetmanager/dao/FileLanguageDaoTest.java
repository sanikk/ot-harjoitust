package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Language;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    private FileLanguageDao fileLanguageDao; // class = FileLanguageDao, jota testataan
    private String file = "testitiedosto.txt"; // given file = tiedosto jonka nimen FileLanguageDao saa syötteenä

    @Before
    public void setUp() {
        this.fileLanguageDao = new FileLanguageDao(file);

    }

    @After
    public void tearDown() {
        File siivous = new File(file);
        siivous.delete();
    }

    @Test
    public void givenFileIsCreatedIfMissing() {
        fileLanguageDao.create(new Language("testikieli"));
        File test = new File(file);
        assertEquals(true, test.exists());
    }

    @Test
    public void classWritesToGivenFile() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertTrue(tiedostonlukija.hasNextLine());
    }

    @Test
    public void writingOneLanguageIncreasesNextIndex() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertEquals(1, fileLanguageDao.giveNextId());
    }

    @Test
    public void classWritesOneLanguageToFileCorrectly() throws Exception {
        fileLanguageDao.create(new Language("testikieli"));
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertEquals("1", tiedostonlukija.nextLine());
        assertEquals("testikieli,0", tiedostonlukija.nextLine());
        assertTrue(!tiedostonlukija.hasNext());
    }

    @Test
    public void classWritesManyLanguagesToFileCorrectly() throws Exception {
        for (int i = 1; i < 11; i++) {
            fileLanguageDao.create(new Language("testikieli" + i));
        }
        Scanner tiedostonlukija = new Scanner(new File(file));
        assertEquals("10", tiedostonlukija.nextLine());
        for (int i = 1; i < 11; i++) {
            String haetaan = "testikieli" + i + "," + (i - 1);
            assertEquals(haetaan, tiedostonlukija.nextLine());
        }
        assertTrue(!tiedostonlukija.hasNext());
    }

    @Test
    public void classReturnsAList() {
        List<Language> verrattava = new ArrayList<>();
        assertEquals(verrattava, fileLanguageDao.getAll());
    }

    @Test
    public void classReturnsAListWithCorrectMembers() {
        for (int i = 1; i < 11; i++) {
            fileLanguageDao.create(new Language("testikieli" + i));
        }
        List<Language> verrattava = fileLanguageDao.getAll();
        for (int i = 1; i < 11; i++) {
            String haetaan = "testikieli" + i + "," + (i - 1);
            assertEquals(haetaan, verrattava.get(i - 1).toString());
        }
    }
}
