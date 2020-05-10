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
    private Language uusiKieli;

    @Before
    public void setUp() {
        this.ld = new SqlLanguageDao(tiedosto);
        uusiKieli = new Language("Pori");
        ld.create(uusiKieli);
        this.sd = new SqlSnippetDao(tiedosto);

    }
//
    @After
    public void tearDown() {
        File poista = new File(tiedosto + ".mv.db");
        poista.delete();
        poista = new File(tiedosto + ".trace.db");
        poista.delete();
    }

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

        assertEquals(0, sd.getAll(-1).size());
    }

    @Test
    public void sqlSnippetCreatedIncreasesGetAll() {
        assertEquals(tiedosto, sd.getDbname());
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        sd.create(new Snippet(uusiKieli, "testeri", "Koodii", lista));
        assertEquals(1, sd.getAll(-1).size());
        
    }

    @Test
    public void sqlSnippetCreatesWithCorrectInfo() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        s = sd.getAll(1).get(0);
        assertEquals(1, s.getLanguageId());
        assertEquals("testeri", s.getName());
        assertEquals("Koodi", s.getCode());
        assertEquals(lista, s.getTags());
    }

    @Test
    public void sqlSnippetFindByTitleFinds() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1, sd.findByTitle("testeri", -1).size());
    }

    @Test
    public void sqlSnippetFindByTitleFindsRightAmountWithRightLanguage() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(uusiKieli, "testeri 2", "piip poop", new ArrayList<>()));
        sd.create(new Snippet(uusiKieli, "testeri 3", "piip poop", new ArrayList<>()));
        assertEquals(3, sd.findByTitle("testeri", 1).size());
    }

    @Test
    public void sqlSnippetFindByTitleFindsWithRightLanguage() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1, sd.findByTitle("testeri", 1).size());
    }

    @Test
    public void sqlSnippetFindByTitleFindsNoneWithBadInfo() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(0, sd.findByTitle("Koodi", -1).size());
    }

    @Test
    public void sqlSnippetFindByTagFinds() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(1, sd.findByTag("koira", -1).size());
    }

    @Test
    public void sqlSnippetFindByTagFindsAllRightOnes() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(uusiKieli, "testeri 2", "piip poop", lista));
        sd.create(new Snippet(uusiKieli, "testeri 2", "piip poop", new ArrayList<>()));
        sd.create(new Snippet(uusiKieli, "testeri 3", "piip poop", lista));
        sd.create(new Snippet(uusiKieli, "testeri 4", "piip poop", lista));
        assertEquals(4, sd.findByTag("koira", -1).size());
    }

    @Test
    public void sqlSnippetFindByTagFindsNoneWithBadInfo() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        assertEquals(0, sd.findByTag("marsu", -1).size());
    }

    @Test
    public void sqlSnippetGetByIdFindsRight() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(uusiKieli, "turhake", "piip poop", new ArrayList<>()));
        assertEquals("testeri", sd.getById(1).getName());
    }

    @Test
    public void sqlSnippetFindByTitleAndTag() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        sd.create(new Snippet(uusiKieli, "turhake", "piip poop", new ArrayList<>()));
        assertEquals("testeri", sd.findByTitleAndTag("testeri", "koira", -1).get(0).getName());
    }
    
    @Test
    public void sqlSnippetUpdateChangesData() {
        List<String> lista = new ArrayList<>();
        lista.add("koira");
        lista.add("kissa");
        Snippet s = new Snippet(uusiKieli, "testeri", "Koodi", lista);
        sd.create(s);
        s.setName("muutettuNimi");
        s.setCode("muutettua koodia");
        sd.update(s);
        assertEquals("muutettuNimi", sd.getById(1).getName());
        assertEquals("muutettua koodia", sd.getById(1).getCode());
    }
    
//    @Test
//    public void sqlSnippetDeleteDeletesExisting() {
//        List<String> lista = new ArrayList<>();
//        lista.add("koira");
//        lista.add("kissa");
//        Snippet s = new Snippet(1, uusiKieli, "testeri", "Koodi", lista);
//        sd.create(s);
//        assertEquals(1, sd.findByTag("koira", -1).size());
//        sd.delete(s);
//        assertEquals(0, sd.findByTag("koira", -1).size());
//        
//    }
}
