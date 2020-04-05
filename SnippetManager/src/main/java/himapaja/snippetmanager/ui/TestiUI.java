package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Snippet;
import himapaja.snippetmanager.domain.SnippetManager;
import java.util.Scanner;

/**
 * Dev aikainen hiekkalaatikko
 *
 * @author karpo
 */
public class TestiUI {

    private Scanner skanner;
    private SnippetManager snippetman;

    public TestiUI(Scanner skanner, SnippetManager snippetman) {
        this.skanner = skanner;
        this.snippetman = snippetman;
    }
    
    public void startUI() {
        //tänne mitä testataan
        snippetman.createSnippet("nimi1", "koodi1");
        snippetman.createSnippet("nimi2", "koodi2");
        snippetman.createSnippet("nimi3", "koodi3");
        Snippet palaute = snippetman.createSnippet("nimi", "koodi");
        System.out.println(palaute.getId());
        
    }
}
