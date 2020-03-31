package himapaja.snippetmanager.domain;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Samuli Nikkilä
 */
public class LanguageTest {
    
     @Test
     public void toStringTest() {
         Language tester = new Language("test-language", 666);
         assertEquals("test-language,666", tester.toString());
     }
}
