package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.logic.SnippetManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Samuli Nikkilä
 */
public class FxGUI extends Application {
    
    private SnippetManager sm;

    @Override
    public void start(Stage primaryStage) {

        this.sm = new SnippetManager("sql");
        BorderPane root = new BorderPane();
        
        //STAATTISET ELEMENTIT
        //otsikkorivi
        Text otsikko = new Text("SnippetManager v1.0, All rights reserved");
        otsikko.setTextAlignment(TextAlignment.RIGHT );
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // vasen paneeli eli linkkilista
        VBox vasen = new VBox();
        vasen.setPadding(new Insets(25, 0, 10, 20));
        vasen.setSpacing(10);
        Label intro = new Label("Saving to");
        vasen.getChildren().add(intro);
        Label tallennetaan = new Label("SQL");
        vasen.getChildren().add(tallennetaan);
        Button home = leftButton("Home", "https://127.0.0.1/", root);
        //Button home = new Button("Home");
        home.setMaxWidth(Double.MAX_VALUE);
        vasen.getChildren().add(home);
        Button select = new Button();
        select.setMaxWidth(Double.MAX_VALUE);
        select.setText("Language: NONE");
        select.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("ping!");
                root.setCenter(selectLanguage());
            }
        });
        vasen.getChildren().add(select);
        Button browseLanguage = new Button("Browse in\nJava ");
        browseLanguage.setMaxWidth(Double.MAX_VALUE);
        vasen.getChildren().add(browseLanguage);
        Button browseAll = new Button("Browse ALL");
        browseAll.setMaxWidth(Double.MAX_VALUE);
        vasen.getChildren().add(browseAll);
        Button addSnippet = new Button("Add snippet");
        addSnippet.setMaxWidth(Double.MAX_VALUE);
        vasen.getChildren().add(addSnippet);
        

        //kehystä
        //BorderPane root = new BorderPane();
        root.setTop(otsikko);
        root.setLeft(vasen);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("SnippetManager v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Button leftButton(String text, String url, BorderPane root) {
        Button palautettava = new Button(text);
        palautettava.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("ping!");
                root.setCenter(homepage());
            }
        });
        return palautettava;
    }
    
    public Node homepage() {
        return null;
    }
    
    public Node selectLanguage() {
        VBox palautettava = new VBox();
        palautettava.setPadding(new Insets(25, 0, 10, 20));
        palautettava.setSpacing(10);
        
        Text otsikko = new Text("Please choose a language from the list, or add a new one");
        otsikko.setTextAlignment(TextAlignment.RIGHT );
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        palautettava.getChildren().add(otsikko);
        
        Text lista = new Text();
        lista.setText("moi");

        return palautettava;
    }
    public Node browseLanguage() {
        return null;
    }
    public Node browseAll() {
        return null;
    }
    public Node addSnippet() {
        return null;
    }
    
    public static void main(String[] args) {
        launch(FxGUI.class);
    }

}
