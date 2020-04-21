package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Tag;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public interface HashtagDao {
    
    Tag create(Tag hashtag) throws Exception;
    
    List<Tag> getAll();
    
    Tag findByName(String name);
    
}
