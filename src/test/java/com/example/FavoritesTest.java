package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class FavoritesTest {
    static Favorites f;

    @BeforeAll
    static void setUp() {
        f = new Favorites();
        f.setUserID(1);
        f.setRestaurantID(2);
        f.setID(3);
    }

    @Test
    public void UserIDTest() {
        assertEquals(1, f.getUserID());
        f.setUserID(23);
        assertEquals(23, f.getUserID());
    }

    @Test 
    public void restaurantIDTest() {
        assertEquals(2, f.getRestaurantID());
        f.setRestaurantID(69);
        assertEquals(69, f.getRestaurantID());
    }

    @Test
    public void idTest() {
        assertEquals(3, f.getID());
        f.setID(19);
        assertEquals(19, f.getID());
    }
}


 