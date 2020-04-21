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

    // LUKU/KIRJOITUS
    public void load() {
        File tiedosto = new File(file);
        if (!tiedosto.exists()) {
            System.out.println("File not found, creating a new one: " + file);
            nextId = 0;

        } else {
            try {
                Scanner fileReader = new Scanner(new File(file));
                if (fileReader.hasNextLine()) {
                    nextId = Integer.parseInt(fileReader.nextLine());
                } else {
                    System.out.println("Error reading snippet file " + file + ": Empty file!");
                    nextId = 0;
                    return;
                }
                while (fileReader.hasNextLine()) {
                    String rivi = fileReader.nextLine();
                    String[] sanat = rivi.split("-,-");
                    Snippet uusi = new Snippet(Integer.parseInt(sanat[0]), Integer.parseInt(sanat[1]), sanat[2], sanat[3]);
                    snippets.add(uusi);
                    List<String> tags = uusi.getTags();
                    String[] palat = sanat[4]
                            .substring(1, sanat[4].length() - 1)
                            .split(", ");
                    for (int i = 0; i < palat.length; i++) {
                        tags.add(palat[i]);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading snippets file " + file + ":" + e.getMessage());
            }
        }
    }

    public boolean save() {
        //eli tiedoston alkuun tulee nextId arvo, sen jälkeen tallennetut pätkät muodossa: id, langId, nimi, koodi
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
    
    // ETSINTÄ
    
    public Snippet getById(int id) {
        for(Snippet snippet: snippets) {
            if(snippet.getId() == id) {
                return snippet;
            }
        }
        return null;
    }
    
    public Snippet getByName(String name) {
        for(Snippet snippet : snippets) {
            if(snippet.getName() == name) {
                return snippet;
            }
        }
        return null;
    }
    
    public List<Snippet> findByTag(String tag) {
        List<Snippet> palautettava = new ArrayList<>();
        for(Snippet snippet : snippets) {
            List<String> tags = snippet.getTags();
            for(String tagInSnippet : tags) {
                if(tagInSnippet.equals(tag)) {
                    palautettava.add(snippet);
                }
            }
        }
        return palautettava;
    }
    

    // LISTAT
    public List<Snippet> getAll() {
        return this.snippets;
    }

    public List<Snippet> getAll(int id) {
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : snippets) {
            if (snippet.getLanguageId() == id) {
                palautettava.add(snippet);
            }
        }
        return palautettava;
    }

    // UPDATE hoituukin viitteillä jo aiemmin ja savella. Ottaa input koska: SnippetDao
    public boolean update(Snippet snippet) {
        return this.save();
    }

    //DELETE
    public boolean delete(Snippet snippet) {
        if (snippets.remove(snippet)) {
            return save();
        }

        return false;
    }

    // CREATE JA ID
    public boolean create(Snippet snippet) {
        snippet.setId(nextId);
        snippets.add(snippet);
        nextId++;
        if (save()) {
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
