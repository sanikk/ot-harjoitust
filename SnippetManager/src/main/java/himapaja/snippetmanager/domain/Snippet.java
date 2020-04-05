package himapaja.snippetmanager.domain;

import java.util.ArrayList;
import java.util.List;

public class Snippet {

    private int id;
    private int languageId;
    private String name;
    private String code;
    private List<String> hashtags;
    private String language;

    public Snippet() {
        this.name = "";
        this.code = "";
        this.hashtags = new ArrayList<>();
    }

    public Snippet(int languageId, String name, String code) {
        this.languageId = languageId;
        this.name = name;
        this.code = code;
        this.hashtags = new ArrayList<>();
    }

    public Snippet(int id, int languageId, String name, String code) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.code = code;
        this.hashtags = new ArrayList<>();
    }

    public Snippet(int id, int languageId,String name, String code, List<String> tagit) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.code = code;
        this.hashtags = tagit;
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

    public int getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
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
        return "Name: " + this.name + "\n     Code: " + this.code;
    }
    
    //for textUI
    public String longString() {
        return this.language + "," + this.name + "," + this.code;
    }

    public String data() {
        return this.id + "-,-" + this.languageId + "-,-" + this.name + "-,-" + this.code;
    }

}
