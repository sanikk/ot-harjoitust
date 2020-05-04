package himapaja.snippetmanager.logic;

import himapaja.snippetmanager.dao.LanguageDao;
import himapaja.snippetmanager.domain.Language;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Samuli Nikkilä
 *
 * Class handling known programming languages
 */
public class LanguageService {

    private LanguageDao languageDao;
    private Map<Integer, Language> idToLanguage = null;
    
    private void updateCache(List<Language> lista) {
        idToLanguage = new HashMap<>();
        for(Language language : lista) {
            idToLanguage.put(language.getId(), language);
        }
    }

    public LanguageService(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    public boolean createLanguage(String name) {
        Language language = new Language(name);
        try {
            languageDao.create(language);
            updateCache(languageDao.getAll());
        } catch (Exception e) {
            System.out.println("Error in language service create: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Language> getLanguages() {
        return languageDao.getAll();
    }

    public Language getById(int id) {
        if(idToLanguage != null) {
            return idToLanguage.get(id);
        }
        return languageDao.getById(id);
    }

    public String idToString(int id) {
        if(idToLanguage != null) {
            return idToLanguage.get(id).getName();
        }
        return languageDao.idToString(id);
    }
    
    public Map<Integer, Language> getMap() {
        return idToLanguage;
    }
}
