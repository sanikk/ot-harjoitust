# SnippetManager
(of your dreams)

Sovelluksen avulla on mahdollista tallentaa ja katsella myöhemmin näppäriä koodinpätkiä mitä on tullut kirjoitettua. Sovellus on toteutettu yhdelle käyttäjälle (tässä vaiheessa), ja se tallentaa
siihen syötetyt pätkät tietokantaan.

### Riippuvuuksista

Tässä vaiheessa sovellus edellyttää ainakin seuraavia: Java11, maven, testaukseen jacoco ja surefire(?), junit 4.12. Riippuvuudet ovat pom.xml:ssä, joten jos koneelta löytyy java11 ja maven,
pitäisi toimia.

### Dokumentaatio

* [Käyttöohje](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/kayttoohje.md)
* [määrittelydokumentti](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/maarittelydokumentti.md)
* [Arkkitehtuurikuvaus](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/arkkitehtuuri.md)
* [Testausdokumentti](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/testaus.md)
* [työaikakirjanpito](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/tuntikirjanpito.md)

### Releaset

* [Final release](https://github.com/sanikk/ot-harjoitust/release/tag/final)

* [SnippetManager v1.0](https://github.com/sanikk/ot-harjoitust/releases/tag/Viikko5) eli viikon 5 tämä
* [Viikon 6 release](https://github.com/sanikk/ot-harjoitust/releases/tag/demo)

## Komentorivitoiminnot

### Suoritus komentoriviltä

mvn compile exec:java -Dexec.mainClass=himapaja.snippetmanager.SnippetManagerApp

### Testaus

Testit suoritetaan komennolla

mvn test

Tässä vaiheessa testataan: FileSnippetDao, FileLanguageDao, sekä luokkien Snippet ja Language antamia tulostuksia, Snippet luokasta lähinnä data() metodin antamaa suoraan tiedostoon tallennettavaa
muotoa. Nyt tietysti alkaa sql dao:jen testaaminen.

Testikattavuusraportti luodaan komennolla

mvn test jacoco:report

Kattavuusraporttia voi tarkastella selaimella avaamalla sillä tiedoston target/site/jacoco/index.html . Toistaiseksi kattavuus on aika pieni, testaan vain oleellisimmat asiat.

### Suoritettavan jarin generointi

mvn package

generoi hakemistoon target suoritettavan jar-tiedoston SnippetManager-1.0-SNAPSHOT.jar

### JavaDoc

Luodaan komennolla mvn javadoc:javadoc
Javadocit on tehty kolmelle luokalle.

### Checkstyle

Tarkistus suoritetaan komennolla

mvn jxr:jxr checkstyle:checkstyle

Tarkistusta ei suoriteta käyttöliittymätoteutuksille, eli tekstipohjaiselle TextUI:lle, eikä graafiselle FxGUI:lle.
Tällä hetkellä SqlSnippetDao:ssa on 4 liian pitkää metodia.
