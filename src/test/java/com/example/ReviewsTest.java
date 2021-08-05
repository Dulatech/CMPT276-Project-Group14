package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class ReviewsTest {
    static Reviews r;

    @BeforeAll
    static void setUp() {
        r = new Reviews();
        r.setID(1);
        r.setUserID(1);
        r.setRestaurantID(1);
        r.setRestaurant("Kiyo Sushi House");
        r.setFullName("Bobby");
        r.setTime("2021-08-05'T'12:45");
        r.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        r.setRating(1);
    }

    @Test
    public void reviewIDTest() {
        assertEquals(1, r.getID());
        r.setID(2);
        assertEquals(2, r.getID());
    }

    @Test
    public void reviewUserIDTest() {
        assertEquals(1, r.getUserID());
        r.setUserID(2);
        assertEquals(2, r.getUserID());
    }

    @Test
    public void reviewRestaurantIDTest() {
        assertEquals(1, r.getRestaurantID());
        r.setRestaurantID(2);
        assertEquals(2, r.getRestaurantID());
    }

    @Test
    public void reviewRestaurantNameTest() {
        assertEquals("Kiyo Sushi House", r.getRestaurant());
        r.setRestaurant("Kiyo Sushi");
        assertEquals("Kiyo Sushi", r.getRestaurant());
    }

    @Test
    public void reviewFullNameTest() {
        assertEquals("Bobby", r.getFullName());
        r.setFullName("Chan");
        assertEquals("Chan", r.getFullName());
    }

    @Test
    public void reviewTimeTest() {
        assertEquals("2021-08-05'T'12:45", r.getTime());
        r.setTime("2021-08-05'T'12:50");
        assertEquals("2021-08-05'T'12:50", r.getTime());
    }

    @Test
    public void reviewCommentTest() {
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", r.getComment());
        r.setComment("hello world");
        assertEquals("hello world", r.getComment());
    }

    @Test
    public void reviewRatingTest() {
        assertEquals(1, r.getRating());
        r.setRating(5);
        assertEquals(5, r.getRating());
    }
    
}