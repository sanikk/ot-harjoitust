package com.mycompany.unicafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

//TSILJOONA TESTIÄ VAIKKA VÄHEMMÄLLÄKIN OLIS PÄRJÄNNY

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(0);
    }

    //INIT TESTIT
    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(kassa != null);
    }

    @Test
    public void rahamaaraAlussaOikein() { //1000
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraOikea() { //0
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void myytyjenMaukkaidenLounaidenMaaraOikea() { //0
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    //KÄTEISOSTO TESTIT
    @Test
    public void kateisostoMaukasRahaRiittaaVaihtorahat() { //yes
        assertEquals(0, kassa.syoMaukkaasti(400));
    }

    @Test
    public void kateisostoMaukasRahaRiittaaMyydytKasvaa() { //yes

        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoMaukasRahaRiittaaKassaKasvaa() { //yes
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void kateisostoMaukasRahaEiRiitaVaihtorahat() { //no
        assertEquals(300, kassa.syoMaukkaasti(300));
    }

    @Test
    public void kateisostoMaukasRahaEiRiitaMyydytEiKasva() { //no
        kassa.syoMaukkaasti(0);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoMaukasRahaEiRiitaKassaEiKasva() { //no
        kassa.syoMaukkaasti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kateisostoEdullinenRahaRiittaaVaihtorahat() { //yes
        assertEquals(760, kassa.syoEdullisesti(1000));
    }

    @Test
    public void kateisostoEdullinenRahaRiittaaMyydytKasvaa() { //yes

        kassa.syoEdullisesti(300);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kateisostoEdullinenRahaRiittaaKassaKasvaa() { //yes
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void kateisostoEdullinenRahaEiRiitaVaihtorahat() { //no
        assertEquals(230, kassa.syoEdullisesti(230));
    }

    @Test
    public void kateisostoEdullinenRahaEiRiitaMyydytEiKasva() { //no
        kassa.syoEdullisesti(0);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kateisostoEdullinenRahaEiRiitaKassaEiKasva() { //no
        kassa.syoEdullisesti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    //KORTTIOSTOTESTIT
    @Test
    public void korttiostoEdullinenRahaRiittaaKortiltaRahaa() {
        kortti.lataaRahaa(240);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kortti.saldo());

    }

    @Test
    public void korttiostoEdullinenRahaRiittaaMyyntiKasvaa() {
        kortti.lataaRahaa(300);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttiostoEdullinenRahaRiittaaKassaEiKasva() {
        kortti.lataaRahaa(100000);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void korttiostoEdullinenRahaEiRiitaKortiltaEiRahaa() {
        kortti.lataaRahaa(14);
        kassa.syoEdullisesti(kortti);
        assertEquals(14, kortti.saldo());
    }

    @Test
    public void korttiostoEdullinenRahaEiRiitaMyyntiEiKasva() {
        kortti.lataaRahaa(0);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttiostoEdullinenRahaEiRiitaKassaEiKasva() {
        kortti.lataaRahaa(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());

    }
    @Test
    public void korttiostoMaukasRahaRiittaaKortiltaRahaa() {
        kortti.lataaRahaa(400);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kortti.saldo());

    }

    @Test
    public void korttiostoMaukasRahaRiittaaMyyntiKasvaa() {
        kortti.lataaRahaa(900);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoMaukasRahaRiittaaKassaEiKasva() {
        kortti.lataaRahaa(100000);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void korttiostoMaukasRahaEiRiitaKortiltaEiRahaa() {
        kortti.lataaRahaa(14);
        kassa.syoMaukkaasti(kortti);
        assertEquals(14, kortti.saldo());
    }

    @Test
    public void korttiostoMaukasRahaEiRiitaMyyntiEiKasva() {
        kortti.lataaRahaa(0);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoMaukasRahaEiRiitaKassaEiKasva() {
        kortti.lataaRahaa(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());

    }
    
    //KORTIN LATAUS KASSAPÄÄTTEELLÄ
    
    @Test
    public void latausKortinSaldoKasvaa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void latausKassaanTuleeRahaa() {
        kassa.lataaRahaaKortille(kortti, 333333);
        assertEquals(433333, kassa.kassassaRahaa());
    }
    
    @Test
    public void latausNegatiivinenSummaKortinSaldo() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void latausNegatiivinenSummaKassassaRahaa() {
        kassa.lataaRahaaKortille(kortti, -666);
        assertEquals(100000, kassa.kassassaRahaa());
    }

}
