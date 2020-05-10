# Testausdokumentti

Ohjelmaa on testattu sekä automaattisesti junit-yksikkötesteillä, että järjestelmää 
manuaalisesti.

### Sovelluslogiikka

Sovelluslogiikan testauksesta löytyvät suurimmat puutteet. Käytännössä
en ole pitänyt erityisen tarpeellisena pelkkien return lauseiden muodostavien
metodien testausta erikseen. Dao testit testaavat melko suoraan koko pinoa.

### Dao-luokat

Erityisesti SQL-versiot on testattu tarpeeksi kattavasti. File-versiot jäivät
hieman sivuun SQL:n valmistuttua. Ne toimivat kuitenkin edelleen.

## Sovellukseen jääneet laatuongelmat

Liian vähän testausta, liian vähän kirjoitusta.
