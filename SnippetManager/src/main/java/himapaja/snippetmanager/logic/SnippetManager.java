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

                String languageFile = properties.getProperty("languageFile").trim();
                this.languageService = new LanguageService(new FileLanguageDao(languageFile));

                String snippetFile = properties.getProperty("snippetFile").trim();
                this.snippetService = new SnippetService(new FileSnippetDao(snippetFile, languageService), this.languageService);

            } catch (IOException e) {
                System.out.println("Error reading config file:" + e.getMessage());
            }
        } else if (valinta.equals("sql")) {
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

    public List<Snippet> findByTag(String tag) {
        return snippetService.findByTag(tag);
    }
    public List<Snippet> findByTagAndLanguage(String tag) {
        return snippetService.findByTag(tag, this.getLanguageId());
    }
    
    public List<Snippet> findByTitle(String title) {
        return snippetService.findByTitle(title);
    }
    public List<Snippet> findByTitleAndLanguage(String title) {
        return snippetService.findByTitle(title, this.getLanguageId());
    }
    public List<Snippet> findByTitleAndTag(String title, String tag) {
        return snippetService.findByTitleAndTag(title, tag);
    }
    public List<Snippet> findByTitleAndTagAndLanguage(String title, String tag) {
        return snippetService.findByTitleAndTag(title, tag, this.getLanguageId());
    }
}
