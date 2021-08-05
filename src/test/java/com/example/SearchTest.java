package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    static Search r;

    @BeforeAll
    static void setUp() {
        r = new Search();
        r.setID(1);
        r.setParam("test");
    }

    @Test
    public void searchIDTest() {
        assertEquals(1, r.getID());
        r.setID(2);
        assertEquals(2, r.getID());
    }

    @Test
    public void searchParamTest() {
        assertEquals("test", r.getParam());
        r.setParam("aaaaaa");
        assertEquals("aaaaaa", r.getParam());
    }


    
}