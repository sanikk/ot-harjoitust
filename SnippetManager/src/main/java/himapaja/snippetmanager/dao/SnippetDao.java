package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Snippet;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface SnippetDao {

    boolean create(Snippet snippet);

    List<Snippet> getAll(int languageId);
    
    Snippet getById(int id);
    
    List<Snippet> findByTag(String tag, int langId);

    boolean update(Snippet snippet);
    
    boolean delete(Snippet snippet);
    
    List<Snippet> findByTitle(String title, int langId);

    List<Snippet> findByTitleAndTag(String title, String tag, int langid);
}
