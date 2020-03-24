package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(90);
        assertEquals(100, kortti.saldo());
    }

    @Test
    public void otaRahaaOlemassa() {
        kortti.otaRahaa(5);
        assertTrue(true);
    }

    @Test
    public void otaRahaaSaldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(10);
        assertEquals(0, kortti.saldo());
    }

    @Test
    public void otaRahaaSaldoEiMuutuJosRahaEiRiita() {
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void otaRahaaPalauttaaTrueJosTarpeeksiRahaa() {
        assertTrue(kortti.otaRahaa(10));
    }

    @Test
    public void otaRahaaPalauttaaFalseJosEiTarpeeksiRahaa() {
        assertFalse(kortti.otaRahaa(20));
    }
}
