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

@Controller
@SpringBootApplication
public class Main {

  // @Value("${spring.datasource.url}")
  // private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index(Map<String, Object> model) {
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
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID serial, UserName varchar(50), Password varchar(50), FullName varchar(50), Email varchar(50), Phone varchar(11), Address varchar(255), UserType varchar(1))");
      String sql = "INSERT INTO Users (UserName, Password, FullName, Email, Phone, Address, UserType) VALUES ('" + user.getUserName() + "','" + user.getPassword() 
      + "','" + user.getFullName() + "','"  + user.getEmail() + "','" + user.getPhone() + "','"   + user.getAddress() + "','" + user.getUserType()  + "')";
      stmt.executeUpdate(sql);
      return "redirect:/";
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
      String sql = "SELECT ID FROM Users WHERE UserName = '" + user.getUserName() + "' AND PASSWORD = '" + user.getPassword() + "'";
      ResultSet rs = stmt.executeQuery(sql);
      System.out.println(rs);
      ArrayList<Users> d = new ArrayList<Users>();
      Users output = new Users();
      if(rs.next()==true){
      output.setID(rs.getInt("ID"));
      d.add(output);
      return "redirect:/success";
      }
      else
     return "redirect:/loginError";
    } catch (Exception e) {
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
    //aaaa
  }
}
