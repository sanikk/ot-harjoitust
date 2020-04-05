package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.Dao.LanguageDao;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 * 
 * Class handling known programming languages
 */
public class LanguageService {
    private LanguageDao languageDao;
    
    public LanguageService(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }
    
    public boolean createLanguage(String name) {
        Language language = new Language(name, languageDao.giveNextId());
        try {
            languageDao.create(language);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public List<Language> getLanguages() {
        return languageDao.getAll();
    }
    
    public Language getByIndex(int index) {
        return languageDao.getByIndex(index);
    }
    
    public String idToString(int id) {
        return languageDao.idToString(id);
    }
    
    
}
