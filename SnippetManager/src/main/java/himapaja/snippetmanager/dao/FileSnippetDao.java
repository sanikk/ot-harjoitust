package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.logic.LanguageService;
import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuli Nikkilä
 */
public class FileSnippetDao implements SnippetDao {

    private final List<Snippet> snippets;
    private final String file;
    private final File tiedosto;
    private int nextId;
    private LanguageService ls;

    public FileSnippetDao(String file, LanguageService ls) {
        this.snippets = new ArrayList<>();
        this.file = file;
        this.tiedosto = new File(file);
        this.ls = ls;
        load();
    }

    private void load() {
        if (!tiedosto.exists()) {
            loadError();
        } else {
            try {
                Scanner fileReader = new Scanner(tiedosto);
                if (!fileReader.hasNextLine()) {
                    loadError();
                } else {  // kaikki ok, luetaan
                    nextId = Integer.parseInt(fileReader.nextLine());
                    while (fileReader.hasNextLine()) {
                        snippets.add(new Snippet(fileReader.nextLine(), ls));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading snippets file " + file + ":" + e.getMessage());
            }
        }
    }

    private void loadError() {
        System.out.println("File not found or empty, creating a new one on save: " + file);
        nextId = 0;
    }

    public boolean save() {
        //eli tiedoston alkuun tulee nextId arvo, sen jälkeen tallennetut pätkät muodossa: id, langId, nimi, koodi, (tagit?)
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
    @Override
    public Snippet getById(int id) {
        for (Snippet snippet : snippets) {
            if (snippet.getId() == id) {
                return snippet;
            }
        }
        return null;
    }

//    @Override
//    public Snippet getByName(String name) {
//        for (Snippet snippet : snippets) {
//            if (snippet.getName().equals(name)) {
//                return snippet;
//            }
//        }
//        return null;
//    }
    public List<Snippet> findByTitle(String title, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : this.snippets) {
            if (snippet.getName().contains(title)) {
                if (snippet.getLanguageId() == -1 || snippet.getLanguageId() == langId) {
                    palautettava.add(snippet);
                }
            }
        }
        return palautettava;
    }

    @Override
    public List<Snippet> findByTag(String tag, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : snippets) {
            List<String> tags = snippet.getTags();
            for (String tagInSnippet : tags) {
                if (tagInSnippet.equals(tag) && langId == -1 || langId == snippet.getLanguageId()) {
                    palautettava.add(snippet);
                }
            }
        }
        return palautettava;
    }
    
    public List<Snippet> findByTitleAndTag(String title, String tag, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : snippets) {
            if (!snippet.getName().contains(title)) {
                continue;
            }
            List<String> tags = snippet.getTags();
            for (String tagInSnippet : tags) {
                if (tagInSnippet.equals(tag) && langId == -1 || langId == snippet.getLanguageId()) {
                    palautettava.add(snippet);
                }
            }
        }
        return palautettava;
    }

    // LISTAT
    @Override
    public List<Snippet> getAll(int id) {
        if (id == -1) {
            return this.snippets;
        }
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : snippets) {
            if (snippet.getLanguageId() == id) {
                palautettava.add(snippet);
            }
        }
        return palautettava;
    }

    // UPDATE hoituukin viitteillä jo aiemmin ja savella. Ottaa input koska: SnippetDao
    @Override
    public boolean update(Snippet snippet) {
        return this.save();
    }

    //DELETE
    @Override
    public boolean delete(Snippet snippet) {
        if (snippets.remove(snippet)) {
            return save();
        }

        return false;
    }

    // CREATE JA ID
    @Override
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
