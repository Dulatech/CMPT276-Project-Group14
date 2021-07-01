package com.example;

public class Users {
    private Integer id;
    private String UserName;
    private String FullName;
    private String Password;
    private String Email;
    private String Phone;
    private String Address;
    private String UserType;

    public Integer getID(){
        return this.id;
    }

    public String getFullName(){
        return this.FullName;
    }

    public String getUserName(){
        return this.UserName;
    }

    public String getPassword(){
        return this.Password;
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

    public String getUserType(){
        return this.UserType;
    }

    public void setID(Integer id){
         this.id = id;
    }

    public void setFullName(String FullName){
         this.FullName = FullName;
    }

    public void setUserName(String UserName){
         this.UserName = UserName;
    }

    public void setPassword(String Password){
         this.Password = Password;
    }

    public void setEmail(String Email){
         this.Email = Email;
    }

    public void setPhone(String Phone){
         this.Phone = Phone;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public void setUserType(String UserType){
        this.UserType = UserType;
    }
}
