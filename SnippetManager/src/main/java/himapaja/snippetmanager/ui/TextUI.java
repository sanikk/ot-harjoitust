package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.Dao.FileLanguageDao;
import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.LanguageService;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class TextUI {

    private Scanner skanner;
    private LanguageService languageService;
    private int selectedLanguage = -1;

    public TextUI(Scanner skanner) {
        this.skanner = skanner;
    }

    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        String languageFile = properties.getProperty("languageFile");
        FileLanguageDao languageDao = new FileLanguageDao(languageFile);
        languageService = new LanguageService(languageDao);
    }

    public void startUI() {
        try {
            init();
        } catch (Exception e) {
            System.out.println("Error initializing: " + e.getMessage());
        }
        boolean running = true;
        System.out.println("\nThis is an ASCII-based UI for (at least) testing purposes"); // FIXME REMOVE THIS LINE
        System.out.println("\nWelcome to the SnippetManager of your dreams.");
        this.selectedLanguage = selectLanguage();
        while (running) {
            System.out.println("\nSelected language: " + languageService.getByIndex(selectedLanguage).getName());
            listActions();
            int selectedAction = Integer.parseInt(skanner.nextLine());
            switch (selectedAction) {
                case 1:
                    addNewSnippet();
                    continue;
                case 99:
                    selectedLanguage = selectLanguage();
                    continue;
                case 0:
                    System.out.println("Thank you, come again!");
                    running = false;
                    break;
            }

        }
    }

    private int selectLanguage() {
        
        
        
        Integer palautettava = -1;
        while (palautettava < 1) {
            List<Language> languages = languageService.getLanguages();
            System.out.println("\nPlease choose a language to use:");
            
            for (int j = 1; j < languages.size(); j++) {
                if (selectedLanguage == j) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
                System.out.println(j + " - " + languages.get(j).getName());
            }
            System.out.println("  0 - Add a new language to the list");

            palautettava = Integer.parseInt(skanner.nextLine());

            // FIXME replace with languageDAO stuff
            if (palautettava == 0) {
                System.out.print("What's the name of the language to add? ");
                String newLanguage = skanner.nextLine();
                if (newLanguage != null && !newLanguage.isEmpty()) {
                    languageService.createLanguage(newLanguage);
                }
            } else if(palautettava >= languages.size()) {
                palautettava = -1;
            }
        }
        return (int) palautettava;
    }

    private void listActions() {
        System.out.println("\nChoose an action:");
        System.out.println("1 - Add a snippet in " + languageService.getByIndex(selectedLanguage).getName());
        System.out.println("99 - Change the current programming language");
        System.out.println("0 - Exit SnippetManager");
    }

    private void addNewSnippet() {
        System.out.println("\nNothing here yet!");
    }
}
