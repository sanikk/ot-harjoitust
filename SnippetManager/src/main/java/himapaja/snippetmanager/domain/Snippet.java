package himapaja.snippetmanager.domain;

import java.util.ArrayList;
import java.util.List;

public class Snippet {

    private int id;
    private String name;
    private String code;
    private List<String> hashtags;

    public Snippet() {
        this.name = "";
        this.code = "";
        this.hashtags = new ArrayList<>();
    }

    public Snippet(String name, String code) {
        this.name = name;
        this.code = code;
        this.hashtags = new ArrayList<>();
    }

    public Snippet(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.hashtags = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addHashtag(String hashtag) {
        if (!this.hashtags.contains(hashtag)) {
            this.hashtags.add(hashtag);
            return true;
        }
        return false;
    }

    public boolean removeHashtag(String hashtag) { //jää turhaks kohta
        return hashtags.remove(hashtag);
    }

    public String toString() {
        return this.id + "," + this.name + "," + this.code;
    }
    
    public String data() {
        return this.id + "-,-" + this.name + "-,-" + this.code;
    }

}
