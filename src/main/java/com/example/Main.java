/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;


import org.springframework.web.bind.annotation.SessionAttributes; 

@Controller

//session variables
@SessionAttributes({"userID", "userName", "userType"})


@SpringBootApplication
public class Main {

  // @Value("${spring.datasource.url}")
  // private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  //initialize session variable
  @ModelAttribute("userID")
  public Integer initializeUserID() {
    return -1;
  }

  @ModelAttribute("userName")
  public String initializeUserName() {
    return "";
  }

  @ModelAttribute("userType")
  public String initializeUserType() {
    return "";
  }

 
  
  @RequestMapping("/")
  String index(Map<String, Object> model, @ModelAttribute("userID") String test, @ModelAttribute("userID") String userName) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();

     
     

      ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
       
      System.out.println(test);
      
      ArrayList<Users> output = new ArrayList<Users>();
      ArrayList<Restaurants> output2 = new ArrayList<Restaurants>();
      ArrayList<Reservations> output3 = new ArrayList<Reservations>();
      ArrayList<Reviews> output4 = new ArrayList<Reviews>();
      ArrayList<Favorites> output5 = new ArrayList<Favorites>();

      while (rs.next()) {
        
        Integer id = rs.getInt("ID");
        String uName = rs.getString("UserName");
        String name = rs.getString("FullName");
        String pass = rs.getString("Password");
        String email = rs.getString("Email");
        String phone = rs.getString("Phone");
        String addr = rs.getString("Address");
        String userType = rs.getString("UserType");
        Users user = new Users();
        user.setID(id);
        user.setFullName(name);
        user.setUserName(uName);
        user.setPassword(pass);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(addr);
        user.setUserType(userType);
        output.add(user);
      }

      ResultSet rs2 = stmt.executeQuery("SELECT * FROM Restaurants");

      while (rs2.next()) {
        
        Integer id = rs2.getInt("ID");
        Integer ownerID = rs2.getInt("OwnerID");
        String name = rs2.getString("Name");
        String cus = rs2.getString("Cuisine");
        String desc = rs2.getString("Description");
        String email = rs2.getString("Email");
        String phone = rs2.getString("Phone");
        String addr = rs2.getString("Address");
        String st = rs2.getString("StartTime");
        String et = rs2.getString("EndTime");
        Integer single = rs2.getInt("SingleTables");
        Integer duo = rs2.getInt("DoubleTables");
        Integer quad = rs2.getInt("FourPersonTables");
        Integer party = rs2.getInt("PartyTables");
        Restaurants restaurant = new Restaurants();
        restaurant.setID(id);
        restaurant.setOwnerID(ownerID);
        restaurant.setName(name);
        restaurant.setCuisine(cus);
        restaurant.setDescription(desc);
        restaurant.setEmail(email);
        restaurant.setPhone(phone);
        restaurant.setAddress(addr);
        restaurant.setStartTime(st);
        restaurant.setEndTime(et);
        restaurant.setSingleTables(single);
        restaurant.setDoubleTables(duo);
        restaurant.setFourPersonTables(quad);
        restaurant.setPartyTables(party);
        output2.add(restaurant);
      }

      ResultSet rs3 = stmt.executeQuery("SELECT * FROM Reservations1");

      while (rs3.next()) {
        
        Integer id = rs3.getInt("ID");
        Integer userId = rs3.getInt("UserID");
        Integer restaurantId = rs3.getInt("RestaurantID");
        String name = rs3.getString("FullName");
        String time = rs3.getString("Time");
        String phone = rs3.getString("Phone");
        String tabletype = rs3.getString("TableType");
        Reservations reservation = new Reservations();
        reservation.setID(id);
        reservation.setUserID(userId);
        reservation.setRestaurantID(restaurantId);
        reservation.setFullName(name);
        reservation.setTime(time);
        reservation.setTableType(tabletype);
        reservation.setPhone(phone);
        output3.add(reservation);
      }

      ResultSet rs4 = stmt.executeQuery("SELECT * FROM Reviews");

      while (rs4.next()) {
        
        Integer id = rs4.getInt("ID");
        Integer userId = rs4.getInt("UserID");
        String restaurant = rs4.getString("Restaurant");
        String name = rs4.getString("FullName");
        String time = rs4.getString("Time");
        String comment = rs4.getString("Comment");
        Integer rating = rs4.getInt("Rating");
        Reviews review = new Reviews();
        review.setID(id);
        review.setUserID(userId);
        review.setRestaurant(restaurant);
        review.setFullName(name);
        review.setTime(time);
        review.setComment(comment);
        review.setRating(rating);
        output4.add(review);
      }

      ResultSet rs5 = stmt.executeQuery("SELECT * FROM Favorites");

