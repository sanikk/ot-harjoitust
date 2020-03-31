package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Snippet;
import java.util.List;
/**
 *
 * @author Samuli Nikkilä
 */
public interface SnippetDao {
    
    boolean create(Snippet snippet) throws Exception;
    
    List<Snippet> getAll();
    
    int giveNextId();
    
}
