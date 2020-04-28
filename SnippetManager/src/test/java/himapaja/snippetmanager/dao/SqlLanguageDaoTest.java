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
}
