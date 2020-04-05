package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Language;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface LanguageDao {
    
    boolean create(Language language) throws Exception;
    
    List<Language> getAll();
    
    Language getByIndex(int index);
    
    int giveNextId();
    
    String idToString(int id);
    
}
