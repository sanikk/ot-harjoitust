package himapaja.snippetmanager.dao;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Samuli Nikkilä
 */
public class SqlLanguageDaoTest {
    
    private final String tiedosto = "testitiedosto";
    
    public SqlLanguageDaoTest() {
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


     @Test
     public void sqlLanguageConstructorCreatesDBFileGivenAsParameter() {
         SqlLanguageDao sd = new SqlLanguageDao(tiedosto);
         File siivottava = new File(tiedosto + ".mv.db");
         //System.out.println(siivottava.toString());
         System.out.println(siivottava.exists());
     }
}
