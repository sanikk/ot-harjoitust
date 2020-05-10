package himapaja.snippetmanager.logic;

import himapaja.snippetmanager.dao.FileLanguageDao;
import himapaja.snippetmanager.dao.FileSnippetDao;
import himapaja.snippetmanager.dao.LanguageDao;
import himapaja.snippetmanager.dao.SqlLanguageDao;
import himapaja.snippetmanager.dao.SqlSnippetDao;
import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.Snippet;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Sovelluslogiikasta vastaava luokka
 */
public class SnippetManager {

    private String dbmode = "";
    private LanguageService languageService;
    private SnippetService snippetService;
    private Language selected;

    /**
     * Konstruktori mockien avulla testaamiseen (todo)
     *
     * @param languageDao suoraan kielten tietokantaa käyttävä luokka
     * @param snippetService katkelmia hallinnoiva palvelu, saa myös languageDao:n
     * @param dbmode merkkijono "file" tai "sql" sen mukaan kumman haluaa näkyvän UI:ssä testatessa
     */
    public SnippetManager(LanguageDao languageDao, SnippetService snippetService, String dbmode) {
        this.languageService = new LanguageService(languageDao);
        this.snippetService = snippetService;
        this.dbmode = dbmode;
    }

    /**
     * Ohjelman käyttämä konstruktori
     *
     * @param valinta file tai sql sen mukaan kumpaa halutaan käyttää tallennukseen
     * 
     * @see himapaja.snippetmanager.logic.SnippetService
     * @see himapaja.snippetmanager.logic.LanguageService
     */
    public SnippetManager(String valinta) {
        this.dbmode = valinta;
        if (valinta.equals("file")) {
            fileDb();
        } else if (valinta.equals("sql")) {
            sqlDb();
        }
    }

    /**
     * Metodi jolla asetetaan tekstitiedosto tallennusmuodoksi. Tiedot etsitään
     * tiedostosta config.properties. Käytetään konstruktorissa, sekä
     * erillisellä setterillä lennosta vaihtoon.
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setDbmode(java.lang.String)
     */

    public void fileDb() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));

            String languageFile = properties.getProperty("languageFile").trim();
            this.languageService = new LanguageService(new FileLanguageDao(languageFile));

            String snippetFile = properties.getProperty("snippetFile").trim();
            this.snippetService = new SnippetService(new FileSnippetDao(snippetFile, languageService), this.languageService);

        } catch (IOException e) {
            System.out.println("Error reading config file:" + e.getMessage());
        }
    }

    /**
     * Metodi jolla asetetaan sql-tietokanta(h2) tallennusmuodoksi. Käytetään
     * konstruktorissa, sekä erillisellä setterillä lennosta vaihtoon.
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setDbmode(java.lang.String)
     */

    public void sqlDb() {
        this.languageService = new LanguageService(new SqlLanguageDao());
        this.snippetService = new SnippetService(new SqlSnippetDao(), languageService);
    }

    //language metodit
    /**
     * Aktiivisena olevan ohjelmointikielen valinta. Heijastuu tallentamiseen,
     * ja toiseen Snippettien listausmuotoon.
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#createSnippet(java.lang.String,
     * java.lang.String, java.util.List)
     * @see himapaja.snippetmanager.logic.SnippetManager#getSnippetList()
     *
     * @param language Language-tyyppinen olio joka valitaan aktiiviseksi.
     */
    public void setSelected(Language language) {
        this.selected = language;
    }

    /**
     *
     * @return String palauttaa valitun kielen nimen merkkijonona
     */
    public String getLanguageString() {
        if (selected == null) {
            return null;
        }
        return this.selected.getName();
    }

    /**
     *
     * @return Language palauttaa valitun kielen Language oliona
     */
    public Language getLanguage() {
        return this.selected;
    }

    /**
     *
     * @return int id Kielen tunniste
     */
    public int getLanguageId() {
        return this.selected.getId();
    }

    /**
     *
     * @return List kaikista tallennetuista ohjelmointikielistä
     */
    public List<Language> getLanguages() {
        return languageService.getLanguages();
    }

    /**
     * Uuden ohjelmointikielen luonti ja tallettaminen tietokantaan.
     *
     * @param name Merkkijono jolla ohjelmointikieli jatkossa tunnistetaan.
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     */
    public void createLanguage(String name) {
        languageService.createLanguage(name);
    }

    // SNIPPET METODIT
    /**
     * Lista valitulla kielellä tallennetuista katkelmista
     *
     * @see himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     *
     * @return List - lista katkelmista valitulla kielellä
     */
    public List<Snippet> getSnippetList() {
        return snippetService.getAll(selected);

    }

    /**
     * Lista kaikista katkelmista
     *
     * @return List - lista snippeteistä kaikilla kielillä
     */
    public List<Snippet> getSnippetLongList() {
        return snippetService.getAll();
    }

    /**
     * Uuden katkelman luominen ja tallentaminen valittuun tietokantaan
     * (sql/file) valitulla kielellä
     * 
     * @param language Tallennettu ohjelmakieli johon katkelma liittyy
     *
     * @param name Nimi merkkijonona
     *
     * @param code Varsinainen koodi merkkijonona.
     *
     * @param tags Lista tägeistä joilla snippet:tiä voidaan hakea.
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     * @see himapaja.snippetmanager.logic.SnippetManager#dbmode
     *
     * @return Snippet - Palauttaa luodun katkelman, sisältäen id:n jolla se on
     * tallennettu.
     */
    public Snippet createSnippet(String name, String code, List<String> tags) {
        return snippetService.createSnippet(selected, name, code, tags);
    }
    public Snippet createSnippet(Language language, String name, String code, List<String> tags) {
        return snippetService.createSnippet(language, name, code, tags);
    }
