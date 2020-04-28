package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.Snippet;
import himapaja.snippetmanager.logic.SnippetManager;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Samuli Nikkilä
 */
public class TextUI {

    private final Scanner skanner;
    private final SnippetManager snippetMan;

    public TextUI(Scanner skanner, SnippetManager snippetMan) {
        this.skanner = skanner;
        this.snippetMan = snippetMan;
    }

    public void startUI() {
        boolean running = true;
        System.out.println("\nThis is an ASCII-based UI for (at least) testing purposes");
        System.out.println("\nWelcome to the SnippetManager of your dreams.");
        selectLanguage();
        while (running) {
            System.out.println("\nSelected language: " + snippetMan.getLanguage());
            listActions();
            System.out.print("  ");
            int selectedAction = Integer.parseInt(skanner.nextLine());
            switch (selectedAction) {
                case 1:
                    addNewSnippet();
                    continue;
                case 5:
                    listSnippetsInLanguage(snippetMan.getSnippetList());
                    continue;
                case 9:
                    listAllSnippets(snippetMan.getSnippetLongList());
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
                if (snippetMan.getLanguage() != null && snippetMan.getLanguageId() == languages.get(j).getId()) {
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
            if (luettu >= 0 && luettu < languages.size()) {
                snippetMan.setSelected(languages.get(luettu));
                return;
            }
        }
    }

    private void listActions() {
        System.out.println("\nChoose an action:");
        System.out.println("   1 - Add a snippet in " + snippetMan.getLanguage());
        System.out.println("   5 - List all saved snippets in " + snippetMan.getLanguage());
        System.out.println("   9 - List saved snippets in all languages");
        System.out.println("  99 - Change the current programming language");
        System.out.println("   0 - Exit SnippetManager");
    }

    private void addNewSnippet() {
        System.out.println("\nYou are adding a snippet in " + snippetMan.getLanguage());
        System.out.println("\nPlease give a short description for your snippet:");
        String desc = skanner.nextLine();
        System.out.println("Insert the code to be saved below");
        String code = skanner.nextLine();
        Snippet uusi = snippetMan.createSnippet(desc, code);

        //JATKA TÄSTÄ TÄGEILLÄ
        System.out.println("Please give tags to make your code easier to find. Separate them by line changes. Empty line quits");
        List<String> lista = uusi.getTags();
        while (true) {
            String tagi = skanner.nextLine();
            if (tagi.equals("")) {
                break;
            }
            lista.add(tagi);
        }
        snippetMan.updateSnippet(uusi);
    }

    private void listSnippetsInLanguage(List<Snippet> lista) {
        System.out.println("\nHere are the snippets in " + snippetMan.getLanguage());
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(String.format("%3.3s", i) + ". " + lista.get(i).textUIString());
            System.out.println("     Tags: " + lista.get(i).printTags() + "\n");
        }
        chooseASnippetFromList(lista, "selected");
    }

    private void listAllSnippets(List<Snippet> lista) {
        System.out.println("\nHere are all saved snippets:\n");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(String.format("%3.3s", i) + ". " + lista.get(i).longString());
            System.out.println("     Tags: " + lista.get(i).printTags() + "\n");
        }
        chooseASnippetFromList(lista, "all");
    }

    private void filterSnippetListByTitle(String mode) {
        System.out.print("\nWhat string to filter titles by? ");
        String vastaus = skanner.nextLine();
        if(mode.equals("all")) {
            listAllSnippets(snippetMan.findByTitle(vastaus));
        } else if(mode.equals("selected")) {
            listSnippetsInLanguage(snippetMan.findByTitle(vastaus));
        }
    }

    private void chooseASnippetFromList(List<Snippet> lista, String mode) {
        Snippet valittu = null;
        while (valittu == null) {
            System.out.println("\nFilter by tag with t. Filter by title with f.");
            System.out.println("\nChoose a snippet by number, or press enter to return to main menu:");
            String jatko = skanner.nextLine();
            if (jatko.isEmpty()) {
                return;
            } else if(jatko.equals("f")) {
                filterSnippetListByTitle(mode);
                return;
            }
            try {
                int valittuLuku = Integer.parseInt(jatko);
                if(valittuLuku < 0 || valittuLuku >= lista.size()) {
                    continue;
                }
                valittu = lista.get(valittuLuku);
            } catch (NumberFormatException e) {
                System.out.println("Virheellinen numerosyöte! " + e.getMessage());
            }
        }
        oneSnippetView(valittu);
        oneSnippetMenu(valittu, lista);
    }

    public void oneSnippetView(Snippet valittu) {

        System.out.println(" 1 - Name: " + valittu.getName());
        System.out.println(" 2 - Language: " + valittu.getLanguage());
        System.out.println(" 3 - Code: " + valittu.getCode());

        System.out.print(" 4 - Tags: " + valittu.printTags());

        System.out.println("\n99 - delete entry");
    }

    public void oneSnippetMenu(Snippet valittu, List<Snippet> lista) {
        int valinta = -1;
        while (true) {
            System.out.println("\nChoose a number or press enter to return to main menu:");
            String jatko = skanner.nextLine();
            if (jatko.isEmpty()) {
                return;
            }
            try {
                valinta = Integer.parseInt(jatko);
            } catch (Exception e) {
                System.out.println("Virheellinen numerosyöte!");
            }
            switch (valinta) {
                case 1:
                    System.out.println("Current name: " + valittu.getName());
                    System.out.print("Enter new name or leave empty to return: ");
                    String uusiNimi = skanner.nextLine();
                    if (uusiNimi.isEmpty()) {
                        continue;
                    }
                    valittu.setName(uusiNimi);
                    continue;
                case 2:
                    System.out.println("Current language: " + valittu.getLanguage());
                    continue;

                case 3:
                    System.out.println("Current code: " + valittu.getCode());
                    continue;
                case 4:
                    System.out.println("Current tags: ");
                    continue;
                case 99:
                    System.out.print("Are you sure you want to delete this entry? Type YES to confirm: ");
                    String vastaus = skanner.nextLine();
                    if (vastaus.equals("YES")) {
                        if (snippetMan.deleteSnippet(valittu)) {
                            lista.remove(valittu);
                            return;
                        }
                        System.out.println("Something went wrong!");
                        continue;
                    }
            }
        }
    }
}
