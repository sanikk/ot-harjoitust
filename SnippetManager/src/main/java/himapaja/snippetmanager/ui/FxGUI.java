package himapaja.snippetmanager.ui;

import himapaja.snippetmanager.domain.Language;
import himapaja.snippetmanager.domain.Snippet;
import himapaja.snippetmanager.logic.SnippetManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Graafinen käyttöliittymä SnippetManagerille
 *
 * @author Samuli Nikkilä
 */
public class FxGUI extends Application {

    private SnippetManager sm;
    private BorderPane root;
    private Map<String, Button> buttonMap = new HashMap<>();
    private Map<String, TextField> textMap = new HashMap<>();
    private TextArea codeTextArea;

    /**
     * Nappitehdas luo napin annetulla tekstillä ja asettaa sen maksimileveyteen
     *
     * @param teksti Nappiin tuleva teksti
     *
     * @return Button palauttaa napin käytettäväksi muualla. Nappi vaatii vielä
     * handlerin.
     */
    private static Button luoNappi(String teksti) {
        Button palautettava = new Button(teksti);
        palautettava.setMaxWidth(Double.MAX_VALUE);
        return palautettava;
    }

    /**
     * VBox tehdas luo sopivia pohjia keskinäkymälle. Ylimmäksi tulee otsikko
     * (Text)
     *
     * @param teksti Otsikon osaksi asetettava teksti.
     *
     * @return VBox Pohjaksi valmiiksi pohjustettu VBox-kehikko.
     */
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

    /**
     * Hakukenttä-tehdas joka palauttaa HBoxeja joissa on hakuun soveltuvat
     * kentät ja nappi
     *
     * @return HBox Pohjustettu kehikko jossa on kenttä tageille ja toinen
     * geneerisille etsintäsanoille.
     */
    private HBox hakutoiminnot() {
        HBox palautettava = new HBox(10);
        palautettava.setPadding(new Insets(25, 0, 10, 20));
        palautettava.setSpacing(10);
        Label tagLabel = new Label("Tag:");
        TextField tagField = new TextField("");
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField("");
        Button searchButton = new Button("Search");
        textMap.put("searchTagField", tagField);
        textMap.put("searchTitleField", titleField);
        buttonMap.put("searchButton", searchButton);
        palautettava.getChildren().addAll(tagLabel, tagField, titleLabel, titleField, searchButton);
        return palautettava;
    }

    /**
     * Staattinen metodi jolla päivitetään select- ja browseLanguage-napit
     * näyttämään Snippetmanagerissa aktiiviseksi valittu ohjelmointikieli.
     *
     * @param select Select-nappi
     *
     * @param browseLanguage listaa valitulla kielellä-nappi
     *
     * @param language Snippetmanagerissa valittu kieli
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     */
    private static void languageChange(Button select, Button browseLanguage, String language) {
        select.setText("Language: " + language);
        browseLanguage.setText("Browse in\n" + language);
    }

