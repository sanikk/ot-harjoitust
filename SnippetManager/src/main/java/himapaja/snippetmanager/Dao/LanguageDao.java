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
    
    Language getById(int id);
    
    //int giveNextId(); //file version sisäiseen käyttöön
    
    String idToString(int id);
    
}
