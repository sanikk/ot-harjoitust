package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.LanguageService;
import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            } catch (FileNotFoundException | NumberFormatException e) {
                System.out.println("Error reading snippets file " + file + ":" + e.getMessage());
            }
        }
    }

    private void loadError() {
        System.out.println("File not found or empty, creating a new one on save: " + file);
        nextId = 0;
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
    @Override
    public Snippet getById(int id) {
        for (Snippet snippet : snippets) {
            if (snippet.getId() == id) {
                return snippet;
            }
        }
        return null;
    }

    @Override
    public Snippet getByName(String name) {
        for (Snippet snippet : snippets) {
            if (snippet.getName().equals(name)) {
                return snippet;
            }
        }
        return null;
    }

    @Override
    public List<Snippet> findByTag(String tag) {
        List<Snippet> palautettava = new ArrayList<>();
        for (Snippet snippet : snippets) {
            List<String> tags = snippet.getTags();
            for (String tagInSnippet : tags) {
                if (tagInSnippet.equals(tag)) {
                    palautettava.add(snippet);
                }
            }
        }
        return palautettava;
    }

    // LISTAT
    @Override
    public List<Snippet> getAll() {
        return this.snippets;
    }

    @Override
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
