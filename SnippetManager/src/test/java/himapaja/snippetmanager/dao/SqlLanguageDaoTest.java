package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Language;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlLanguageDaoTest {
    
    private final String tiedosto = "testitiedosto";
    
    

     @Test
     public void sqlLanguageConstructorCreatesDBFileGivenAsParameter() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         File siivottava = new File(tiedosto + ".mv.db");
         assertEquals(true, siivottava.exists());
         siivottava.delete();
         assertEquals(false, siivottava.exists());
     }
     
     @Test
     public void sqlLanguageCreateLanguageIncreasesList() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         sd.create(new Language("Pori"));
         assertEquals(1, sd.getAll().size());
         File siivottava = new File(tiedosto + ".mv.db");
         siivottava.delete();
     }
     
     @Test
     public void sqlLanguageGetByIdGetsExisting() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         Language uusi = new Language("Pori", 1);
         sd.create(uusi);
         assertEquals("Pori", sd.getById(1).getName());
         assertEquals(1, sd.getById(1).getId());
         File siivottava = new File(tiedosto + ".mv.db");
         siivottava.delete();
     }
     @Test
     public void sqlLanguageGetByIdReturnsNullIfNotFound() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         Language uusi = new Language("Pori", 1);
         sd.create(uusi);
         assertEquals(null, sd.getById(2));
         File siivottava = new File(tiedosto + ".mv.db");
         siivottava.delete();
     }
     
     @Test
     public void sqlLanguageIdToStringReturnsRightString() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         Language uusi = new Language("Pori", 1);
         sd.create(uusi);
         assertEquals("Pori", sd.idToString(1));
         File siivottava = new File(tiedosto + ".mv.db");
         siivottava.delete();
     }
}
