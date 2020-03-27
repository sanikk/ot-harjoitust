package himapaja.snippetmanager;

import java.util.Scanner;
import himapaja.snippetmanager.ui.TextUI;

public class SnippetManagerApp {

    public static void main(String[] args) throws Exception {
        Scanner skanner = new Scanner(System.in);
        TextUI kayttis = new TextUI(skanner);
        kayttis.startUI();
    }
}
