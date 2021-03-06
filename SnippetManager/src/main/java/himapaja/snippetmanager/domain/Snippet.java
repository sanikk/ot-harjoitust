package himapaja.snippetmanager.domain;

import himapaja.snippetmanager.logic.LanguageService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snippet {

    private int id;
    private int languageId;
    private String name;
    private String code;
    private List<String> tags;
    private Language language;

    // KONSTRUKTOREJA
    // getById, getAll
    public Snippet(int id, int languageId, String name, String code, List<String> tags) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.code = code;
        this.tags = tags;
    }
    // en löytänyt missä käytetään
//    public Snippet(int id, Language language, String name, String code) {
//        this.id = id;
//        this.language = language;
//        this.name = name;
//        this.code = code;
//        this.tags = new ArrayList<>();
//    }

    //findByTitle - kun valitaan haetaanko yhteen vai moneen tageja
    public Snippet(int id, int langId, String name, String code) {
        this.id = id;
        this.languageId = langId;
        this.name = name;
        this.code = code;
        this.tags = new ArrayList<>();
    }

    public Snippet(int id, Language language, String name, String code, List<String> tagit) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.code = code;
        this.tags = tagit;
    }

    public Snippet(Language language, String name, String code, List<String> tagit) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.code = code;
        this.tags = tagit;
    }

    //checkstyle tyytyväiseksi niin pistän filedao:n purkamiset tähän £€@€@‚£$‚
    public Snippet(String rivi, LanguageService ls) throws Exception {
        String[] sanat = rivi.split("-,-");
        this.id = Integer.parseInt(sanat[0]);
        this.language = ls.getById(Integer.parseInt(sanat[1]));
        this.name = sanat[2];
        this.code = sanat[3];
        this.tags = new ArrayList<>();
        String[] palat = sanat[4].substring(1, sanat[4].length() - 1).split(", ");
        tags.addAll(Arrays.asList(palat));
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

    public boolean addTag(String hashtag) {
        if (!this.tags.contains(hashtag)) {
            this.tags.add(hashtag);
            return true;
        }
        return false;
    }

    public boolean removeTag(String hashtag) { //jää turhaks kohta
        return tags.remove(hashtag);
    }

    public void setTags(List<String> tagit) {
        this.tags = tagit;
    }

    public List<String> getTags() {
        return this.tags;
    }

    //tämä on tällainen että sopisi näkymään. Uusi nimi @TextUi
    public String textUIString() {
        return "Name: " + this.name + "\n     Code: " + this.code;
    }

    public String toString() {
        return "Id: " + id + ", name: " + name + ", language: " + language.getName() + "(id:" + language.getId() + "), code: " + code + ", tags: " + tags;
    }

    //for textUI
    public String longString() {
        return this.language + "," + this.name + "," + this.code;
    }

    //for FileSnippetDao
    public String data() {
        return this.id + "-,-" + this.languageId + "-,-" + this.name + "-,-" + this.code + "-,-" + this.tags.toString();
    }

    public String printTags() {
        String tuloste = this.tags.toString();
        return tuloste.substring(1, tuloste.length() - 1);
    }

    public int getLanguageId() {
        if (language == null) {
            return languageId;
        }
        return this.language.getId();
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getLanguageString() {
        return this.language.getName();
    }

    public Language getLanguage() {
        return this.language;
    }

}
