package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Language;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface LanguageDao {
    
    Language create(Language language) throws Exception;
    
    List<Language> getAll();
    
    Language getByIndex(int index);
    
}
