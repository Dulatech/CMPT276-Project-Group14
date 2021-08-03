package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    static Users user;

    @BeforeAll
    static void setUp() {
        user = new Users();
        user.setID(100);
        user.setUserName("userName");
        user.setFullName("Ricky");
        user.setPassword("111");
        user.setEmail("myEmail@hotmail.com");
        user.setPhone("6041111111");
        user.setAddress("3202 Parker Street, Vancouver BC, Canada");
        user.setUserType("C");
    }

    @Test
    public void userIDTest() {
        assertEquals(100, user.getID());
        user.setID(75);
        assertEquals(75, user.getID());
    }

    @Test
    public void userUserNameTest() {
        assertEquals("userName", user.getUserName());
        user.setUserName("dragonSlayer");
        assertEquals("dragonSlayer", user.getUserName());
        assertNotEquals("dragonslayer", user.getUserName());
    }
 
    @Test 
    public void userFullNameTest() {
       assertEquals("Ricky", user.getFullName());
       user.setFullName("Ricky x");
       assertEquals("Ricky x", user.getFullName());
       assertNotEquals("Ricky x ", user.getFullName());
    }

    @Test
    public void userpwTest() {
        assertEquals("111", user.getPassword());
        user.setPassword("320");
        assertEquals("320", user.getPassword());
        assertNotEquals(320, user.getPassword());
    }

    @Test 
    public void userEmailTest() {
        assertEquals("myEmail@hotmail.com", user.getEmail());
        user.setEmail("tehe@hotmail.com");
        assertEquals("tehe@hotmail.com", user.getEmail());
    }

    @Test
    public void userAddressTest() {
        assertEquals("3202 Parker Street, Vancouver BC, Canada", user.getAddress());
        user.setAddress("");
        assertEquals("", user.getAddress());
        assertNotEquals(" ", user.getAddress());
    }

    @Test 
    public void userTypeTest() {
        assertEquals("C", user.getUserType());
        user.setUserType("c");
        assertNotEquals("C", user.getUserType());
        assertEquals("c", user.getUserType());
    }

}

 