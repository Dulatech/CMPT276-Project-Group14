# Group 14 - Project Proposal
**CMPT 276 - D100**

David Delatycki - 301445530

Tommy Truong - 301304389

Ricky Xian -  301290569

Philip Zhang - 301348636

Jaspreet Singh - 301435873

## Project Overview

## Name: CraveIt

## How is the problem currently solved?
The goal of our web application is to provide a centralized platform where one can rate, search,  reserve and save restaurants. The app is aimed at everyone but most likely will be utilized by youth. Many people currently use Yelp or Instagram. However, both sites require an account for additional services. Yelp requires an account and downloading the app in order to book a reservation. Instagram requires an account to see additional posts or contact the restaurant. CraveIt will exist as an all in one web application where creating an account is optional for those who would like to leave reviews or save future or past visits.
Some other alternatives are Urbanspoon, OpenTable and Apple Maps. Urbanspoon is used by millions to find nearby restaurants with ratings and reviews by consumers and food critics. Features available are browsing menus and photos of items and booking a reservation using OpenTable’s system. Open table holds a large selection of restaurants where parking/pricing information is available and user reviews are shown as well. Apple Maps exist directly on every user's iPhones and will show nearby restaurants with ratings directly from google search. There is also a built-in GPS feature where one can see the directions from their current location.
At the moment, a complete collection of all these features and services do not exist in a centralized location or platform. Our mission is to create one that is simple, modern, and easy to use for all. This will make it easy for all to read and leave reviews at local restaurants, book and cancel reservations, and create an account to bookmark potential visits.
 
 
## How will this project make life better?
Our app will provide a seamless experience from the beginning of their reservation to its end with as few interactions as possible and  real-time reviews and recommendations from other customers, and an option to make their preferences known. Using our app with a user-friendly interface that can be used with ease on any device and that is simple to navigate will make life easier for both customers and staff. It provides all the nearby restaurant options to the users and online reservation will enable customers to reserve tables with ease and efficiency with the confirmation via email or SMS about their booking confirmation or cancellation. 

## Target audience
The application is designed for everyone, but is targeted toward youth adults.

## API 
Google Geo-Location

## Similar Apps/inspiration
Yelp, Urbanspoon

## Sample Stories
### Making a Reservation
The user will be able to view restaurants in their proximity and “reserve a table” at the restaurant. The user can then “delete the reservation” or “update the reservation”.

In order to “make a reservation” the user must first select the restaurant on the map. They will be prompted to enter information about the reservation like the time and number of people. Once the reservation has been made an email/text confirmation will be sent to the user.

The user will be able  to view all of the reservations on their profile. The reservations will be sorted by date. Each reservation can be selected for viewing, updating and deleting.
If the user selects the reservation, they can then view specific information about it, like the time, location, and number of people.

The user can decide to update the reservation in regards to the time and number of people. A confirmation will be sent to users email/text to notify them of the change.

Lastly the user will be able to delete a reservation that they have booked. Once again a confirmation will be sent to the user notifying them of the successful deletion.
 
### Writing a Review
The user will be able to create reviews of each of the restaurants. The review will also allow for editing and deleting

After selecting a restaurant from the map, a user will be presented with a list of reviews from other users. Any user will be able to add their own review by clicking “add review” on the restaurant page.

The user can write their own review of the restaurant and share them with other users. The user will be prompted to give a rating out of 5 and write a short description.

Each user can view all of the reviews they have written on their profile page. From there they can update or delete the review if they change their mind.

The user can decide to update the review of each restaurant and change the rating and description. A tag will be added to the review stating that it has been updated.

Lastly the user will be able to delete a review that they have made.
 
### Filtering restaurants 
The user can filter out restaurants by selecting categories. Some of these categories may be “cafe,” “fast food,” “bar/pub,” “breakfast only,” “mexican,” “asian,” “sushi,” etc. By filtering to a certain category, the user will only be able to see restaurants under that category. A restaurant can also fall under many categories

Users will be able to select favorites for their own personal category and will be found under their account. Filtering by favorites will only show restaurants that they selected as favorites. They can also unfavorite a favorite restaurant.

Additionally, users can block restaurants to never show them. To view again they must unblock that restaurant.

This will all be done through a series of array lists.
 
 
### Discover restaurants

Have a page showing users recommended restaurants based on how frequent they visit similar restaurants and previous reviews. The app will track the number of visits of each restaurant and, together with the user’s reviews, will generate a list displaying how likely the user will enjoy another restaurant. The list, if possible, should be ordered and concise. It should not be an ordered list of every possible restaurant but just a top 15.

This page (or on a new page) should also display new restaurants around the area. 

The user can choose a recommended or new restaurant that redirects them to a small info page showing the restaurant’s details and options (such as making a reservation, writing a review, etc.). They will also be given a remind option. On a new page, users can view all restaurants they clicked as remind and can mark as reminded to remove from the page. This is so users can keep track of restaurants they see on the discover page and are interested in.
 
### Route destination
Once the user books a reservation, the app will map the nearest route to the restaurant. The user can choose their commute that shows different routes. Each route will return a travel time for user convenience. 

Users will also be able to view routes when viewing a restaurant (no reservations booked).

## Group Meetings
Every Monday 7pm via Discord.

## Project Links
Git Repo: https://github.com/Dulatech/CMPT276-Project-Group14
Hosted App: https://cmpt276-project-group14.herokuapp.com/

## Epics
CraveIt is a mobile application that seeks to enhance the user’s dining experience by providing an alternative way to make reservations via the web. Users will be able to make add reservations, cancel reservations, update reservations, give reviews, delete reviews, and update reviews. Users of the app will be greeted with a notification indicating if their request was successful or not after submitting a request. In addition, the app will also show nearby restaurants and provide information on those restaurants. The creation of a user account is required to give reviews and book reservations.
The features in paragraph one are features that we will be prioritizing for the app. The following features are additional features we are interested in implementing, but might not do so due to difficulty or time constraint. Additional features we are interested in implementing include: the option to sort restaurants, option to favourite a restaurant, and a friend system.