      while (rs5.next()) {
        Integer id = rs5.getInt("ID");
        Integer uid = rs5.getInt("userID");
        Integer rid = rs5.getInt("restaurantID");
        Favorites favorite = new Favorites();
        favorite.setID(id);
        favorite.setUserID(uid);
        favorite.setRestaurantID(rid);
        output5.add(favorite);
      }


      model.put("records", output);
      model.put("records2", output2);
      model.put("records3", output3);
      model.put("records4", output4);
      model.put("records5", output5);
      return "index";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @RequestMapping("/adduser")
  String getloginForm(Map<String, Object> model) {
    Users user = new Users();
    model.put("user", user);
    return "adduser";
  }

  @PostMapping(
    path = "/adduser",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowserRectangleSubmit(Map<String, Object> model, Users user) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE UserName = '" + user.getUserName() + "' OR Email = '" + user.getEmail() + "'");

     int count =0;
     while (rs.next()) {
       count++;
     }

      if(count<=0)  {  
      
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID serial, UserName varchar(50), Password varchar(50), FullName varchar(50), Email varchar(50), Phone varchar(11), Address varchar(255), UserType varchar(1))");
      String sql = "INSERT INTO Users (UserName, Password, FullName, Email, Phone, Address, UserType) VALUES ('" + user.getUserName() + "','" + user.getPassword() 
      + "','" + user.getFullName() + "','"  + user.getEmail() + "','" + user.getPhone() + "','"   + user.getAddress() + "','" + user.getUserType()  + "')";
      stmt.executeUpdate(sql);
      model.put("user", user);
      return "login";
      } else {
        model.put("user", user);
        model.put("error", "The Username or Email already exists");
        return "adduser";
      }
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }

  @RequestMapping("/login")
  String getRectangleForm(Map<String, Object> model) {
    Users user = new Users();
    model.put("user", user);
    return "login";
  }

  @PostMapping(
    path = "/login",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String userLogin(Map<String, Object> model, Users user) throws Exception {
    // Check the user authentication in the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      String sql = "SELECT ID,UserType FROM Users WHERE UserName = '" + user.getUserName() + "' AND PASSWORD = '" + user.getPassword() + "'";
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList<Users> d = new ArrayList<Users>();
      Users output = new Users();
      

      if(rs.next()==true){

      Integer id = rs.getInt("ID"); 
    
      model.put("userID",id); // set session variable userID
      model.put("userName",user.getUserName()); // set session variable userID
      model.put("userType", rs.getString("userType"));
      output.setID(rs.getInt("ID"));
      d.add(output);
      
      String userType = rs.getString("UserType");

      /** 
      switch(userType) {
        case "A":
          return "redirect:/user/adminView"; 
        case "R":
          return "redirect:/ownerView/" + id;
        default:
          return "redirect:/user=" + id;
        }
        */
      
      return "redirect:/user=" + id;


      } else {
        return "redirect:/loginError";
      } 
    } catch (Exception e) {
      return "error";
    }
  }

  //logout 
  @RequestMapping("/logout")
  public String logout(Map<String, Object> model) {
    model.put("userID", -1); 
    model.put("userName", ""); 
    model.put("userType", "");
    return "redirect:/";
  }

  //cuser default page
  @RequestMapping("/user={id}")
  public String cuserHome(Map<String, Object> model, @PathVariable String id, @ModelAttribute("userID") String userID, @ModelAttribute("userType") String userName) {
    
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE id="+(Integer.parseInt(id))); 
       
      while (rs.next()) { //load info
        String uName = rs.getString("UserName");
        String name = rs.getString("FullName");
        String pass = rs.getString("Password");
        String email = rs.getString("Email");
        String phone = rs.getString("Phone");
        String addr = rs.getString("Address");
        String userType = rs.getString("UserType");
        model.put("id", id);
        model.put("uName", uName);
        model.put("name", name);
        model.put("pass", pass);
        model.put("email", email);
        model.put("phone", phone);
        model.put("addr", addr);
        model.put("userType", userType);
      } 
      ArrayList<Reservations> output3 = new ArrayList<Reservations>();
      ResultSet rs3 = stmt.executeQuery("SELECT * FROM Reservations2 WHERE UserID =" + userID);

      while (rs3.next()) {
        
        Integer Rid = rs3.getInt("ID");
        Integer userId = rs3.getInt("UserID");
        String restaurant = rs3.getString("Restaurant");
        String name = rs3.getString("FullName");
        String time = rs3.getString("Time");
        String phone = rs3.getString("Phone");
        String tabletype = rs3.getString("TableType");
        Reservations reservation = new Reservations();
        reservation.setID(Rid);
        reservation.setUserID(userId);
        reservation.setRestaurant(restaurant);
        reservation.setFullName(name);
        reservation.setTime(time);
        reservation.setTableType(tabletype);
        reservation.setPhone(phone);
        output3.add(reservation);
      }
      Search search = new Search();
      model.put("search", search);
      model.put("records3", output3);
      return "userHome";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }
 
  //userInfo
  @RequestMapping("/userInfo") 
  public String cuserSetting(Map<String, Object> model, @ModelAttribute("userID") String id, @ModelAttribute("userID") String userName) {
    if (id.equals("-1")) {
      return "redirect:/login";
    }

    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE id=" + id); 
       
      while (rs.next()) { //load info
        String uName = rs.getString("UserName");
        String name = rs.getString("FullName");
        String pass = rs.getString("Password");
        String email = rs.getString("Email");
        String phone = rs.getString("Phone");
        String addr = rs.getString("Address");
        String userType = rs.getString("UserType");
        model.put("id", id);
        model.put("uName", uName);
        model.put("name", name);
        model.put("pass", pass);
        model.put("email", email);
        model.put("phone", phone);
        model.put("addr", addr);
        model.put("userType", userType);
      } 
      return "userInfo";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  //update info for regular user  
  @RequestMapping("/updateUserInfo/{selector}")
  public String setupUpdateInfo(Map<String, Object> model, @PathVariable String selector, @ModelAttribute("userID") String id) {
    if (id.equals("-1")) {
      return "redirect:/login";
    }

    Users user = new Users();
    model.put("selector", selector);
    model.put("user", user);
    return "updateUserInfo";
  }

  @PostMapping(
    path = "/updateUserInfo/{selector}",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )

  public String updateInfo(Map<String, Object> model, @PathVariable String selector, Users user, @ModelAttribute("userID") String id, @ModelAttribute("userID") String userName) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      //I have done it this way because selector doesn't get saved when we call PostMapping -Ricky
      if (user.getFullName() != null) { //update name
        String sql = "UPDATE Users SET FullName = '" + user.getFullName() + "' WHERE ID = '" + id + "'";
        stmt.executeUpdate(sql);
      } else if (user.getPassword() != null) { // update pass
        String sql = "UPDATE Users SET Password = '" + user.getPassword() + "' WHERE ID = '" + id + "'";
        stmt.executeUpdate(sql);
      } else if (user.getEmail() != null) { //update email
        String sql = "UPDATE Users SET Email = '" + user.getEmail() + "' WHERE ID = '" + id + "'";
        stmt.executeUpdate(sql);
      } else if (user.getAddress() != null) { //update address
        String sql = "UPDATE Users SET Address = '" + user.getAddress() + "' WHERE ID = '" + id + "'";
        stmt.executeUpdate(sql);
      } else { //error
        return "error";
      }
      
      return "redirect:/success";
    } catch (Exception e) {
      return "error";
    }
  }

  // owner view
  @RequestMapping("/ownerView/{id}")
  public String ownerView(Map<String, Object> model, @PathVariable String id) {

    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE id="+(Integer.parseInt(id)));

      while (rs.next()) { //load info
        String uName = rs.getString("UserName");
        String name = rs.getString("FullName");
        String pass = rs.getString("Password");
        String email = rs.getString("Email");
        String phone = rs.getString("Phone");
        String addr = rs.getString("Address");
        String userType = rs.getString("UserType");
        model.put("id", id);
        model.put("uName", uName);
        model.put("name", name);
        model.put("pass", pass);
        model.put("email", email);
        model.put("phone", phone);
        model.put("addr", addr);
        model.put("userType", userType);
      } 
      Search search = new Search();
      model.put("search", search);
      return "ownerView";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
      }
  }

  //admin view 
  @GetMapping("/adminView")
  public String accessAdminPage(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
      
      ArrayList<Users> output = new ArrayList<Users>();
      while (rs.next()) {
        Integer id = rs.getInt("ID");
        String uName = rs.getString("UserName");
        String name = rs.getString("FullName");
        String pass = rs.getString("Password");
        String email = rs.getString("Email");
        String phone = rs.getString("Phone");
        String addr = rs.getString("Address");
        String userType = rs.getString("UserType");

        Users user = new Users();
        user.setID(id);
        user.setFullName(name);
        user.setUserName(uName);
        user.setPassword(pass);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(addr);
        user.setUserType(userType);
        
        output.add(user);
      }

      model.put("records", output);
      return "adminView";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping("/user/delete/{pid}")
  public String deleteUser(Map<String, Object> model, @PathVariable String pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("DELETE FROM Users WHERE ID =" + pid);
      
      return "redirect:/adminView";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  //map
  @GetMapping("/map") 
    public String getMap(Map<String, Object> model, @ModelAttribute("userID") Integer userID) {
      try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        ArrayList<Restaurants> output = new ArrayList<Restaurants>();
        ArrayList<Favorites> output2 = new ArrayList<Favorites>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants");
        while (rs.next()) {
          Integer id = rs.getInt("ID");
          Integer ownerID = rs.getInt("OwnerID");
          String name = rs.getString("Name");
          String cus = rs.getString("Cuisine");
          String desc = rs.getString("Description");
          String email = rs.getString("Email");
          String phone = rs.getString("Phone");
          String addr = rs.getString("Address");
          String st = rs.getString("StartTime");
          String et = rs.getString("EndTime");
          Integer single = rs.getInt("SingleTables");
          Integer duo = rs.getInt("DoubleTables");
          Integer quad = rs.getInt("FourPersonTables");
          Integer party = rs.getInt("PartyTables");
          Restaurants restaurant = new Restaurants();
          restaurant.setID(id);
          restaurant.setOwnerID(ownerID);
          restaurant.setName(name);
          restaurant.setCuisine(cus);
          restaurant.setDescription(desc);
          restaurant.setEmail(email);
          restaurant.setPhone(phone);
          restaurant.setAddress(addr);
          restaurant.setStartTime(st);
          restaurant.setEndTime(et);
          restaurant.setSingleTables(single);
          restaurant.setDoubleTables(duo);
          restaurant.setFourPersonTables(quad);
          restaurant.setPartyTables(party);
          output.add(restaurant);
        }

        ResultSet rs2 = stmt.executeQuery("SELECT * FROM Favorites WHERE userID=" + userID);

        while (rs2.next()) {
          Integer id = rs2.getInt("ID");
          Integer uid = rs2.getInt("userID");
          Integer rid = rs2.getInt("restaurantID");
          Favorites favorite = new Favorites();
          favorite.setID(id);
          favorite.setUserID(uid);
          favorite.setRestaurantID(rid);
          output2.add(favorite);
        }

        Favorites favorite = new Favorites();
        model.put("favorite", favorite);
        model.put("userFavorites", output2);
        model.put("restaurants", output);
        model.put("id", userID);
        return "map";
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
    }

  @PostMapping(
    path = "/map",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )

  public String test(Map<String, Object> model, Favorites favorite, @ModelAttribute("userID") Integer userID) throws Exception {
     try (Connection connection = dataSource.getConnection()) { 
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Favorites (ID serial, UserID numeric, RestaurantID numeric)");
      ResultSet rs = stmt.executeQuery("SELECT * FROM Favorites WHERE userID=" + userID);
      boolean isDelete = false; 
      String sql = "INSERT INTO Favorites (UserID, RestaurantID) VALUES (" + favorite.getUserID() + "," + favorite.getRestaurantID() + ")";

      while(rs.next()) { //check if we are deleting or inserting 
        Integer rid = rs.getInt("restaurantID");
        if (rid == favorite.getRestaurantID()) {
          isDelete = true; 
          System.out.println("ENTERED");
          sql = "DELETE FROM Favorites WHERE UserID =" + userID + " AND RestaurantID = " + favorite.getRestaurantID();  
          break;
        }
      }
      stmt.executeUpdate(sql);
      model.put("favorite", favorite);
      return "redirect:/map";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping("/success")
    public String userSuccess(){
        return "success";
  }

  @GetMapping("/loginError")
    public String userError(){
        return "loginError";
  }


  @RequestMapping("/addrestaurant")
  String getRestaurantForm(Map<String, Object> model) {
    Restaurants restaurant = new Restaurants();
    model.put("restaurant", restaurant);
    return "addrestaurant";
  }

  @PostMapping(
    path = "/addrestaurant",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleRestaurantSubmit(Map<String, Object> model, Restaurants restaurant,  @ModelAttribute("userID") int userID) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();

    
      
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Restaurants (ID serial, OwnerID numeric, Name varchar(50), Description varchar(255), Cuisine varchar(50), Email varchar(50), Phone varchar(255), Address varchar(255), StartTime varchar(50), EndTime varchar(50), SingleTables  numeric, DoubleTables  numeric, FourPersonTables numeric, PartyTables numeric)");
      String sql = "INSERT INTO Restaurants (OwnerID, Name, Cuisine, Description, StartTime, EndTime, Email, Phone, Address, SingleTables, DoubleTables, FourPersonTables, PartyTables) VALUES (" + userID + ",'" + restaurant.getName() 
      + "','"  + restaurant.getCuisine() + "','"  + restaurant.getDescription() + "','"  + restaurant.getStartTime() + "','" + restaurant.getEndTime()  + "','"  + restaurant.getEmail() + "','" + restaurant.getPhone() + "','"   + restaurant.getAddress() + "'," + restaurant.getSingleTables() + "," +  restaurant.getDoubleTables() + "," +  restaurant.getFourPersonTables() + "," +  restaurant.getPartyTables() + ")";
      stmt.executeUpdate(sql);
      model.put("restaurant", restaurant);
      return "redirect:/user=" + userID;
      
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }



  
  @RequestMapping("/addreservation")
  String getReservationForm(Map<String, Object> model) {
    Reservations reservation = new Reservations();
    model.put("reservation", reservation);
    return "addreservation";
  }
 
  @RequestMapping("/addreservation/{pid}")
  public String getReservationFormWithRestID(Map<String, Object> model, @PathVariable String pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
     
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants WHERE id=" + pid);

      Reservations reservation = new Reservations();
      if(rs.next()==true) {
        Integer id = rs.getInt("ID");
        String name = rs.getString("Name");
        model.put("id", id);
        model.put("name", name);

        reservation.setRestaurantID(id);
        reservation.setRestaurant(name);
      } 
      
      model.put("reservation", reservation);
      return "addreservation";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
    
  }

  @PostMapping(
    path = "/addreservation",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleReservationSubmit(Map<String, Object> model, Reservations reservation, @ModelAttribute("userID") int id, @ModelAttribute("userID") String userName) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservations2 (ID serial,UserID numeric,Restaurant varchar(225),FullName varchar(225), Time varchar(225),Phone varchar(255), TableType varchar(1))");
      String sql = "INSERT INTO Reservations2 (UserID, Restaurant, FullName, Time, Phone, TableType) VALUES ('" + id + "','" + reservation.getRestaurant() + "','" + reservation.getFullName() + "','" + reservation.getTime() + "','"  + reservation.getPhone() + "','" + reservation.getTableType()  + "')";
      stmt.executeUpdate(sql);
      // model.put("reservation", reservation);
      return "redirect:/user=" + id;
      
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping("/deleteReservation/{pid}")
  public String deleteReservation(Map<String, Object> model, @PathVariable int pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("DELETE FROM Reservations2 WHERE ID =" + pid);
      
      return "deleteReservation";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping("/editReservation/{pid}")
  public String getEditReservationForm(Map<String, Object> model, @PathVariable String pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
     
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations2 WHERE id=" + pid);

      Reservations reservation = new Reservations();
      if(rs.next()==true) {
        Integer id = rs.getInt("ID");
        String name = rs.getString("Restaurant");
        model.put("id", id);
        model.put("name", name);

        reservation.setID(id);
        reservation.setRestaurant(name);
      } 
      
      model.put("reservation", reservation);
      return "addEditReservation";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
    
  }

  @PostMapping(
    path = "/addEditReservation",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleEditReservationSubmit(Map<String, Object> model, Reservations reservation, @ModelAttribute("userID") int id, @ModelAttribute("userID") String userName) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservations2 (ID serial,UserID numeric,Restaurant varchar(225),FullName varchar(225), Time varchar(225),Phone varchar(255), TableType varchar(1))");
      String sql = "UPDATE Reservations2 SET FullName ='" + reservation.getFullName() + "', Time ='" + reservation.getTime() + "', Phone = '"  + reservation.getPhone() + "', TableType ='" + reservation.getTableType()  + "' WHERE ID = " + reservation.getID();
      stmt.executeUpdate(sql);
      // model.put("reservation", reservation);
      return "redirect:/user=" + id;
      
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @RequestMapping("/addreview")
  String getReviewForm(Map<String, Object> model) {
    Reviews review = new Reviews();
    model.put("review", review);
    return "addreview";
  }
 
  @RequestMapping("/addreview/{pid}")
  public String getReviewFormWithRestID(Map<String, Object> model, @PathVariable String pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
     
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants WHERE id=" + pid);

      Reviews review = new Reviews();
      if(rs.next()==true) {
        Integer id = rs.getInt("ID");
        String name = rs.getString("Name");
        model.put("id", id);
        model.put("name", name);

        review.setRestaurantID(id);
        review.setRestaurant(name);
      } 
      
      model.put("review", review);
      return "addreview";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
    
  }

  @PostMapping(
    path = "/addreview",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleReviewSubmit(Map<String, Object> model, Reviews reviews, @ModelAttribute("userID") int id, @ModelAttribute("userID") String userName) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      
      stmt.executeUpdate("DROP TABLE IF EXISTS Reviews");
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reviews (ID serial, UserID numeric, Restaurant varchar(225), FullName varchar(225), Time varchar(255), Comment text, Rating numeric)");
      String sql = "INSERT INTO Reviews (UserID, Restaurant, FullName, Time, Comment, Rating) VALUES ('" + id + "','" + reviews.getRestaurant() + "','" + reviews.getFullName() + "','" + reviews.getTime() + "','"  + reviews.getComment() + "','" + reviews.getRating()  + "')";
      stmt.executeUpdate(sql);
      // model.put("reservation", reservation);
      return "redirect:/user=" + id;
      
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  // @RequestMapping("/favorites")
  // String getFavoriteForm(Map<String, Object> model) {
  //   Favorites favorite = new Favorites();
  //   model.put("favorite", favorite);
  //   return "addreview";
  // }
 
  @RequestMapping("/favorites")
  public String getFavoriteFormWithRestID(Map<String, Object> model, @ModelAttribute("userID") int pid, @ModelAttribute("userID") String userName) throws Exception {
    try (Connection connection = dataSource.getConnection()) { 
      Statement stmt = connection.createStatement();
      System.out.println(pid);
       ResultSet rs = stmt.executeQuery("SELECT * FROM Favorites WHERE UserID=" + pid);
       ArrayList<Favorites> output = new ArrayList<Favorites>();
       Favorites favorite = new Favorites();
       while(rs.next()==true) {
       Integer id = rs.getInt("ID");
       Integer UserID = rs.getInt("UserID");
       Integer RestaurentID = rs.getInt("RestaurantID");

        favorite.setID(id);
        favorite.setUserID(UserID);
        favorite.setRestaurantID(RestaurentID);
        output.add(favorite);
       } 
      
      model.put("records", output);
      return "favorites";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
    
  }

   @GetMapping("/favorites/{pid}")
  public String unFavorite(Map<String, Object> model, @PathVariable int pid) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("DELETE FROM Favorites WHERE ID =" + pid);
      
      return "favorites";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  // @PostMapping(
  //   path = "/addfavorite",
  //   consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  // )
  // public String handleFavoriteSubmit(Map<String, Object> model, Reviews reviews, @ModelAttribute("userID") int id, @ModelAttribute("userID") String userName) throws Exception {
  //   // Save the person data into the database
  //   try (Connection connection = dataSource.getConnection()) {
  //     Statement stmt = connection.createStatement();
      

  //     stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Favorites (ID serial, UserID numeric, RestaurantID numeric)");
  //     // String sql = "INSERT INTO Reservations2 (UserID, Restaurant, FullName, Time, Phone, TableType) VALUES ('" + id + "','" + reservation.getRestaurant() + "','" + reservation.getFullName() + "','" + reservation.getTime() + "','"  + reservation.getPhone() + "','" + reservation.getTableType()  + "')";
  //     // stmt.executeUpdate(sql);
  //     // // model.put("reservation", reservation);
  //     return "redirect:/user=" + id;
      
  //   } catch (Exception e) {
  //     model.put("message", e.getMessage());
  //     return "error";
  //   }
  // }




  @PostMapping(
    path = "/search",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String getSearch(Map<String, Object> model, Search search, @ModelAttribute("userID") Integer userID) throws Exception {
      try (Connection connection = dataSource.getConnection()) {
       
        return "redirect:/searchResult/" + search.getParam();
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
    }


    @GetMapping("/searchResult/{param}")
public String searchTerminal(Map<String, Object> model, @ModelAttribute("userID") Integer userID, @PathVariable String param) {

  try (Connection connection = dataSource.getConnection()) {
    Statement stmt = connection.createStatement();
    ArrayList<SearchResult> output = new ArrayList<SearchResult>();
    ArrayList<Favorites> output2 = new ArrayList<Favorites>();
    Search search = new Search();
    search.setParam(param);
    ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants WHERE Name ILIKE '%"+ search.getParam() +  "%' OR Description ILIKE '%" + search.getParam() + "%' OR Cuisine ILIKE '%" + search.getParam() + "%' OR Address ILIKE '%" + search.getParam() + "%'");
    while (rs.next()) {
      Integer id = rs.getInt("ID");
      Integer ownerID = rs.getInt("OwnerID");
      String name = rs.getString("Name");
      String cus = rs.getString("Cuisine");
      String desc = rs.getString("Description");
      String email = rs.getString("Email");
      String phone = rs.getString("Phone");
      String addr = rs.getString("Address");
      String st = rs.getString("StartTime");
      String et = rs.getString("EndTime");
      Integer single = rs.getInt("SingleTables");
      Integer duo = rs.getInt("DoubleTables");
      Integer quad = rs.getInt("FourPersonTables");
      Integer party = rs.getInt("PartyTables");
      SearchResult rearchResult = new SearchResult();
      rearchResult.setID(id);
      rearchResult.setOwnerID(ownerID);
      rearchResult.setName(name);
      rearchResult.setCuisine(cus);
      rearchResult.setDescription(desc);
      rearchResult.setEmail(email);
      rearchResult.setPhone(phone);
      rearchResult.setAddress(addr);
      rearchResult.setStartTime(st);
      rearchResult.setEndTime(et);
      rearchResult.setSingleTables(single);
      rearchResult.setDoubleTables(duo);
      rearchResult.setFourPersonTables(quad);
      rearchResult.setPartyTables(party);
      output.add(rearchResult);
    }

    ResultSet rs2 = stmt.executeQuery("SELECT * FROM Favorites WHERE userID = " + userID);

    while (rs2.next()) {
      Integer id = rs2.getInt("ID");
      Integer uid = rs2.getInt("userID");
      Integer rid = rs2.getInt("restaurantID");
      Favorites favorite = new Favorites();
      favorite.setID(id);
      favorite.setUserID(uid);
      favorite.setRestaurantID(rid);
      output2.add(favorite);
    }

    for (Integer i = 0; i < output.size(); i++) {
      output.get(i).setFavorite(0);
      for (Integer j = 0; j < output2.size(); j++) {
        if (output2.get(j).getRestaurantID() == output.get(i).getID()) {
          j = output2.size();
          output.get(i).setFavorite(1);
        }
      }
    }
   
    model.put("records", output);
    model.put("id", userID);
    
    model.put("search", search);
    return "searchResult";
  } catch (Exception e) {
    model.put("message", e.getMessage());
    return "error";
  }
}

@GetMapping("/searchResult")
public String searchTerminalNull(Map<String, Object> model, @ModelAttribute("userID") Integer userID) {

  try (Connection connection = dataSource.getConnection()) {
    Statement stmt = connection.createStatement();
    ArrayList<SearchResult> output = new ArrayList<SearchResult>();
    ArrayList<Favorites> output2 = new ArrayList<Favorites>();
    Search search = new Search();
    search.setParam("");
    ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants");
    while (rs.next()) {
      Integer id = rs.getInt("ID");
      Integer ownerID = rs.getInt("OwnerID");
      String name = rs.getString("Name");
      String cus = rs.getString("Cuisine");
      String desc = rs.getString("Description");
      String email = rs.getString("Email");
      String phone = rs.getString("Phone");
      String addr = rs.getString("Address");
      String st = rs.getString("StartTime");
      String et = rs.getString("EndTime");
      Integer single = rs.getInt("SingleTables");
      Integer duo = rs.getInt("DoubleTables");
      Integer quad = rs.getInt("FourPersonTables");
      Integer party = rs.getInt("PartyTables");
      SearchResult rearchResult = new SearchResult();
      rearchResult.setID(id);
      rearchResult.setOwnerID(ownerID);
      rearchResult.setName(name);
      rearchResult.setCuisine(cus);
      rearchResult.setDescription(desc);
      rearchResult.setEmail(email);
      rearchResult.setPhone(phone);
      rearchResult.setAddress(addr);
      rearchResult.setStartTime(st);
      rearchResult.setEndTime(et);
      rearchResult.setSingleTables(single);
      rearchResult.setDoubleTables(duo);
      rearchResult.setFourPersonTables(quad);
      rearchResult.setPartyTables(party);
      output.add(rearchResult);
    }

    ResultSet rs2 = stmt.executeQuery("SELECT * FROM Favorites WHERE userID = " + userID);

    while (rs2.next()) {
      Integer id = rs2.getInt("ID");
      Integer uid = rs2.getInt("userID");
      Integer rid = rs2.getInt("restaurantID");
      Favorites favorite = new Favorites();
      favorite.setID(id);
      favorite.setUserID(uid);
      favorite.setRestaurantID(rid);
      output2.add(favorite);
    }

    for (Integer i = 0; i < output.size(); i++) {
      output.get(i).setFavorite(0);
      for (Integer j = 0; j < output2.size(); j++) {
        if (output2.get(j).getRestaurantID() == output.get(i).getID()) {
          j = output2.size();
          output.get(i).setFavorite(1);
        }
      }
    }
   
    model.put("records", output);
    model.put("id", userID);
    
    model.put("search", search);
    return "searchResult";
  } catch (Exception e) {
    model.put("message", e.getMessage());
    return "error";
  }
}

    @PostMapping(
      path = "/searchFavorite/{pid}",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
  
    public String test(Map<String, Object> model, @PathVariable String pid, Search search, @ModelAttribute("userID") Integer userID) throws Exception {
       try (Connection connection = dataSource.getConnection()) { 
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Favorites (ID serial, UserID numeric, RestaurantID numeric)");
        ResultSet rs = stmt.executeQuery("SELECT * FROM Favorites WHERE userID=" + userID);
        boolean isDelete = false; 
        String sql = "INSERT INTO Favorites (UserID, RestaurantID) VALUES (" + userID + "," + pid + ")";
  
        while(rs.next()) { //check if we are deleting or inserting 
          Integer rid = rs.getInt("restaurantID");
          if (rid == Integer.parseInt(pid)) {
            isDelete = true; 
            System.out.println("ENTERED");
            sql = "DELETE FROM Favorites WHERE UserID =" + userID + " AND RestaurantID = " + pid;  
            break;
          }
        }
        stmt.executeUpdate(sql);

        
        return "redirect:/searchResult/" + search.getParam();
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
    }


    @GetMapping("/restaurants")
public String restTerminal(Map<String, Object> model, @ModelAttribute("userID") Integer userID) {

  try (Connection connection = dataSource.getConnection()) {
    Statement stmt = connection.createStatement();
    ArrayList<Restaurants> output = new ArrayList<Restaurants>();
   
    ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants WHERE OwnerID = " + userID + "");
    while (rs.next()) {
      Integer id = rs.getInt("ID");
      Integer ownerID = rs.getInt("OwnerID");
      String name = rs.getString("Name");
      String cus = rs.getString("Cuisine");
      String desc = rs.getString("Description");
      String email = rs.getString("Email");
      String phone = rs.getString("Phone");
      String addr = rs.getString("Address");
      String st = rs.getString("StartTime");
      String et = rs.getString("EndTime");
      Integer single = rs.getInt("SingleTables");
      Integer duo = rs.getInt("DoubleTables");
      Integer quad = rs.getInt("FourPersonTables");
      Integer party = rs.getInt("PartyTables");
      Restaurants restaurant = new Restaurants();
      restaurant.setID(id);
      restaurant.setOwnerID(ownerID);
      restaurant.setName(name);
      restaurant.setCuisine(cus);
      restaurant.setDescription(desc);
      restaurant.setEmail(email);
      restaurant.setPhone(phone);
      restaurant.setAddress(addr);
      restaurant.setStartTime(st);
      restaurant.setEndTime(et);
      restaurant.setSingleTables(single);
      restaurant.setDoubleTables(duo);
      restaurant.setFourPersonTables(quad);
      restaurant.setPartyTables(party);
      output.add(restaurant);
    }

    
    model.put("records", output);
    model.put("id", userID);
    
    return "restaurants";
  } catch (Exception e) {
    model.put("message", e.getMessage());
    return "error";
  }
}

@RequestMapping("/editrestaurant/{rid}")
  String getEditRestaurantForm(Map<String, Object> model, @PathVariable int rid) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
    Restaurants restaurant = new Restaurants();
    ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants WHERE ID = " + rid + "");
    while (rs.next()) {
      Integer id = rs.getInt("ID");
      Integer ownerID = rs.getInt("OwnerID");
      String name = rs.getString("Name");
      String cus = rs.getString("Cuisine");
      String desc = rs.getString("Description");
      String email = rs.getString("Email");
      String phone = rs.getString("Phone");
      String addr = rs.getString("Address");
      String st = rs.getString("StartTime");
      String et = rs.getString("EndTime");
      Integer single = rs.getInt("SingleTables");
      Integer duo = rs.getInt("DoubleTables");
      Integer quad = rs.getInt("FourPersonTables");
      Integer party = rs.getInt("PartyTables");
      
      restaurant.setID(id);
      restaurant.setOwnerID(ownerID);
      restaurant.setName(name);
      restaurant.setCuisine(cus);
      restaurant.setDescription(desc);
      restaurant.setEmail(email);
      restaurant.setPhone(phone);
      restaurant.setAddress(addr);
      restaurant.setStartTime(st);
      restaurant.setEndTime(et);
      restaurant.setSingleTables(single);
      restaurant.setDoubleTables(duo);
      restaurant.setFourPersonTables(quad);
      restaurant.setPartyTables(party);
      
    }

    model.put("restaurant", restaurant);
    return "editrestaurant";
  } catch (Exception e) {
    model.put("message", e.getMessage());
    return "error";
  }
  }

  @PostMapping(
    path = "/editrestaurant",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleEditRestaurantSubmit(Map<String, Object> model, Restaurants restaurant,  @ModelAttribute("userID") int userID) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();

    
      
      System.out.println(restaurant.getID());
      String sql = "UPDATE Restaurants SET Name = '" + restaurant.getName() + "', Cuisine = '" + restaurant.getCuisine() + "', Description = '" + restaurant.getDescription() + "', StartTime= '" + restaurant.getStartTime() + "', EndTime= '" + restaurant.getEndTime() + "', Email = '" + restaurant.getEmail() + "', Phone= '" + restaurant.getPhone() + "', Address= '" + restaurant.getAddress() + "', SingleTables= " + restaurant.getSingleTables() + ", DoubleTables= " + restaurant.getDoubleTables() + ", FourPersonTables= " + restaurant.getFourPersonTables() + ", PartyTables= " + restaurant.getPartyTables() + " WHERE ID = " +  restaurant.getID() + "";
      stmt.executeUpdate(sql);
      model.put("restaurant", restaurant);
      return "redirect:/restaurants";
      
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }

    

  
  

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws URISyntaxException {
    // if (dbUrl == null || dbUrl.isEmpty()) {
    //   return new HikariDataSource();
    // } else {
      HikariConfig config = new HikariConfig();
      URI dbUri = new URI(System.getenv("DATABASE_URL"));

      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
      config.setJdbcUrl(dbUrl);
      config.setUsername(username);
      config.setPassword(password);
      return new HikariDataSource(config);
    // }
  }

}
