package com.example;
import java.util.Date;

public class Reservations {
    private Integer id;
    private String FullName;
    private String Restaurant;
    private Integer NumberofGuests;
    private String Phone;
    private Date Time;
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

    public Date getTime(){
        return this.Time;
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

    public void setTime(Date Time){
         this.Time = Time;
    }

    public void setTableType(String TableType){
         this.TableType = TableType;
    }

    public void setPhone(String Phone){
         this.Phone = Phone;
    }

}
