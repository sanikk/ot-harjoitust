# Käyttöohje

Lataa tiedosto [SnippetManager.jar](link)

## Konfigurointi

Mikäli ohjelman hakemistossa ei ole snippetdb.mv.db tiedostoa, ohjelma luo uuden tietokannan. Ohjelmaan on toteutettu myös
tavallista tekstitiedostoa hyödyntävä tallennusmahdollisuus, mutta siihen ei vielä pääse käsiksi edes komentorivi-parametreillä.

Tekstitiedostoon tallentamisessa hyödynnetään config.properties-tiedostosta löytyviä tiedostonnimiä.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla
'''
java -jar SnippetManager.jar
'''

Mikäli haluaa kokeilla pidemmälle edennyttä tekstikäyttöliittymää se käynnistetään komennolla
'''
java -jar SnippetManager.jar text
'''
Oletuksena on siis GUI:n käyttö, vaikka siitä puuttuukin vielä: mahdollisuus tallentaa tageja, sekä etsiä eri hakuehdoilla.

## Tekstikäyttöliittymän ohjeet

Yleisesti ottaen kaikki vaihtoehdot on kerrottu ruudulla. Rivinvaihto ilman muita syötteitä palaa alivalikoista takaisin kohti päävalikkoa,
ja 0 lopettaa päävalikossa.

### Aloitus ja kielen vaihtaminen (teksti)

Aluksi ohjelma kysyy mitä kieltä halutaan juuri nyt aktiivisesti käyttää, tämä vaikuttaa tallentamiseen, ja toiseen
listaus/haku-muotoon. Sen jälkeen avautuu päävalikko vaihtoehtoineen. Kieltä voi vaihtaa myöhemminkin päävalikosta, vaihtoehto 99.

### Koodinpätkien lisääminen

Valitsemalla valikosta vaihtoehdon yksi pääsee syöttämään uuden koodinpätkän tietokantaan. Tällä hetkellä tekstimuotoisessa käyttöliittymässä
tallennetaan automaattisesti valittuna olevalla kielellä.

### Pätkien listaaminen ja hakeminen

Päävalikossa on tähän kaksi vaihtoehtoa, sen mukaan haluaako listata/hakea kaikkia paikallisessa tietokannassa olevia (5), vai ainoastaan valitulla
kielellä tallennettuja pätkiä. Valitsemalla tallennetun pätkän numeron, sitä pääsee tarkastelemaan lähemmin, ja muuttamaan sen tallennettuja arvoja(TODO tekstikäyttöliittymässä).
Rivinvaihto tyhjällä tyhjällä rivillä palaa päävalikkoon. Listaa tarkastellessa voi näkyviä pätkiä rajata tagien (t) tai otsikon (f) perusteella.

## Graafisen käyttöliittymän ohjeet


