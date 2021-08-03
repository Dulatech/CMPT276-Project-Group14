package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    static Reservations r;

    @BeforeAll
    static void setUp() {
        r = new Reservations();
        r.setUserID(49);
        r.setRestaurantID(3);
        r.setID(2);
        r.setPhone("215646514");
    }

    @Test
    public void UserIDTest() {
        assertEquals(49, r.getUserID());
        r.setUserID(23);
        assertEquals(23, r.getUserID());
    }

    @Test 
    public void restaurantIDTest() {
        assertEquals(3, r.getRestaurantID());
        r.setRestaurantID(54);
        assertEquals(54, r.getRestaurantID());
    }

    @Test
    public void idTest() {
        assertEquals(2, r.getID());
        r.setID(15);
        assertEquals(15, r.getID());
    }

    @Test
    public void PhoneTest() {
        assertEquals("215646514", r.getPhone());
        r.setPhone("6245211554");
        assertEquals("6245211554", r.getPhone());
    }
    
}