//    public int createSnippetReturnId(Language language, String name, String code, List<String> tags) {
//        return snippetService.createSnippet(language, name, code, tags).getId();
//    }

    /** 
     * Poista annettu katkelma tietokannasta
     * 
     * @param snippet Katkelma joka halutaan poistaa
     * 
     * @return boolean onnistuiko poisto-operaatio
     */
    public boolean deleteSnippet(Snippet snippet) {
        return snippetService.deleteSnippet(snippet);
    }

    /**
     * Päivitä annettua katkelmaa tietokannassa
     *
     * @param snippet päivitetty katkelma
     * 
     * @return boolean onnistuiko päivitysoperaatio
     */
    public boolean updateSnippet(Snippet snippet) {
        return snippetService.updateSnippet(snippet);
        //return false;
    }

    /**
     * Palauttaa listan katkelmista joilla on hakuehdoksi annettu tag
     *
     * @param tag hakuehto
     *
     * @return List lista katkelmista joilla on annettu tag
     */
    public List<Snippet> findByTag(String tag) {
        return snippetService.findByTag(tag);
    }

    /**
     * Palauttaa listan katkelmista valitulla kielellä jotka omaavat annetun
     * tagin
     *
     * @param tag hakuehto
     *
     * @see himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     *
     * @return List - lista katkelmista joilla on annettu tag,
     * valitulla kielellä
     */
    public List<Snippet> findByTagAndLanguage(String tag) {
        return snippetService.findByTag(tag, this.getLanguageId());
    }

    /**
     * Palauttaa listan katkelmista millä tahansa kielellä joiden otsikosta
     * löytyy annettu merkkijono.
     *
     * @param title hakuehto
     *
     * @return List lista hakuehdon täyttävistä katkelmista
     */
    public List<Snippet> findByTitle(String title) {
        return snippetService.findByTitle(title);
    }

    /**
     * Palauttaa listan katkelmista valitulla kielellä joiden otsikosta löytyy
     * annettu merkkijono.
     *
     * @param title hakuehto
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     *
     * @return List lista hakuehdon täyttävistä katkelmista valitulla
     * kielellä
     */
    public List<Snippet> findByTitleAndLanguage(String title) {
        return snippetService.findByTitle(title, this.getLanguageId());
    }

    /**
     * Palauttaa listan katkelmista millä tahansa kielellä joiden otsikosta
     * löytyy annettu merkkijono.
     *
     * @param title hakuehto - merkkijono jonka on löydyttävä otsikosta
     * @param tag hakuehto - tagi joka katkelman on omattava
     *
     * @return List lista hakuehdon täyttävistä katkelmista
     */
    public List<Snippet> findByTitleAndTag(String title, String tag) {
        return snippetService.findByTitleAndTag(title, tag);
    }

    /**
     * Palauttaa listan katkelmista joilla on annettu tag, joiden otsikosta
     * löytyy annettu merkkijono ja jotka ovat valitulla(selected) kielellä.
     *
     * @param title Merkkijono jonka on löydyttävä katkelman otsikosta
     * @param tag Tagi jonka katkelman on omattava
     *
     * @see himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     *
     * @return List - lista hakuehtoihin sopivista katkelmista.
     */
    public List<Snippet> findByTitleAndTagAndLanguage(String title, String tag) {
        return snippetService.findByTitleAndTag(title, tag, this.getLanguageId());
    }

    /**
     * Palauttaa merkkijonon tallennetaanko h2-tietokantaan("sql") vai
     * tiedostoon ("file") Huom! Lower case.
     *
     * @return String "sql" tai "file"
     */
    
    //tietokannan valintaan liittyvät metodit
    public String getDbmode() {
        return this.dbmode;
    }
/**
 * Metodi jonka avulla voidaan vaihtaa tallennusmuotoa lennosta.
 * 
 * @param dbmode Merkkijono jolla ilmaistaan haluttu tallennusosoite, "sql" tai "file".
 * 
 * @see himapaja.snippetmanager.logic.SnippetManager#fileDb() 
 * @see himapaja.snippetmanager.logic.SnippetManager#sqlDb()
 */

    public void setDbmode(String dbmode) {
        if (dbmode.equals("file")) {
            fileDb();
        } else if (dbmode.equals("sql")) {
            sqlDb();
        } else {
            return;
        }
    }
}
