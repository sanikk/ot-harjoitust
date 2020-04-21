package himapaja.snippetmanager.logic;

import himapaja.snippetmanager.dao.LanguageDao;
import himapaja.snippetmanager.domain.Language;
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
        Language language = new Language(name);
        try {
            languageDao.create(language);
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
        return languageDao.getById(id);
    }

    public String idToString(int id) {
        return languageDao.idToString(id);
    }
}
