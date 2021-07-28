package com.example;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Reviews {
    private Integer id;
    private Integer UserId;
    private Integer RestaurantID;
    private String FullName;
    private String Restaurant;
    private String Time;
    private Date date;
    private String comment;
    private Integer rating;
   

    public Integer getID(){
        return this.id;
    }

    public Integer getUserID(){
        return this.UserId;
    }

    public Integer getRestaurantID(){
        return this.RestaurantID;
    }

    public String getRestaurant(){
        return this.Restaurant;
    }

    public String getFullName(){
        return this.FullName;
    }

    public String getTime(){
        return this.Time;
    }

    public String getDate(){
        return this.date.toString();
    }

    public String getComment(){
        return this.comment;
    }

    public Integer getRating(){
        return this.rating;
    }
    
    public void setID(Integer id){
         this.id = id;
    }

    public void setUserID(Integer id){
        this.UserId = id;
   }

   public void setRestaurantID(Integer id){
        this.RestaurantID = id;
    }

    public void setRestaurant(String Restaurant){
         this.Restaurant = Restaurant;
    }

    public void setFullName(String FullName){
         this.FullName = FullName;
    }

    public void setTime(String Time){
         this.Time = Time;
    }

    public void setDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm");
        date = format.parse(Time);
   }

    public void setComment(String comment){
         this.comment = comment;
    }

    public void setRating(int rating){
         this.rating = rating;
    }

}
