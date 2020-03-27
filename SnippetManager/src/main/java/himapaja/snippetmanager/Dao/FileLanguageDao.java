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

    public FileLanguageDao(String file) {
        languages = new ArrayList<>();
        this.file = file;
        load();
    }

    //aluksi vaan jokainen omalla rivillään, id:t tulkoot muuten, se pitää muuttaa kohta
    //jos kieliä ei poisteta, ikinä, id = indeksi
    private void load() {
        try {
            Scanner fileReader = new Scanner(new File(file));
            while (fileReader.hasNextLine()) {
                String kieli = fileReader.nextLine();
                languages.add(new Language(kieli));
            }
        } catch (Exception e) {
            System.out.println("Error reading file " + e.getMessage());
            System.out.println("Using an empty list of languages.");
            languages.add(new Language("list of programming languages:"));
        }

    }

    private void save() {
        try {
            FileWriter saver = new FileWriter(new File(file));
            for (Language language : languages) {
                saver.write(language.getName() + "\n");
            }
            saver.close();
        } catch (Exception e) {
            System.out.println("Error writing to file " + file + ": " + e.getMessage());
        }

    }

    @Override
    public List<Language> getAll() {
        return languages;
    }

    @Override
    public Language create(Language language) {
        languages.add(language);
        save();
        return language;
    }
    
    public Language getByIndex(int index) {
        return languages.get(index);
    }

}
