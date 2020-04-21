# SnippetManager
(of your dreams)

Sovelluksen avulla on mahdollista tallentaa ja katsella myöhemmin näppäriä koodinpätkiä mitä on tullut kirjoitettua. Sovellus on toteutettu yhdelle käyttäjälle (tässä vaiheessa), mutta koska
tietokantana käytetään tekstitiedostoja, niiden jakaminen on hyvin helppoa muiden tekemillä työkaluilla (esim. USB-tikku, liitetiedosto, jne.) Tässä vaiheessa toteutus on puhtaasti teksti-
pohjainen.

### Riippuvuuksista

Tässä vaiheessa sovellus edellyttää ainakin seuraavia: Java11, maven, testaukseen jacoco ja surefire(?), junit 4.12. Riippuvuudet ovat pom.xml:ssä, joten jos koneelta löytyy java11 ja maven,
pitäisi toimia.

### Dokumentaatio

* Käyttöohje (ei ole)
* [määrittelydokumentti](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/maarittelydokumentti.md)
* [Arkkitehtuurikuvaus](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/arkkitehtuuri.md)
* Testausdokumentti (ei ole)
* [työaikakirjanpito](https://github.com/sanikk/ot-harjoitust/blob/master/dokumentointi/tuntikirjanpito.md)

### Releaset

[SnippetManager v1.0](.)

## Komentorivitoiminnot

### Suoritus komentoriviltä

mvn compile exec:java -Dexec.mainClass=himapaja.snippetmanager.SnippetManagerApp

### Testaus

Testit suoritetaan komennolla

mvn test

Tässä vaiheessa testataan: FileSnippetDao, FileLanguageDao, sekä luokkien Snippet ja Language antamia tulostuksia, Snippet luokasta lähinnä data() metodin antamaa suoraan tiedostoon tallennettavaa
muotoa.

Testikattavuusraportti luodaan komennolla

mvn test jacoco:report

Kattavuusraporttia voi tarkastella selaimella avaamalla sillä tiedoston target/site/jacoco/index.html . Toistaiseksi kattavuus on aika pieni, testaan vain oleellisimmat asiat. Rivikattavuus ilman käyttöliittymää on nyt 33%(+11%).

### Suoritettavan jarin generointi

mvn package

generoi hakemistoon target suoritettavan jar-tiedoston SnippetManager-1.0-SNAPSHOT.jar

### JavaDoc

Ei ole vielä.

### Checkstyle

Tarkistus suoritetaan komennolla

mvn jxr:jxr checkstyle:checkstyle

Ei pitäisi olla virheitä, tosin TextUI on jätetty tarkistuksen ulkopuolelle. Siistin sen kyllä lähes virheettömäksi mutta muutama pidempi metodi siellä.

