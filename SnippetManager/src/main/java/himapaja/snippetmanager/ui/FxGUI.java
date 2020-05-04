package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.Snippet;
import himapaja.snippetmanager.logic.SnippetManager;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class FxGUI extends Application {

    private SnippetManager sm;
    private BorderPane root;

    //tekee vasemman valikon napin (ilman toimintoa)
    private static Button luoNappi(String teksti) {
        Button palautettava = new Button(teksti);
        palautettava.setMaxWidth(Double.MAX_VALUE);
        return palautettava;
    }

    //tekee pohjan keskinäkymälle
    private static VBox pohja(String teksti) {
        VBox palautettava = new VBox();
        palautettava.setPadding(new Insets(25, 0, 10, 20));
        palautettava.setSpacing(10);
        Text otsikko = new Text("Please choose a " + teksti + " from the list");
        otsikko.setTextAlignment(TextAlignment.RIGHT);
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        palautettava.getChildren().add(otsikko);
        return palautettava;
    }

    @Override
    public void start(Stage primaryStage) {

        this.sm = new SnippetManager("sql");
        root = new BorderPane();

        //STAATTISET ELEMENTIT
        //otsikkorivi
        Text otsikko = new Text("SnippetManager v1.0, All rights reserved");
        otsikko.setTextAlignment(TextAlignment.RIGHT);
        otsikko.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // vasen paneeli eli päävalikko
        VBox vasen = new VBox();
        vasen.setPadding(new Insets(25, 0, 10, 20));
        vasen.setSpacing(10);
        vasen.setPrefWidth(200);
        Label intro = new Label("Saving to " + sm.getDbmode().toUpperCase());
        vasen.getChildren().add(intro);
        Button home = luoNappi("Home");
        Button select = luoNappi("Language: " + sm.getLanguageString());
        Button browseLanguage = luoNappi("Browse in\n" + sm.getLanguageString());
        Button browseAll = luoNappi("Browse ALL");
        Button addSnippet = luoNappi("Add snippet");
        home.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(homepage());
            }
        });
        select.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(selectLanguage(select, browseLanguage));
            }
        });
        browseLanguage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (sm.getLanguageString().equals("NONE")) {
                    root.setCenter(selectLanguage(select, browseLanguage));
                } else {
                    root.setCenter(listView(sm.getSnippetList(), false));
                }
            }
        });
        browseAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(listView(sm.getSnippetLongList(), true));
            }
        });
        addSnippet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (sm.getLanguageString().equals("NONE")) {
                    root.setCenter(selectLanguage(select, browseLanguage));
                } else {
                    root.setCenter(addSnippet());
                }
            }
        });
        vasen.getChildren().addAll(home, select, browseLanguage, browseAll, addSnippet);

        //kehystä
        root.setTop(otsikko);
        root.setLeft(vasen);
        root.setCenter(selectLanguage(select, browseLanguage));

        Scene scene = new Scene(root, 1000, 600);

        primaryStage.setTitle("SnippetManager v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Node homepage() {
        return null;
    }

    private Node selectLanguage(Button select, Button browseLanguage) {
        VBox palautettava = pohja("language");
        ListView<Language> lv = new ListView<Language>();
        ObservableList<Language> items = FXCollections.observableArrayList(sm.getLanguages());
        lv.setItems(items);
        lv.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Language>() {
            @Override
            public void changed(ObservableValue<? extends Language> ov, Language old_val, Language new_val) {
                sm.setSelected(new_val);
                languageChange(select, browseLanguage);
            }
        }
        );
        lv.setCellFactory(param -> new ListCell<Language>() {
            @Override
            protected void updateItem(Language language, boolean empty) {
                super.updateItem(language, empty);
                if (language == null || language.getName() == null) {
                    setText(null);
                } else {
                    setText(language.getName());
                }
            }
        });
        palautettava.getChildren().add(lv);
        return palautettava;
    }

    //for name and code in single snippet view
    private static HBox singleSnippetRow(String name, String data) {
        HBox singleRow = new HBox();
        Label singleLabel = new Label(name + ":");
        TextField singleField = new TextField(data);
        singleField.setMaxWidth(Double.MAX_VALUE);
        singleRow.getChildren().addAll(singleLabel, singleField);
        return singleRow;
    }

    //for language drop box in single snippet view
    private static HBox singleSnippetLanguage(ObservableList<Language> items, Language selected) {
        HBox singleRow = new HBox();
        Label languageLabel = new Label("Language:");
        ComboBox comboBox = new ComboBox(items);
        comboBox.setValue(selected.getName());
        comboBox.setCellFactory(param -> new ListCell<Language>() {
            @Override
            protected void updateItem(Language language, boolean empty) {
                super.updateItem(language, empty);
                if (language == null || language.getName() == null) {
                    setText(null);
                } else {
                    setText(language.getName());
                }
            }
        });
        singleRow.getChildren()
                .addAll(languageLabel, comboBox);
        return singleRow;
    }

    private HBox singleSnippetButtons(List<Snippet> lista, boolean kaikki) {
        HBox buttonRow = new HBox();
        Button back = new Button("BACK");
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (!lista.isEmpty()) {
                    root.setCenter(listView(sm.getSnippetList(), kaikki));
                } else {
                    root.setCenter(listView(lista, kaikki));
                }
            }
        });
        Button saveChanges = new Button("Save Changes");
        buttonRow.getChildren().addAll(back, saveChanges);
        return buttonRow;
    }

    //tän vois modata tekemään editin ja uuden
    private Node single_snippet_view(Snippet naytetaan, List<Snippet> lista, boolean kaikki) {
        VBox palautettava = pohja("option");

        TextField language = new TextField(naytetaan.getLanguageString());

        TextField tags = new TextField(naytetaan.getTags().toString());
        palautettava.getChildren().addAll(singleSnippetRow("Name", naytetaan.getName()),
                singleSnippetLanguage(FXCollections.observableArrayList(sm.getLanguages()), naytetaan.getLanguage()),
                singleSnippetRow("Code", naytetaan.getCode()),
                tags,
                singleSnippetButtons(lista, kaikki));
        return palautettava;
    }

    private Node listView(List<Snippet> lista, boolean kaikki) {
        VBox palautettava = pohja("snippet");
        ListView<Snippet> lv = new ListView<Snippet>();
        ObservableList<Snippet> items = FXCollections.observableArrayList(lista);
        lv.setItems(items);
        lv.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Snippet>() {
            @Override
            public void changed(ObservableValue<? extends Snippet> ov, Snippet old_val, Snippet new_val) {
                root.setCenter(single_snippet_view(new_val, lista, kaikki));
            }
        }
        );
        lv.setCellFactory(param -> new ListCell<Snippet>() {
            @Override
            protected void updateItem(Snippet snippet, boolean empty) {
                super.updateItem(snippet, empty);
                if (snippet == null || snippet.getName() == null) {
                    setText(null);
                } else {
                    if (kaikki) {
                        setText("Name: " + snippet.getName() + "\nLanguage: " + snippet.getLanguageString() + "\nTags: " + snippet.printTags());
                    } else {
                        setText("Name: " + snippet.getName() + "\nTags: " + snippet.printTags());
                    }
                }
            }
        });
        palautettava.getChildren().add(lv);
        return palautettava;
    }

    public Node addSnippet() {
        return single_snippet_view(new Snippet(sm.getLanguage(), "", "", new ArrayList<>()), new ArrayList<>(), false);
    }

    public static void main(String[] args) {
        launch(FxGUI.class);
    }

    private void languageChange(Button select, Button browseLanguage) {
        select.setText("Language: " + sm.getLanguageString());
        browseLanguage.setText("Browse in\n" + sm.getLanguageString());
    }

}
