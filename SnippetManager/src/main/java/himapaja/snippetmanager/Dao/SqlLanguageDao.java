package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Language;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author karpo
 */
public class SqlLanguageDao {

    public void create(Language language) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "");
        conn.prepareStatement("INSERT INTO Languages (nimi) VALUES " + language.getName());
        } catch (Exception e) {
            System.out.println("Error creating entry for: " + language);
            System.out.println(e.getMessage());
        }
    }
    
    

    public static void alustaTietokanta() {
        String kielia = "('Java,')" + "('JavaScript')";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            conn.prepareStatement("DROP TABLE Snippets IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Languages IF EXISTS;").executeUpdate();
            
            conn.prepareStatement("CREATE TABLE Languages(id serial, nimi varchar(127)").executeUpdate();
            conn.prepareStatement("CREATE TABLE Snippets(id serial, languageId, name, code").executeUpdate();
            
            conn.prepareStatement("INSERT INTO Languages (nimi) VALUES " + kielia + ";").executeLargeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error creating db: " + e.getMessage());
        }

    }

}
