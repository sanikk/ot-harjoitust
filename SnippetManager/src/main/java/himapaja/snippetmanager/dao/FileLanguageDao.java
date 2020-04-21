package himapaja.snippetmanager.dao;

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

    private final List<Language> languages;
    private final String tiedosto;
    private File file;
    private int nextId;

    public FileLanguageDao(String tiedosto) {
        this.languages = new ArrayList<>();
        this.tiedosto = tiedosto;
        this.file = new File(tiedosto);
        load();
    }

    private void load() { //checkstyle pakotti tekemään tyhmää kikkailua ja tiivistämään tätä vaikeammin devattavaksi =(
        if (!file.exists()) {
            loadError();
        } else {
            try {
                Scanner fileReader = new Scanner(file);
                if (!fileReader.hasNextLine()) {
                    loadError();
                } else {
                    nextId = Integer.parseInt(fileReader.nextLine());
                    while (fileReader.hasNextLine()) {
                        String[] sanat = fileReader.nextLine().split(",");
                        languages.add(new Language(sanat[0], Integer.parseInt(sanat[1])));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading languages file " + tiedosto + ":" + e.getMessage());
            }
        }
    }

    private boolean save() {
        //eli tiedoston alkuun tulee nextId arvo, sen jälkeen tallennetut kielet muodossa: nimi,id
        try {
            FileWriter saver = new FileWriter(file);
            saver.write(nextId + "\n");
            for (Language language : languages) {
                saver.write(language + "\n");
            }
            saver.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file " + tiedosto + ": " + e.getMessage());
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
        if (save()) {
            return true;
        }
        // tallennus epäonnistui syystä x, poistetaan kaikki muutokset ja palautetaan false
        nextId--;
        languages.remove(language);
        return false;
    }

    @Override
    public Language getById(int index) {
        return languages.get(index);
    }

    @Override
    public String idToString(int id) {
        for (Language language : languages) {
            if (language.getId() == id) {
                return language.getName();
            }
        }
        return "";
    }

    //checkstylen takia
    private void loadError() {
        System.out.println("Languages file not found, faulty or empty, creating new one on save!");
        nextId = 0;
    }

    // luokan sisäiseen käyttöön, seuraava id
    //oikeastaan turha kun on nyt sisäiseen
//    public int giveNextId() {
//        return nextId;
//    }
}