    /**
     * Varsinaisen graafisen käyttöliittymän logiikka
     *
     * @param primaryStage tulee javafx launcherilta
     */
    @Override
    public void start(Stage primaryStage) {
        this.sm = new SnippetManager("sql");

        //STAATTISET ELEMENTIT
        root = new BorderPane();
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
        buttonMap.put("select", select);
        Button browseLanguage = luoNappi("Browse in\n" + sm.getLanguageString());
        buttonMap.put("browseLanguage", browseLanguage);
        Button browseAll = luoNappi("Browse ALL");
        Button addSnippet = luoNappi("Add snippet");
        Button quit = luoNappi("Quit");
        home.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(homepage());
            }
        });
        select.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(selectLanguage());
            }
        });
        browseLanguage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (sm.getLanguageString().equals("NONE")) {
                    root.setCenter(selectLanguage());
                } else {
                    root.setCenter(listView(false, null));
                }
            }
        });
        browseAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(listView(true, null));
            }
        });
        addSnippet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (sm.getLanguageString().equals("NONE")) {
                    root.setCenter(selectLanguage());
                } else {
                    root.setCenter(addSnippet());
                }
            }
        });
        quit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        vasen.getChildren().addAll(home, select, browseLanguage, browseAll, addSnippet, quit);

        //kehystä
        root.setTop(otsikko);
        root.setLeft(vasen);
        root.setCenter(selectLanguage());

        Scene scene = new Scene(root, 1000, 600);

        primaryStage.setTitle("SnippetManager v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Kotisivun palauttava (staattinen) metodi. Palauttaa vielä null.
     *
     * @return null Näkymän keskialue asetetaan tyhjäksi.
     */
    public static Node homepage() {
        return null;
    }

    /**
     * Kielenvalinta-näkymä. Molemmat napit välitetään kartan avulla metodille
     * joka vaihtaa niiden sisältämiä tekstejä heijastamaan valittua kieltä.
     *
     * @param select Select-nappi
     *
     * @param browseLanguage BrowseLanguage-nappi
     *
     * @return Node Palauttaa kielenvalinta-näkymän.
     */
    private Node selectLanguage() {
        VBox palautettava = pohja("language");
        ListView<Language> lv = new ListView<Language>();
        ObservableList<Language> items = FXCollections.observableArrayList(sm.getLanguages());
        lv.setItems(items);
        lv.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Language>() {
                    @Override
                    public void changed(ObservableValue<? extends Language> ov, Language oldVal, Language newVal) {
                        sm.setSelected(newVal);
                        languageChange(buttonMap.get("select"), buttonMap.get("browseLanguage"), sm.getLanguageString());
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

    /**
     * Staattinen metodi joka luo yhden_katkelman- ja luo_katkelma-näkymissä
     * name-rivin alkuarvoilla parametrien mukaan
     *
     * @param name Arvon näytettävä nimi (name)
     *
     * @param data Muokattattavaan tekstikenttään aluksi tulevat tekstit
     *
     * @param key Avain jolla TextField tallennetaan textMap:piin
     *
     * @return HBox Laatikko joka sisältää ominaisuuden nimen ja muokattavan
     * tekstikentän.
     */
    private HBox singleSnippetName(String name, String data, String key) {
        HBox singleRow = new HBox();
        Label singleLabel = new Label(name + ":");
        TextField singleField = new TextField(data);
        singleField.setMaxWidth(600);
        singleField.setPrefWidth(600);
        singleRow.getChildren().addAll(singleLabel, singleField);
        textMap.put("nameField", singleField);
        return singleRow;
    }

    /**
     * Luo yhden katkelman näkymissä koodi-rivin. Muokattavissa.
     *
     * @param name Rivillä näkyvä nimi
     * @param data Tekstilaatikossa aluksi näkyvä teksti
     * @return
     */
    private HBox singleSnippetCode(String name, String data) {
        HBox singleRow = new HBox();
        Label singleLabel = new Label(name + ":");
        codeTextArea = new TextArea(data);
        codeTextArea.setMaxWidth(600);
        codeTextArea.setPrefWidth(600);
        singleRow.getChildren().addAll(singleLabel, codeTextArea);
        return singleRow;
    }

    /**
     * Staattinen metodi joka luo yhden_katkelman- ja luo_katkelma-näkymissa
     * language rivin alasvetovalikolla.
     *
     * @param comboBox ObservableList muotoisen listan sisältävä alasvetovalikko
     * @param selected Language SnippetManagerissa aktiiviseksi valittu
     * ohjelmointikieli
     *
     * @see
     * himapaja.snippetmanager.logic.SnippetManager#setSelected(himapaja.snippetmanager.domain.Language)
     *
     * @return HBox Laatikko jossa Language-teksti ja alasvetovalikko
     */
    private static HBox singleSnippetLanguage(Language selected, ComboBox comboBox) {
        HBox singleRow = new HBox();
        Label languageLabel = new Label("Language:");
        comboBox.setValue(selected);
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

    /**
     * Yhden_katkelman- ja luo_katkelma näkymiin sijoitettava palaa-nappi.
     *
     * @param kaikki boolean arvo joka määrittää näytetäänkö palaa-napista
     * kaikkien kielten, vai valitun kielen listanäkymä
     *
     * @return Button Palauttaa napin jolla palataan listaus-näkymään
     */
    private HBox singleSnippetButtons(boolean kaikki) {
        HBox buttonRow = new HBox();
        Button back = new Button("BACK");
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.setCenter(listView(kaikki, null));
            }
        });
        Button saveChanges = new Button("Save Changes");
        Button deleteSnippet = new Button("Delete Snippet");
        buttonRow.getChildren().addAll(back, saveChanges, deleteSnippet);
        buttonMap.put("backButton", back);
        buttonMap.put("saveButton", saveChanges);
        buttonMap.put("deleteSnippet", deleteSnippet);
        return buttonRow;
    }

    /**
     * Metodi joka luo yhden_katkelman- ja luo_katkelma-näkymät. Kaikki arvot
     * editoitavissa.
     *
     * @param naytetaan Tällä hetkellä tarkasteltava katkelma. Tyhjä uusi
     * katkelma valitulla kielellä kun tehdään kokonaan uutta katkelmaa.
     * @param kaikki Boolean arvo joka kertoo näytetäänkö palaa-napista kaikkien
     * kielten listaa, vai vain valitun kielen listaa.
     * @return VBox Laatikko jossa on tallennetut/tallennettavat tiedot
     */
    private VBox singleSnippetView(Snippet naytetaan, boolean kaikki) {
        VBox palautettava = pohja("option");
        palautettava.setPadding(new Insets(25, 0, 10, 20));
        TextField tags = new TextField(naytetaan.printTags());
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(sm.getLanguages()));
        palautettava.getChildren().addAll(
                singleSnippetName("Name", naytetaan.getName(), "nameField"), singleSnippetLanguage(naytetaan.getLanguage(), comboBox),
                singleSnippetCode("Code", naytetaan.getCode()), tags, singleSnippetButtons(kaikki));
        buttonMap.get("deleteSnippet").setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sm.deleteSnippet(naytetaan);
                root.setCenter(listView(kaikki, null));
            }
        });
        buttonMap.get("saveButton").setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String name = textMap.get("nameField").getText();
                String code = codeTextArea.getText();
                Language language = (Language) comboBox.getValue();
                List<String> lista = new ArrayList<>();
                String[] tagit = tags.getText().split(",");
                for (String tag : tagit) {
                    lista.add(tag.trim());
                }
                if (naytetaan.getName().isEmpty() && !name.isEmpty() && !code.isEmpty() && language != null) {
                    sm.createSnippet(language, name, code, lista);
                    root.setCenter(listView(kaikki, null));
                } else {
                    if (!name.equals(naytetaan.getName()) || language != naytetaan.getLanguage() || !code.equals(naytetaan.getCode())) {
                        naytetaan.setName(name);
                        naytetaan.setLanguage(language);
                        naytetaan.setCode(code);
                        naytetaan.setTags(lista);
                        sm.updateSnippet(naytetaan);
                        root.setCenter(listView(kaikki, null));
                    }
                }
            }
        });
        return palautettava;
    }

    /**
     * Lista-näkymä katkelmille. Boolean kaikki määrittää näytetäänkö kaikki vai
     * vain tietyn kielen katkelmat. Katkelman valitsemalla sitä voi tarkastella
     * lähemmin
     *
     * @param kaikki Boolean, jos false, tästä palataan tietyn kielen katkelmien
     * listaus-näkymään, jos true, kaikkien kielten katkelmien listaan.
     * @return VBox joka sisältää listan katkelmista.
     */
    private VBox listView(boolean kaikki, List<Snippet> lista) {
        VBox palautettava = pohja("snippet");
        if (lista == null && kaikki) {
            lista = sm.getSnippetLongList();
        } else if (lista == null) {
            lista = sm.getSnippetList();
        }
        palautettava.getChildren().add(hakutoiminnot());
        ListView<Snippet> lv = new ListView<Snippet>();
        ObservableList<Snippet> items = FXCollections.observableArrayList(lista);
        lv.setItems(items);
        lv.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Snippet>() {
                    @Override
                    public void changed(ObservableValue<? extends Snippet> ov, Snippet oldVal, Snippet newVal) {
                        root.setCenter(singleSnippetView(newVal, kaikki));
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
        buttonMap.get("searchButton").setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String tag = textMap.get("searchTagField").getText();
                String title = textMap.get("searchTitleField").getText();
                ObservableList<Snippet> newItems = null;
                if (title.isEmpty() && tag.isEmpty()) {
                    if (kaikki) {
                        newItems = FXCollections.observableArrayList(sm.getSnippetLongList());
                    } else {
                        newItems = FXCollections.observableArrayList(sm.getSnippetList());
                    }

                } else if (title.isEmpty()) {
                    if (kaikki) {
                        newItems = FXCollections.observableArrayList(sm.findByTag(tag));
                    } else {
                        newItems = FXCollections.observableArrayList(sm.findByTagAndLanguage(tag));
                    }
                } else if (tag.isEmpty()) {
                    if (kaikki) {
                        newItems = FXCollections.observableArrayList(sm.findByTitle(title));
                    } else {
                        newItems = FXCollections.observableArrayList(sm.findByTitleAndLanguage(title));
                    }
                } else {
                    if (kaikki) {
                        newItems = FXCollections.observableArrayList(sm.findByTitleAndTag(title, tag));
                    } else {
                        newItems = FXCollections.observableArrayList(sm.findByTitleAndTagAndLanguage(title, tag));
                    }
                }
                lv.setItems(newItems);
            }
        });
        palautettava.getChildren().add(lv);
        return palautettava;
    }

    /**
     * Parametrien välittäjä yhden_katkelman näkymälle kun sitä käytetään uuden
     * katkelman luomiseen. Luo uuden tyhjän katkelman jota käytetään pohjana
     * näkymässä.
     *
     * @return VBox singleSnippetView
     */
    public Node addSnippet() {
        return singleSnippetView(new Snippet(sm.getLanguage(), "", "", new ArrayList<>()), false);
    }

    public static void main(String[] args) {
        launch(FxGUI.class);
    }
}
