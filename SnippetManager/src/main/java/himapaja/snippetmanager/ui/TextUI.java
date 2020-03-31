package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.SnippetManager;
import java.util.List;
import java.util.Scanner;

public class TextUI {

    private Scanner skanner;
    private SnippetManager snippetMan;
    private Language selectedLanguage;

    public TextUI(Scanner skanner, SnippetManager snippetMan) {
        this.skanner = skanner;
        this.snippetMan = snippetMan;
    }

    public void startUI() {
        boolean running = true;
        System.out.println("\nThis is an ASCII-based UI for (at least) testing purposes"); // FIXME REMOVE THIS LINE
        System.out.println("\nWelcome to the SnippetManager of your dreams.");
        selectLanguage();
        while (running) {
            System.out.println("\nSelected language: " + selectedLanguage.getName());
            listActions();
            System.out.print("  ");
            int selectedAction = Integer.parseInt(skanner.nextLine());
            switch (selectedAction) {
                case 1:
                    addNewSnippet();
                    continue;
                case 99:
                    selectLanguage();
                    continue;
                case 0:
                    System.out.println("Thank you, come again!");
                    running = false;
                    break;
            }

        }
    }

    private void selectLanguage() {
        while (true) {
            List<Language> languages = snippetMan.getLanguages();
            System.out.println("\nPlease choose a language to use:");

            for (int j = 0; j < languages.size(); j++) {
                System.out.print("  ");
                if (selectedLanguage != null && selectedLanguage == languages.get(j)) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
                System.out.println(j + " - " + languages.get(j).getName());
            }
            System.out.println("   99 - Add a new language to the list");
            System.out.print("  ");
            int luettu = Integer.parseInt(skanner.nextLine());

            if (luettu == 99) {
                System.out.print("What's the name of the language to add? ");
                String newLanguage = skanner.nextLine();
                if (newLanguage != null && !newLanguage.isEmpty()) {
                    snippetMan.createLanguage(newLanguage);
                    continue;
                }
            }
            if(luettu >= 0 && luettu < languages.size()) {
                selectedLanguage = languages.get(luettu);
                return;
            }
        }
    }

    private void listActions() {
        System.out.println("\nChoose an action:");
        System.out.println("   1 - Add a snippet in " + selectedLanguage.getName());
        System.out.println("  99 - Change the current programming language");
        System.out.println("   0 - Exit SnippetManager");
    }

    private void addNewSnippet() {
        System.out.println("\nNothing here yet!");
    }
}
