package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.Dao.SnippetDao;
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
        Snippet uusi = new Snippet(languageId, name, code);
        snippetDao.create(uusi);
        return uusi;
    }

    public List<Snippet> getAll(int languageId) {
        return snippetDao.getAll(languageId);
    }

    public List<Snippet> getAll() {
        List<Snippet> lista = snippetDao.getAll();
        for (Snippet snippet : lista) {
            snippet.setLanguage(languageService.idToString(snippet.getLanguageId()));
        }
        return lista;
    }
    
    public boolean deleteSnippet(Snippet snippet) {
        return snippetDao.delete(snippet);
    }
}
