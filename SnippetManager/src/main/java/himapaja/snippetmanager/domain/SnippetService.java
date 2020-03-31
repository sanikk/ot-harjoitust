package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.Dao.SnippetDao;

/**
 *
 * @author Samuli Nikkilä
 */
public class SnippetService {
    
    private SnippetDao snippetDao;
    
    public SnippetService(SnippetDao snippetDao) {
        this.snippetDao = snippetDao;
    }
}
