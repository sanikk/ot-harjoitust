package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlSnippetDaoTest {

    private final String tiedosto = "testitiedosto";
    private SqlSnippetDao sd;
    private SqlLanguageDao ld;

//    public SqlSnippetDaoTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//
//    }
//
//    @After
//    public void tearDown() {
//        File poista = new File(tiedosto + ".mv.db");
//        poista.delete();
//        poista = new File(tiedosto + ".trace.db");
//        poista.delete();
//    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void sqlSnippetUsesCorrectDefaultFileIfItExists() {
        sd = new SqlSnippetDao();
        assertEquals("snippetdb", sd.getDbname());
    }

    @Test
    public void sqlSnippetReturnsEmptyGetAllFromEmptyDB() {
        this.ld = new SqlLanguageDao(tiedosto);
        this.sd = new SqlSnippetDao(tiedosto);
        
        assertEquals(0, sd.getAll(-1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }

    @Test
    public void sqlSnippetCreatedIncreasesGetAll() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        assertEquals(tiedosto, sd.getDbname());
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        sd.create(new Snippet(1, "testeri", "Koodii", lista));
        assertEquals(1, sd.getAll(-1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }

    @Test
    public void sqlSnippetCreatesWithCorrectInfo() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        s = sd.getAll(1).get(0);
        assertEquals(1, s.getLanguageId());
        assertEquals("testeri", s.getName());
        assertEquals("Koodi", s.getCode());
        assertEquals(lista, s.getTags());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    
    @Test
    public void sqlSnippetFindByTitleFinds() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1,sd.findByTitle("testeri", -1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTitleFindsRightAmountWithRightLanguage() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(1, "testeri 2", "piip poop", new ArrayList<>()));
        sd.create(new Snippet(1, "testeri 3", "piip poop", new ArrayList<>()));
        assertEquals(3,sd.findByTitle("testeri", 1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTitleFindsWithRightLanguage() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1,sd.findByTitle("testeri", 1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTitleFindsNoneWithBadInfo() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(0,sd.findByTitle("Koodi", -1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTagFinds() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1,sd.findByTag("koira", -1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTagFindsAllRightOnes() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(1, "testeri 2", "piip poop", lista));
        sd.create(new Snippet(1, "testeri 2", "piip poop", new ArrayList<>()));
        sd.create(new Snippet(1, "testeri 3", "piip poop", lista));
        sd.create(new Snippet(1, "testeri 4", "piip poop", lista));
        assertEquals(4,sd.findByTag("koira", -1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTagFindsNoneWithBadInfo() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(0,sd.findByTag("marsu", -1).size());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetGetByIdFindsRight() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(1, "turhake", "piip poop", new ArrayList<>()));
        assertEquals("testeri",sd.getById(1).getName());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
    @Test
    public void sqlSnippetFindByTitleAndTag() {
        this.ld = new SqlLanguageDao(tiedosto);
        ld.create(new Language("Pori"));
        this.sd = new SqlSnippetDao(tiedosto);
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(1, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(1, "turhake", "piip poop", new ArrayList<>()));
        assertEquals("testeri",sd.findByTitleAndTag("testeri", "koira", -1).get(0).getName());
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }
}
