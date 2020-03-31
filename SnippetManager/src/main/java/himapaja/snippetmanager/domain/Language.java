package himapaja.snippetmanager.domain;

/**
 *
 * @author Samuli Nikkilä
 */
public class Language {
    
    private String name;
    private int id;
    
    public Language(String name) {
        this.name = name;
    }
    
    public Language(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String toString() {
        return this.name + "," + this.id;
    }
}
