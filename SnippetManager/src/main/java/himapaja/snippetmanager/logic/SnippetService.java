package himapaja.snippetmanager.logic;

import himapaja.snippetmanager.logic.LanguageService;
import himapaja.snippetmanager.dao.SnippetDao;
import himapaja.snippetmanager.domain.Snippet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public class SnippetService {

    //vähän turha väliluokka toistaiseksi mutta tulipahan tehtyä
    private SnippetDao snippetDao;
    private LanguageService languageService;

    public SnippetService(SnippetDao snippetDao, LanguageService languageService) {
        this.snippetDao = snippetDao;
        this.languageService = languageService;
    }

    public Snippet createSnippet(int languageId, String name, String code) {
        Snippet uusi = new Snippet(languageId, name, code, new ArrayList<>());
        snippetDao.create(uusi);
        return uusi;
    }
    public Snippet createSnippet(int languageId, String name, String code, List<String> tags) {
        Snippet uusi = new Snippet(languageId, name, code, tags);
        snippetDao.create(uusi);
        return uusi;
    }

    public List<Snippet> getAll(int languageId) {
        return snippetDao.getAll(languageId);
    }

    public List<Snippet> getAll() {
        List<Snippet> lista = snippetDao.getAll(-1);
        for (Snippet snippet : lista) {
            snippet.setLanguage(languageService.idToString(snippet.getLanguageId()));
        }
        return lista;
    }
    
    public boolean deleteSnippet(Snippet snippet) {
        return snippetDao.delete(snippet);
    }
    
    public Snippet getById(int id) {
        return snippetDao.getById(id);
    }
    
    public List<Snippet> findByTitle(String title) {
        return snippetDao.findByTitle(title, -1);
    }
    public List<Snippet> findByTitle(String title, int langId) {
        return snippetDao.findByTitle(title, langId);
    }
    
    public List<Snippet> findByTag(String tag, int langId) {
        return snippetDao.findByTag(tag, langId);
    }
    public List<Snippet> findByTag(String tag) {
        return snippetDao.findByTag(tag, -1);
    }
    public List<Snippet> findByTitleAndTag(String title,String tag) {
        return snippetDao.findByTitleAndTag(title, tag, -1);
    }
    public List<Snippet> findByTitleAndTag(String title, String tag, int langId) {
        return snippetDao.findByTitleAndTag(title, tag, langId);
    }
    
    public boolean updateSnippet(Snippet snippet) {
        return snippetDao.update(snippet);
    }
}
