/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package himapaja.snippetmanager.Dao;

import himapaja.snippetmanager.domain.Snippet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuli Nikkilä
 */
public class FileSnippetDao implements SnippetDao {
    
    private List<Snippet> snippets;
    String file;
    
    public FileSnippetDao(String file) {
        this.snippets = new ArrayList<>();
        this.file = file;
        //load();
    }
    
    public List<Snippet> getAll() {
        return this.snippets;
    }
    
    public boolean create(Snippet snippet) {
        
        
        return true;
    }
    
    public int nextId() {
        
        
        return 0;
    }
    
}
