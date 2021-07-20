package com.example;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservations {
    private Integer id;
    private String FullName;
    private String Restaurant;
    private Integer NumberofGuests;
    private String Phone;
    private String Time;
    private Date date;
    private String TableType;
   

    public Integer getID(){
        return this.id;
    }

    public String getRestaurant(){
        return this.Restaurant;
    }

    public String getFullName(){
        return this.FullName;
    }

    public String getPhone(){
        return this.Phone;
    }

    public Integer getNumberofGuests(){
        return this.NumberofGuests;
    }

    public String getTime(){
        return this.Time;
    }

    public String getDate(){
        return this.date.toString();
    }

    public String getTableType(){
        return this.TableType;
    }
    
    public void setID(Integer id){
         this.id = id;
    }

    public void setRestaurant(String Restaurant){
         this.Restaurant = Restaurant;
    }

    public void setFullName(String FullName){
         this.FullName = FullName;
    }

    public void setNumberofGuests(Integer NumberofGuests){
         this.NumberofGuests = NumberofGuests;
    }

    public void setTime(String Time){
         this.Time = Time;
    }

    public void setDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm");
        date = format.parse(Time);
   }

    public void setTableType(String TableType){
         this.TableType = TableType;
    }

    public void setPhone(String Phone){
         this.Phone = Phone;
    }

}
