package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.Dao.FileLanguageDao;
import himapaja.snippetmanager.Dao.FileSnippetDao;
import himapaja.snippetmanager.Dao.LanguageDao;
import himapaja.snippetmanager.Dao.SqlLanguageDao;
import himapaja.snippetmanager.Dao.SqlSnippetDao;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Samuli Nikkilä
 */
public class SnippetManager {

    private LanguageService languageService;
    private SnippetService snippetService;
    private Language selected;

    //konstruktorit
    //tää on testaukseen
    public SnippetManager(LanguageDao languageDao, SnippetService snippetService) {
        this.languageService = new LanguageService(languageDao);
        this.snippetService = snippetService;
    }

    //tää on käyttöön
    public SnippetManager(String valinta) {
        if (valinta.equals("file")) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("config.properties"));

                String languageFile = properties.getProperty("languageFile");
                this.languageService = new LanguageService(new FileLanguageDao(languageFile));

                String snippetFile = properties.getProperty("snippetFile");
                this.snippetService = new SnippetService(new FileSnippetDao(snippetFile), this.languageService);

            } catch (Exception e) {
                System.out.println("Error reading config file:" + e.getMessage());
                return;
            }
        } else if(valinta.equals("sql")) {
            this.languageService = new LanguageService(new SqlLanguageDao());
            this.snippetService = new SnippetService(new SqlSnippetDao(), languageService);
        }
    }

    //LANGUAGE METODIT:
    public void setSelected(Language language) {
        this.selected = language;
    }

    public String getLanguage() {
        if (selected == null) {
            return null;
        }
        return this.selected.getName();
    }

    public int getLanguageId() {
        return this.selected.getId();
    }

    public List<Language> getLanguages() {
        return languageService.getLanguages();
    }

    public void createLanguage(String name) {
        languageService.createLanguage(name);
    }

    // SNIPPET METODIT
    public List<Snippet> getSnippetList() {
        return snippetService.getAll(selected.getId());

    }

    public List<Snippet> getSnippetLongList() {
        return snippetService.getAll();
    }

    public Snippet createSnippet(String name, String code) {
        return snippetService.createSnippet(selected.getId(), name, code);
    }

    public Snippet createSnippet(String name, String code, List<String> tags) {
        return snippetService.createSnippet(selected.getId(), name, code, tags);
    }

    public boolean deleteSnippet(Snippet snippet) {
        return snippetService.deleteSnippet(snippet);
    }

    public boolean updateSnippet(Snippet snippet) {
        return snippetService.updateSnippet(snippet);
    }

    public Snippet getById(int id) {
        return snippetService.getById(id);
    }

    public Snippet getByName(String name) {
        return snippetService.getByName(name);
    }

    public List<Snippet> findByTag(String tag) {
        return snippetService.findByTag(tag);
    }
}
