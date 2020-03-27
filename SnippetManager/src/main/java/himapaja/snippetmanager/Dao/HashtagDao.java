package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.HashTag;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface HashtagDao {
    
    HashTag create(HashTag hashtag) throws Exception;
    
    List<HashTag> getAll();
    
    HashTag findByName(String name);
    
}
