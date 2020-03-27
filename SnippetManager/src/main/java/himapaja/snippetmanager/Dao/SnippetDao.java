package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Snippet;
import java.util.List;
/**
 *
 * @author Samuli Nikkilä
 */
public interface SnippetDao {
    
    Snippet create(Snippet snippet) throws Exception;
    
    List<Snippet> getAll();
    
}
