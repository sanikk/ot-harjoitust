# Käyttöohje

Lataa tiedosto [SnippetManager.jar](link)

## Konfigurointi

Mikäli ohjelman hakemistossa ei ole snippetdb.mv.db tiedostoa, ohjelma luo uuden tietokannan. Ohjelmaan on toteutettu myös
tavallista tekstitiedostoa hyödyntävä tallennusmahdollisuus, mutta siihen ei vielä pääse käsiksi edes komentorivi-parametreillä.

Koska toiseen tallennusmuotoon ei pääse nyt jar:ista käsiksi, jätetään konffaus-ohjeet toiseen kertaan.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla
'''
java -jar SnippetManager.jar
'''

## Tekstikäyttöliittymän ohjeet

Yleisesti ottaen kaikki vaihtoehdot on kerrottu ruudulla. Rivinvaihto ilman muita syötteitä palaa alivalikoista takaisin kohti päävalikkoa,
ja 0 lopettaa päävalikossa.

### Aloitus ja kielen vaihtaminen

Aluksi ohjelma kysyy mitä kieltä halutaan juuri nyt aktiivisesti käyttää, tämä vaikuttaa tallentamiseen, ja toiseen
listaus/haku-muotoon. Sen jälkeen avautuu päävalikko vaihtoehtoineen. Kieltä voi vaihtaa myöhemminkin päävalikosta, vaihtoehto 99.

### Koodinpätkien lisääminen

Valitsemalla valikosta vaihtoehdon yksi pääsee syöttämään uuden koodinpätkän tietokantaan. Tällä hetkellä tekstimuotoisessa käyttöliittymässä
tallennetaan automaattisesti valittuna olevalla kielellä.

### Pätkien listaaminen ja hakeminen

Päävalikossa on tähän kaksi vaihtoehtoa, sen mukaan haluaako listata/hakea kaikkia paikallisessa tietokannassa olevia (5), vai ainoastaan valitulla
kielellä tallennettuja pätkiä. Valitsemalla tallennetun pätkän numeron, sitä pääsee tarkastelemaan lähemmin, ja muuttamaan sen tallennettuja arvoja(TODO).
Rivinvaihto tyhjällä tyhjällä rivillä palaa päävalikkoon. Listaa tarkastellessa voi näkyviä pätkiä rajata tagien (t) tai otsikon (f) perusteella. Näiden
yhdistelmälle keksin juuri tätä kirjoittaessa toteutuksen, toivottavasti ehdin palauttaa sen jo tällä viikolla.

