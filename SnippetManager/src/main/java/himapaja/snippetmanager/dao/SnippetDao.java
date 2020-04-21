package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Snippet;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface SnippetDao {

    boolean create(Snippet snippet);

    List<Snippet> getAll();
    
    List<Snippet> getAll(int languageId);
    
    Snippet getById(int id);
    
    Snippet getByName(String name);
    
    List<Snippet> findByTag(String tag);

    boolean update(Snippet snippet);
    
    boolean delete(Snippet snippet);
    
    List<Snippet> findByTitle(String title);

}
