package himapaja.snippetmanager;

import himapaja.snippetmanager.domain.SnippetManager;
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
        SnippetManager snippetman = new SnippetManager();
        TextUI kayttis = new TextUI(skanner, snippetman);
        //TestiUI kayttis = new TestiUI(skanner, snippetman);
        kayttis.startUI();
    }

//        public static void main(String[] args) throws Exception {
//            FileLanguageDao fad = new FileLanguageDao("testeri.txt");
//            Language lang = new Language("testikieli");
//            fad.create(lang);
//            System.out.println(lang);
//           
//       }
    
//            public static void main(String[] args) throws Exception {
//            FileSnippetDao sad = new FileSnippetDao("testeri.txt");
//            Snippet snip = new Snippet("testisnippet", "leikkikoodi-on-tässä");
//            sad.create(snip);
//            System.out.println(snip);
//           
//       }
}
