package com.example;

public class Favorites {
    private Integer id; 
    private Integer userID;
    private Integer restaurantID;

    public Integer getUserID() {
        return this.userID;
    }

    public Integer getRestaurantID() {
        return this.restaurantID;
    }

    public Integer getID() {
        return this.id;
    }

    public void setUserID(Integer uid) {
        this.userID = uid;
    }

    public void setRestaurantID(Integer rid) {
        this.restaurantID = rid;
    }

    public void setID(Integer id) {
        this.id = id; 
    } 
}
