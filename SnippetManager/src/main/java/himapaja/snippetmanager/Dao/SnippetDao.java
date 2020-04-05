package himapaja.snippetmanager.Dao;

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

    //int giveNextId(); //tätä ei varmaan tartte, käytetään vain daon sisällä
    
    boolean update(Snippet snippet);
    
    boolean delete(Snippet snippet);

}
