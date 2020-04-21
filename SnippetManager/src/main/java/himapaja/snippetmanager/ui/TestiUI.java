package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.logic.SnippetManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Dev aikainen hiekkalaatikko
 *
 * @author Samuli Nikkilä
 */
public class TestiUI {

    private Scanner skanner;
    private SnippetManager snippetman;

    public TestiUI(Scanner skanner, SnippetManager snippetman) {
        this.skanner = skanner;
        this.snippetman = snippetman;
    }

    public void startUI() {
        //SqlLanguageDao.alustaTietokanta();
        //tänne mitä testataan
        //snippetman.createLanguage("Java");
        //snippetman.createLanguage("JavaScript");
        //System.out.println(snippetman.getLanguages());
        //Language language = snippetman.getLanguages().get(0);
//        List<String> tags = new ArrayList<>();
//        tags.add("testi");
//        tags.add("tagi");
        //snippetman.setSelected(language);
//        snippetman.createSnippet("nimi", "koodi", tags);
        //System.out.println(snippetman.getSnippetList());
        System.out.println(snippetman.getSnippetLongList());
        System.out.println(snippetman.findByTitle("mI"));
        //System.out.println(snippetman.getById(1));
        //System.out.println(snippetman.getByName("nimi"));
        //snippetman.getByName("nimi");


//        List<String> tagit = new ArrayList<>();
//        tagit.add("hello");
//        tagit.add("loop");
//        System.out.println(tagit.toString().substring(1, tagit.toString().length() - 1));
//        snippetman.createSnippet("nimi1", "koodi1");
//        snippetman.createSnippet("nimi2", "koodi2");
//        snippetman.createSnippet("nimi3", "koodi3");
//        Snippet palaute = snippetman.createSnippet("nimi", "koodi");
//        System.out.println(palaute.getId());
    }
}
