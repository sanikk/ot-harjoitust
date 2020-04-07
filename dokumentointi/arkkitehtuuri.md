# Arkkitehtuurikuvaus

## Rakenne

Nelitasoinen rakenne, käyttöliittymä keskustelee domainin kanssa, joka
välittää tiedot palvelukerrokselle, jolla on tiedot käytettävästä
tallennusmuodosta.

snippetmanager.ui

snippetmanager.domain

snippetmanager.service

snippetmanager.dao

### Käyttöliittymä

Tällä hetkellä pelkkä tekstikäyttöliittymä TextUI, joka jätetään cli-versioksi.

### Sovelluslogiikka

SnippetManager huolehtii kaikesta sovelluslogiikasta. Sillä on myös tiedot
kaikista palveluista.

### Palvelukerros

On ohjelmassa puhtaasti laajennettavuuden kannalta. Toistaiseksi hieman turha
abstraktiokerros, samat voisi hoitaa ylempänä/alempana.

### Tietojen tallennus

Eri daot huolehtivat eri tallennusmuodoista. Valmiina tällä hetkellä 
tiedostoon tallennus, teen vielä yhden sqlDao version.

[takaisin README:hin](https://github.com/sanikk/ot-harjoitust/blob/master/README.md)
