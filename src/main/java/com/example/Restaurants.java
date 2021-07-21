package com.example;

public class Restaurants {
    private Integer id;
    private Integer OwnerID;
    private String Name;
    private String Description;
    private String Cuisine;
    private String Email;
    private String Phone;
    private String Address;
    private String StartTime;
    private String EndTime;
    private Integer SingleTables;
    private Integer DoubleTables;
    private Integer FourPersonTables;
    private Integer PartyTables;
   

    public Integer getID(){
        return this.id;
    }

    public Integer getOwnerID(){
        return this.OwnerID;
    }
    
    public String getName(){
        return this.Name;
    }

    public String getDescription(){
        return this.Description;
    }
    
    public String getCuisine(){
        return this.Cuisine;
    }
    public String getEmail(){
        return this.Email;
    }


    public String getPhone(){
        return this.Phone;
    }

    public String getAddress(){
        return this.Address;
    }

    public String getEndTime(){
        return this.EndTime;
    }

    public String getStartTime(){
        return this.StartTime;
    }

    public Integer getSingleTables(){
        return this.SingleTables;
    }
    public Integer getDoubleTables(){
        return this.DoubleTables;
    }
    public Integer getFourPersonTables(){
        return this.FourPersonTables;
    }
    public Integer getPartyTables(){
        return this.PartyTables;
    }

    
    
    public void setID(Integer id){
         this.id = id;
    }

   

    public void setOwnerID(Integer id){
         this.OwnerID = id;
    }
    
    public void setName(String Name){
         this.Name= Name;
    }

    public void setDescription(String Description){
        this.Description= Description;
   }
    
    public void setCuisine(String Cuisine){
         this.Cuisine= Cuisine;
    }
    public void setEmail(String Email){
         this.Email= Email;
    }


    public void setPhone(String Phone){
         this.Phone= Phone;
    }

    public void setAddress(String Address){
         this.Address= Address;
    }

    public void setEndTime(String EndTime){
    this.EndTime =EndTime;
    }

    public void setStartTime(String StartTime){
    this.StartTime = StartTime;
    }

    public void setSingleTables(Integer SingleTables){
         this.SingleTables= SingleTables;
    }
    public void setDoubleTables(Integer DoubleTables){
         this.DoubleTables= DoubleTables;
    }
    public void setFourPersonTables(Integer FourPersonTables){
         this.FourPersonTables= FourPersonTables;
    }
    public void setPartyTables(Integer PartyTables){
         this.PartyTables= PartyTables;
    }
    
}
