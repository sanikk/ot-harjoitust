package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Language;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlLanguageDao implements LanguageDao {

    public SqlLanguageDao() {
        if (!new File("snippetdb.mv.db").exists()) {
            alustaTietokanta();
        }
    }

    @Override
    public boolean create(Language language) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            conn.prepareStatement("INSERT INTO Languages (name) VALUES ('" + language.getName() + "');").executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error creating entry for: " + language);
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Language> getAll() {
        List<Language> palautettava = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT * FROM Languages;").executeQuery();
            while (rs.next()) {
                palautettava.add(new Language(rs.getString("name"), Integer.parseInt(rs.getString("id"))));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error getting list of languages: " + e.getMessage());
        }

        return palautettava;
    }

    @Override
    public String idToString(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT * FROM Languages WHERE id=" + id).executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in idToString: " + e.getMessage());
        }
        return "UNKNOWN - Please contact db administrator";
    }

    @Override
    public Language getById(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT "
                    + "id, name "
                    + "FROM "
                    + "Languages "
                    + "WHERE "
                    + "id "
                    + "= "
                    + id
                    + ";"
            ).executeQuery();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }
        return null;
    }

    public static void alustaTietokanta() {

        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            conn.prepareStatement("DROP TABLE Snippets IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Languages IF EXISTS;").executeUpdate();

            conn.prepareStatement("CREATE TABLE Languages(id serial, name varchar(127));").executeUpdate();
            conn.prepareStatement("CREATE TABLE Snippets(id serial, languageid INTEGER, name varchar(127), code varchar(1023), FOREIGN KEY (languageId) REFERENCES Languages(id));").executeUpdate();
            //String kielia = "('Java'" + "," + "'JavaScript')";
            //conn.prepareStatement("INSERT INTO Languages (nimi) VALUES " + kielia + ";").executeLargeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error creating db: " + e.getMessage());
        }
    }
}
