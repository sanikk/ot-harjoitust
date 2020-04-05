package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Language;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Samuli Nikkilä
 */
public class FileLanguageDao implements LanguageDao {

    private List<Language> languages;
    private String file;
    private int nextId;

    public FileLanguageDao(String file) {
        languages = new ArrayList<>();
        this.file = file;
        load();
    }

    private void load() {
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
                    System.out.println("Empty file!");
                    nextId = 0;
                    return;
                }
                while (fileReader.hasNextLine()) {
                    String rivi = fileReader.nextLine();
                    String[] sanat = rivi.split(",");
                    languages.add(new Language(sanat[0], Integer.parseInt(sanat[1])));
                }
            } catch (Exception e) {
                System.out.println("Error reading languages file " + file + ":" + e.getMessage());
            }
        }
    }

    private boolean save() {
        //eli tiedoston alkuun tulee nextId arvo, sen jälkeen tallennetut kielet muodossa: nimi,id
        try {
            FileWriter saver = new FileWriter(new File(file));
            saver.write(nextId + "\n");
            for (Language language : languages) {
                saver.write(language + "\n");
            }
            saver.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file " + file + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Language> getAll() {
        return languages;
    }

    @Override
    public boolean create(Language language) {
        language.setId(nextId);
        languages.add(language);
        nextId++;
        if(save()) {
            return true;
        }
        // tallennus epäonnistui syystä x, poistetaan kaikki muutokset ja palautetaan false
        nextId--;
        languages.remove(language);
        return false;
    }

    public Language getByIndex(int index) {
        return languages.get(index);
    }
    
    public String idToString(int id) {
        for(Language language : languages) {
            if(language.getId() == id) {
                return language.getName();
            }
        }
        return "";
    }

    public int giveNextId() {
        return nextId;
    }

}
