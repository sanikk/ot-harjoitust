package himapaja.snippetmanager.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public class HashTag {
    
    private String name;
    private List<Snippet> representatives;
    
    public HashTag(String name) {
        this.name = name;
        this.representatives = new ArrayList<>();
    }
    
    public void addRepresentative(Snippet snippet) {
        this.representatives.add(snippet);
    }
    
    public void removeRepresentative(Snippet snippet) {
        this.representatives.remove(snippet);
    }
    
    public List<Snippet> getRepresentatives() {
        return this.representatives;
    }
    
}
