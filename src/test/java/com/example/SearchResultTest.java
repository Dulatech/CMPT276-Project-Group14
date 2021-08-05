package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class SearchResultTest {
    
    static SearchResult r;

    @BeforeAll
    static void setUp() {
        r = new SearchResult();
        r.setID(1);
          r.setOwnerID(1);
          r.setName("Kiyo Sushi House");
          r.setCuisine("Japanese");
          r.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
          r.setEmail("kiyosushi@gmail.com");
          r.setPhone("7788889999");
          r.setAddress("11111 Cambie Road, Richmond, BC, Canada");
          r.setStartTime("9:00");
          r.setEndTime("21:00");
          r.setSingleTables(2);
          r.setDoubleTables(3);
          r.setFourPersonTables(4);
          r.setPartyTables(4);
          r.setFavorite(0);
    }

    @Test
    public void searchResultIDTest() {
        assertEquals(1, r.getID());
        r.setID(2);
        assertEquals(2, r.getID());
    }

    @Test
    public void searchResultOwnerIDTest() {
        assertEquals(1, r.getOwnerID());
        r.setOwnerID(2);
        assertEquals(2, r.getOwnerID());
    }

    @Test
    public void searchResultNameTest() {
        assertEquals("Kiyo Sushi House", r.getName());
        r.setName("Kiyo Sushi");
        assertEquals("Kiyo Sushi", r.getName());
    }

    @Test
    public void searchResultCuisineTest() {
        assertEquals("Japanese", r.getCuisine());
        r.setCuisine("Sushi");
        assertEquals("Sushi", r.getCuisine());
    }

    @Test
    public void searchResultDescriptionTest() {
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", r.getDescription());
        r.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum id.");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum id.", r.getDescription());
    }

    @Test
    public void searchResultEmailTest() {
        assertEquals("kiyosushi@gmail.com", r.getEmail());
        r.setEmail("kiyosushi@hotmail.com");
        assertEquals("kiyosushi@hotmail.com", r.getEmail());
    }

    @Test
    public void searchResultAddressTest() {
        assertEquals("11111 Cambie Road, Richmond, BC, Canada", r.getAddress());
        r.setAddress("22222 Cambie Road, Richmond, BC, Canada");
        assertEquals("22222 Cambie Road, Richmond, BC, Canada", r.getAddress());
    }


    @Test
    public void searchResultPhoneTest() {
        assertEquals("7788889999", r.getPhone());
        r.setPhone("7788889998");
        assertEquals("7788889998", r.getPhone());
    }

    @Test
    public void searchResultStartTimeTest() {
        assertEquals("9:00", r.getStartTime());
        r.setStartTime("9:30");
        assertEquals("9:30", r.getStartTime());
    }


    @Test
    public void searchResultEndTimeTest() {
        assertEquals("21:00", r.getEndTime());
        r.setEndTime("21:30");
        assertEquals("21:30", r.getEndTime());
    }

    @Test
    public void searchResultSingleTablesTest() {
        assertEquals(2, r.getSingleTables());
        r.setSingleTables(3);
        assertEquals(3, r.getSingleTables());
    }

    @Test
    public void searchResultDoubleTablesTest() {
        assertEquals(3, r.getDoubleTables());
        r.setDoubleTables(4);
        assertEquals(4, r.getDoubleTables());
    }

    @Test
    public void searchResultFourPersonTablesTest() {
        assertEquals(4, r.getFourPersonTables());
        r.setFourPersonTables(5);
        assertEquals(5, r.getFourPersonTables());
    }

   

    @Test
    public void searchResultPartyTablesTest() {
        assertEquals(4, r.getPartyTables());
        r.setPartyTables(5);
        assertEquals(5, r.getPartyTables());
    }

    @Test
    public void searchResultFavoriteTest() {
        assertEquals(0, r.getFavorite());
        r.setFavorite(1);
        assertEquals(1, r.getFavorite());
    }

  
    
}