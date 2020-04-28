# Arkkitehtuurikuvaus

## Rakenne

Nelitasoinen rakenne, käyttöliittymä keskustelee domainin kanssa, joka
välittää tiedot palvelukerrokselle, jolla on tiedot käytettävästä
tallennusmuodosta.

Pakettijako:
ui, domain, service, dao
eli käyttöliittymä, luokat, palvelut ja tallennus

### Käyttöliittymä

Tällä hetkellä pelkkä tekstikäyttöliittymä TextUI, joka jätetään cli-versioksi. Vähän aloittelin GUI:ta, saan 
jonkun vedoksen siitäkin ensi viikolla. Lisäksi on vielä TestUI kehityksen tarpeisiin. Viimeisin jää pois
"valmiista tuotteesta."

### Sovelluslogiikka

SnippetManager huolehtii kaikesta sovelluslogiikasta. Sillä on myös tiedot
kaikista palveluista.

### Luokat

Snippet on tietysti snippettien ilmentymä, Language ohjelmointikielten. Tagit on toteutettu Stringeinä, en tee 
niille omaa luokkaa.

[Luokkakaavio](Classdiagram.png)

### Palvelukerros

On ohjelmassa puhtaasti laajennettavuuden kannalta. Toistaiseksi hieman turha abstraktiokerros, samat voisi 
hoitaa ylempänä/alempana. Koitan käyttää tästä palveluita pohjana eräässä omassa projektissa, siksi ylimääräinen
kerros.

### Tietojen tallennus

Eri daot huolehtivat eri tallennusmuodoista. Valmiina tällä hetkellä 
tiedostoon tallennus, ja h2-tietokantaan tallennus artisaani-sql-lauseilla.

[takaisin README:hin](https://github.com/sanikk/ot-harjoitust/blob/master/README.md)
