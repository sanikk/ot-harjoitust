package himapaja.snippetmanager.domain;

import java.util.ArrayList;
import java.util.List;

public class Snippet {

    private String identifier;
    private String code;
    private List<String> hashtags;
    
    public Snippet() {
        this.identifier = "";
        this.code = "";
        this.hashtags = new ArrayList<>();
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public void addHashtag(String hashtag) {
        if(!this.hashtags.contains(hashtag)) {
            this.hashtags.add(hashtag);
        }
    }
    
    public boolean removeHashtag(String hashtag) { //jää turhaks kohta
        return hashtags.remove(hashtag);
    }

}
