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
        String email = rs2.getString("Email");
        String phone = rs2.getString("Phone");
        String addr = rs2.getString("Address");
        Integer single = rs2.getInt("SingleTables");
        Integer duo = rs2.getInt("DoubleTables");
        Integer quad = rs2.getInt("FourPersonTables");
        Integer party = rs2.getInt("PartyTables");
        Restaurants restaurant = new Restaurants();
        restaurant.setID(id);
        restaurant.setOwnerID(ownerID);
        restaurant.setName(name);
        restaurant.setCuisine(cus);
        restaurant.setEmail(email);
        restaurant.setPhone(phone);
        restaurant.setAddress(addr);
        restaurant.setSingleTables(single);
        restaurant.setDoubleTables(duo);
        restaurant.setFourPersonTables(quad);
        restaurant.setPartyTables(party);
        output2.add(restaurant);
      }

      model.put("records", output);
      model.put("records2", output2);
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
      return "userHome";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  //userInfo
  @RequestMapping("/userInfo") 
  public String cuserSetting(Map<String, Object> model, @ModelAttribute("userID") String id, @ModelAttribute("userID") String userName) {
     
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
  public String setupUpdateInfo(Map<String, Object> model, @PathVariable String selector) {
    Users user = new Users();
    System.out.println("hi" + selector);
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
    public String getMap(Map<String, Object> model) {
      try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        ArrayList<Restaurants> output = new ArrayList<Restaurants>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Restaurants");
        while (rs.next()) {
          Integer id = rs.getInt("ID");
          Integer ownerID = rs.getInt("OwnerID");
          String name = rs.getString("Name");
          String cus = rs.getString("Cuisine");
          String email = rs.getString("Email");
          String phone = rs.getString("Phone");
          String addr = rs.getString("Address");
          Integer single = rs.getInt("SingleTables");
          Integer duo = rs.getInt("DoubleTables");
          Integer quad = rs.getInt("FourPersonTables");
          Integer party = rs.getInt("PartyTables");
          Restaurants restaurant = new Restaurants();
          restaurant.setID(id);
          restaurant.setOwnerID(ownerID);
          restaurant.setName(name);
          restaurant.setCuisine(cus);
          restaurant.setEmail(email);
          restaurant.setPhone(phone);
          restaurant.setAddress(addr);
          restaurant.setSingleTables(single);
          restaurant.setDoubleTables(duo);
          restaurant.setFourPersonTables(quad);
          restaurant.setPartyTables(party);
          output.add(restaurant);
        }
        model.put("restaurants", output);
        return "map";
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

    
      
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Restaurants (ID serial, OwnerID numeric, Name varchar(50), Cuisine varchar(50), Email varchar(50), Phone varchar(255), Address varchar(255), SingleTables  numeric, DoubleTables  numeric, FourPersonTables numeric, PartyTables numeric)");
      String sql = "INSERT INTO Restaurants (OwnerID, Name, Cuisine, Email, Phone, Address, SingleTables, DoubleTables, FourPersonTables, PartyTables) VALUES (" + userID + ",'" + restaurant.getName() 
      + "','" + restaurant.getCuisine() + "','"  + restaurant.getEmail() + "','" + restaurant.getPhone() + "','"   + restaurant.getAddress() + "'," + restaurant.getSingleTables() + "," +  restaurant.getDoubleTables() + "," +  restaurant.getFourPersonTables() + "," +  restaurant.getPartyTables() + ")";
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

  @PostMapping(
    path = "/addreservation",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleReservationSubmit(Map<String, Object> model, Reservations reservation, @ModelAttribute("userID") int id, @ModelAttribute("userID") String userName) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservations1 (ID serial,UserID numeric,RestaurantID numeric,FullName varchar(225), Time varchar(225),Phone varchar(255), TableType varchar(1))");
       String sql = "INSERT INTO Reservations1 (UserID, RestaurantID, FullName, Time, Phone, TableType) VALUES ('" + id + "','" + 1 + "','" + reservation.getFullName() + "','" + reservation.getTime() + "','"  + reservation.getPhone() + "','" + reservation.getTableType()  + "')";
       stmt.executeUpdate(sql);
      // model.put("reservation", reservation);
      return "index";
      
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
