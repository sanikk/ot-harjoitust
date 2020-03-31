package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Samuli Nikkilä
 */
public class FileSnippetDao implements SnippetDao {
    
    private List<Snippet> snippets;
    private String file;
    private int nextId;
    
    public FileSnippetDao(String file) {
        this.snippets = new ArrayList<>();
        this.file = file;
        load();
    }
    
    public void load() {
        File tiedosto = new File(file);
        if (!tiedosto.exists()) {
            System.out.println("File not found, creating a new one: " + file);
            nextId = 0;

        } else {
            try {
                Scanner fileReader = new Scanner(new File(file));
                if(fileReader.hasNextLine()) {
                    nextId = Integer.parseInt(fileReader.nextLine());
                } else {
                    System.out.println("Error reading snippet file " + file + ": Empty file!");
                    nextId = 0;
                    return;
                }
                while (fileReader.hasNextLine()) {
                    String rivi = fileReader.nextLine();
                    String[] sanat = rivi.split("-,-");
                    snippets.add(new Snippet(Integer.parseInt(sanat[0]), sanat[1], sanat[2]));
                }
            } catch (Exception e) {
                System.out.println("Error reading snippets file " + file + ":" + e.getMessage());
            }
        }
    }
    
    public boolean save() {
        //eli tiedoston alkuun tulee nextId arvo, sen jälkeen tallennetut kielet muodossa: nimi,id
        try {
            FileWriter saver = new FileWriter(new File(file));
            saver.write(nextId + "\n");
            for (Snippet snippet : snippets) {
                saver.write(snippet.data() + "\n");
            }
            saver.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file " + file + ": " + e.getMessage());
            return false;
        }
    }
    
    
    public List<Snippet> getAll() {
        return this.snippets;
    }
    
    public boolean create(Snippet snippet) {
        snippet.setId(nextId);
        snippets.add(snippet);
        nextId++;
        if(save()) {
            return true;
        }
        // tallennus epäonnistui syystä x, poistetaan kaikki muutokset ja palautetaan false
        nextId--;
        snippets.remove(snippet);
        return false;
    }
    
    public int giveNextId() {
        return nextId;
    }
    
}
