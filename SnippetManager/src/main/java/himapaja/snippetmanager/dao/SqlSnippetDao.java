package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Snippet;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */

public class SqlSnippetDao implements SnippetDao {

    //TEKEMÄTTÄ:
    // - TAGIT
    // - UPDATE
    // - DELETE
    //denormalisointi että on language string suoraan snippets:issä
    //private LanguageService ls;
    
    public SqlSnippetDao() {
        
    }
    
    @Override
    public boolean create(Snippet snippet) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            conn.prepareStatement("INSERT INTO Snippets (name, languageId, code) VALUES ('"
                    + snippet.getName() + "','"
                    + snippet.getLanguageId() + "','"
                    + snippet.getCode()
                    + "');").executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error creating entry for: " + snippet);
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Snippet> getAll() {
        List<Snippet> palautettava = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT Snippets.id,languageid,Snippets.name,code,languages.name AS lname FROM Snippets "
                    + "LEFT JOIN Languages ON Snippets.languageid = Languages.id;").executeQuery();

            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("lname"), rs.getString("name"), rs.getString("code")));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error getting full list: " + e.getMessage());
        }
        return palautettava;
    }

    @Override
    public List<Snippet> getAll(int languageId) {
        List<Snippet> palautettava = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT Snippets.id,languageid,Snippets.name,code,languages.name AS lname FROM Snippets "
                    + "LEFT JOIN Languages ON Snippets.languageid = Languages.id WHERE Snippets.languageid = " + languageId + ";").executeQuery();

            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("lname"), rs.getString("name"), rs.getString("code")));
            }
            conn.close();
        } catch (Exception e) {
        }
        return palautettava;
    }

    @Override
    public Snippet getById(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            //ResultSet rs = conn.prepareStatement("SELECT * FROM Snippets WHERE id = " + id + ";").executeQuery();
            ResultSet rs = conn.prepareStatement("SELECT Snippets.id,languageid,Snippets.name,code,languages.name AS lname FROM Snippets "
                    + "LEFT JOIN Languages ON Snippets.languageid = Languages.id WHERE Snippets.id = '" + id + "';").executeQuery();

            rs.next();
            Snippet snippet = new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("lname"), rs.getString("name"), rs.getString("code"));
            conn.close();
            return snippet;
        } catch (Exception e) {
            System.out.println("Error finding snippet by id: " + id);
            System.out.println("Error was: " + e.getMessage());
        }
        return null;
    }

    // tässä oletetaan nyt että nimi on uniikki.
    // joko pitää tehdä sille tarkistus, tai tehdä tästä listan palautus
    @Override
    public Snippet getByName(String name) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT Snippets.id,languageid,Snippets.name,code,languages.name AS lname FROM Snippets "
                    + "LEFT JOIN Languages ON Snippets.languageid = Languages.id WHERE Snippets.name = '" + name + "';").executeQuery();
            rs.next();
            Snippet snippet = new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("lname"), rs.getString("name"), rs.getString("code"));
            conn.close();
            return snippet;
        } catch (Exception e) {
            System.out.println("Error find snippet by name: " + name);
            System.out.println("Error was: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Snippet> findByTitle(String title) {
        List<Snippet> palautettava = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT Snippets.id,languageid,Snippets.name,code,languages.name AS lname FROM Snippets "
                    + "LEFT JOIN Languages ON Snippets.languageid = Languages.id WHERE snippets.name ILIKE '%" + title + "%';").executeQuery();
            while(rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("lname"), rs.getString("name"), rs.getString("code")));
            }
        } catch (Exception e) {
            System.out.println("Error finding by title: " + title);
            System.out.println("Error was: " + e.getMessage());
            return new ArrayList<>();
        }
        return palautettava;
    }

    // LOPUT KESKEN
    public List<Snippet> findByTag(String tag) {
        List<Snippet> palautettava = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT * FROM Snippets;").executeQuery();
        } catch (Exception e) {
        }
        return palautettava;
    }

    @Override
    public boolean update(Snippet snippet) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            System.out.println("TODO");
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean delete(Snippet snippet) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./snippetdb", "sa", "")) {
            System.out.println("TODO");
        } catch (Exception e) {
        }
        return false;
    }

}
//print row from result set:
//int columnsNumber = rs.getMetaData().getColumnCount();
//            for (int i = 1; i <= columnsNumber; i++) {
//                if (i > 1) {
//                    System.out.print(",  ");
//                }
//                String columnValue = rs.getString(i);
//                System.out.print(columnValue + " " + rs.getMetaData().getColumnName(i));
//            }
