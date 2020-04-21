package himapaja.snippetmanager;

import himapaja.snippetmanager.dao.SqlLanguageDao;
import himapaja.snippetmanager.dao.SqlSnippetDao;
import himapaja.snippetmanager.domain.LanguageService;
import himapaja.snippetmanager.domain.SnippetManager;
import himapaja.snippetmanager.domain.SnippetService;
import himapaja.snippetmanager.ui.TestiUI;
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
        TextUI kayttis = new TextUI(skanner, new SnippetManager("sql"));

        kayttis.startUI();
    }
}
