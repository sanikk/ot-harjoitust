package himapaja.snippetmanager;

import himapaja.snippetmanager.logic.SnippetManager;
import himapaja.snippetmanager.ui.FxGUI;
import himapaja.snippetmanager.ui.TextUI;
import java.util.Scanner;

/**
 *
 * @author Samuli Nikkilä
 */
public class SnippetManagerApp {

    public static void main(String[] args) throws Exception {
        Scanner skanner = new Scanner(System.in);
        //file
        //TestiUI kayttis = new TestiUI(skanner, new SnippetManager("file"));
        //TextUI kayttis = new TextUI(skanner, new SnippetManager("file));

        //sql
        //TestiUI kayttis = new TestiUI(skanner, new SnippetManager("sql"));
        //TextUI kayttis = new TextUI(skanner, new SnippetManager("sql"));
        //kayttis.startUI();
        if (args.length > 0 && args[0].equals("text")) {
            TextUI kayttis = new TextUI(skanner, new SnippetManager("sql"));
            kayttis.startUI();
        } else {
            FxGUI.launch(FxGUI.class);
        }
    }
}
