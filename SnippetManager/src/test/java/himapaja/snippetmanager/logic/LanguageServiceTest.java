package himapaja.snippetmanager.logic;

import himapaja.snippetmanager.dao.FileLanguageDao;
import himapaja.snippetmanager.dao.LanguageDao;
import himapaja.snippetmanager.dao.SqlLanguageDao;
import java.io.File;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Samuli Nikkilä
 */
public class LanguageServiceTest {

    private final String tiedosto = "testitiedosto";

    @Test
    public void langServiceWithFileCreatesFile() {
        LanguageService ls = new LanguageService(new FileLanguageDao(tiedosto + ".txt"));
        File siivottava = new File(tiedosto + ".txt");
        ls.createLanguage("testikieli"); //kirjoittaessa vasta luo
        assertEquals(true, siivottava.exists());
        siivottava.delete();
    }

    @Test
    public void langServiceWithFileCreatesLanguage() {
        LanguageService ls = new LanguageService(new FileLanguageDao(tiedosto + ".txt"));
        ls.createLanguage("testikieli"); //kirjoittaessa vasta luo
        ls = new LanguageService(new FileLanguageDao(tiedosto + ".txt")); //tehdään välissä uusi niin ei tule mistään cachesta(?)
        File siivottava = new File(tiedosto + ".txt");
        assertEquals("testikieli,0", ls.getLanguages().get(0).toString());
        siivottava.delete();
    }

    @Test
    public void langServiceSqlCreatesDB() {
        LanguageService ls = new LanguageService(new SqlLanguageDao(tiedosto));
        File siivottava = new File(tiedosto + ".mv.db");
        assertEquals(true, siivottava.exists());
        siivottava.delete();
    }

    @Test
    public void langServiceSqlGetByIdWorks() {
        LanguageService ls = new LanguageService(new SqlLanguageDao(tiedosto));
        File siivottava = new File(tiedosto + ".mv.db");
        ls.createLanguage("testikieli");
        assertEquals("testikieli,1", ls.getById(1).toString());
        siivottava.delete();
    }

//    @Test
//    public void hello3() {
//
//    }
}
