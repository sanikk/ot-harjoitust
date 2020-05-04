package himapaja.snippetmanager.dao;

import himapaja.snippetmanager.domain.Snippet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlSnippetDao implements SnippetDao {

    //TEKEMÄTTÄ:
    // - UPDATE
    // - DELETE
    private String dbname;

    public SqlSnippetDao(String tiedosto) {
        this.dbname = tiedosto;
    }

    public SqlSnippetDao() {
        this.dbname = "snippetdb";
    }

    @Override
    public boolean create(Snippet snippet) {
        String generatedColumns[] = {"ID"};
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            PreparedStatement paluuposti = conn.prepareStatement("INSERT INTO Snippets (name, languageId, code) VALUES ('"
                    + snippet.getName() + "',"
                    + snippet.getLanguageId() + ",'"
                    + snippet.getCode()
                    + "');", generatedColumns);
            paluuposti.executeUpdate();
            ResultSet rs = paluuposti.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt("id");
                snippet.setId(id);
                List<String> lista = snippet.getTags();
                for (int i = 0; i < lista.size(); i++) {
                    conn.prepareStatement("INSERT INTO Tags (name, snippetid) VALUES ('" + lista.get(i) + "','" + id + "');").executeUpdate();
                }
            }
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error creating entry for: " + snippet);
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Snippet> getAll(int languageId) {    //käytetään -1 = kaikki kielet
        List<Snippet> palautettava = new ArrayList<>();
        String sqLause = "SELECT id,languageid,name,code FROM Snippets";
        if (languageId != -1) {
            sqLause += " WHERE languageid = " + languageId;
        }
        sqLause += ";";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            Map<Integer, List<String>> tagit = tagMap(conn);
            ResultSet rs = conn.prepareStatement(sqLause).executeQuery();
            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("name"), rs.getString("code"),
                        tagit.getOrDefault(rs.getInt("id"), new ArrayList<>())));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR in getAll: " + e.getMessage());
        }
        return palautettava;
    }

    @Override
    public Snippet getById(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            ResultSet rs = conn.prepareStatement("SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE Snippets.id = '" + id + "';").executeQuery();
            if (rs.next()) {
                Snippet snippet = new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("name"), rs.getString("code"));
                snippet.setTags(tagOne(conn, id));
                conn.close();
                return snippet;
            } else {
                conn.close();
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error finding snippet by id: " + id);
            System.out.println("Error was: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Snippet> findByTitle(String title, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        String sqLause = "";
        if (langId == -1) {
            sqLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE name ILIKE '%" + title + "%';";
        } else {
            sqLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE languageid = " + langId + " AND name ILIKE '%" + title + "%';";
        }
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            ResultSet rs = conn.prepareStatement(sqLause).executeQuery();
            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("name"), rs.getString("code")));
            }
            if (palautettava.size() > 3) {
                Map<Integer, List<String>> tagit = tagMap(conn);
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagit.getOrDefault(snippet.getId(), new ArrayList<>()));
                }
            } else {
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagOne(conn, snippet.getId()));
                }
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error finding by title: " + title);
            System.out.println("Error was: " + e.getMessage());
            return new ArrayList<>();
        }
        return palautettava;
    }

    public List<Snippet> findByTag(String tag, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        String SQLause = "";
        if (langId == -1) {
            SQLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE id IN (SELECT snippetid FROM Tags WHERE name ILIKE '%" + tag + "%');";

        } else {
            SQLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE languageid = " + langId + " AND id IN (SELECT snippetid FROM Tags WHERE name ILIKE '%" + tag + "%');";
        }
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            ResultSet rs = conn.prepareStatement(SQLause).executeQuery();
            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("name"), rs.getString("code")));
            }
            if (palautettava.size() > 3) {
                Map<Integer, List<String>> tagit = tagMap(conn);
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagit.getOrDefault(snippet.getId(), new ArrayList<>()));
                }
            } else {
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagOne(conn, snippet.getId()));
                }
            }
            conn.close();

        } catch (Exception e) {
            System.out.println("ERROR IN FINDBYTAG");
            System.out.println("ERROR WAS: " + e.getMessage());
        }
        return palautettava;
    }

    public List<Snippet> findByTitleAndTag(String etsittava, String tagi, int langId) {
        List<Snippet> palautettava = new ArrayList<>();
        String SQLause = "";
        if (langId == -1) {
            SQLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE name ILIKE '%" + etsittava + "%' AND id IN (SELECT snippetid FROM Tags WHERE name ILIKE '%" + tagi + "%');";
        } else {
            SQLause = "SELECT id,languageid,name,code FROM Snippets "
                    + "WHERE languageid = " + langId + " AND name ILIKE '%" + etsittava + "%' AND id IN (SELECT snippetid FROM Tags WHERE name ILIKE '%" + tagi + "%');";
        }
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            ResultSet rs = conn.prepareStatement(SQLause).executeQuery();
            while (rs.next()) {
                palautettava.add(new Snippet(rs.getInt("id"), rs.getInt("languageid"), rs.getString("name"), rs.getString("code")));
            }
            if (palautettava.size() > 3) {
                Map<Integer, List<String>> tagit = tagMap(conn);
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagit.getOrDefault(snippet.getId(), new ArrayList<>()));
                }
            } else {
                for (Snippet snippet : palautettava) {
                    snippet.setTags(tagOne(conn, snippet.getId()));
                }
            }
            conn.close();

        } catch (Exception e) {
            System.out.println("ERROR IN FINDBYTAG");
            System.out.println("ERROR WAS: " + e.getMessage());
        }
        return palautettava;
    }

    // Pari metodia joilla kansoitetaan tags-listoja (kaikki ja yksi)
    public Map<Integer, List<String>> tagMap(Connection conn) throws SQLException {
        Map<Integer, List<String>> palautettava = new HashMap<>();
        ResultSet rs = conn.prepareStatement("SELECT * FROM Tags;").executeQuery();
        int edellinen = -1;
        List<String> lista = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("snippetid") != edellinen) {
                if (edellinen != -1) {
                    palautettava.put(edellinen, lista);
                    lista = new ArrayList<>();
                }
                edellinen = rs.getInt("snippetid");
            }
            lista.add(rs.getString("name"));
        }
        palautettava.put(edellinen, lista);
        return palautettava;
    }

    public List<String> tagOne(Connection conn, int id) throws SQLException {
        List<String> palautettava = new ArrayList<>();
        ResultSet rs = conn.prepareStatement("SELECT * FROM Tags WHERE snippetid = " + id + ";").executeQuery();
        while (rs.next()) {
            palautettava.add(rs.getString("name"));
        }
        System.out.println(palautettava);
        return palautettava;
    }

    // LOPUT KESKEN
    @Override
    public boolean update(Snippet snippet) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            System.out.println("TODO");
            //conn.prepareStatement("");
        } catch (Exception e) {
            System.out.println("ERROR updating snippet " + snippet);
            System.out.println("Error was: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Snippet snippet) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./" + dbname, "sa", "")) {
            System.out.println("TODO");
        } catch (Exception e) {
            System.out.println("ERROR deleting snippet " + snippet);
            System.out.println("Error was: " + e.getMessage());
        }
        return false;
    }

    public String getDbname() {
        return this.dbname;
    }
    //jatkokehityksen tarpeisiin jätän tämän vain tähän
//print row from result set:
//int columnsNumber = rs.getMetaData().getColumnCount();
//            for (int i = 1; i <= columnsNumber; i++) {
//                if (i > 1) {
//                    System.out.print(",  ");
//                }
//                String columnValue = rs.getString(i);
//                System.out.print(columnValue + " " + rs.getMetaData().getColumnName(i));
}
