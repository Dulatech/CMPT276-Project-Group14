var id, target, options, latitude, longitude, map, geocoder, restaurants, restaurantIndex = 0, userMarker, userLocation, userID;
let markers = [];
var userFavorites;

var test1 = 0; 
let rgeo; 

const option = {
    enableHighAccuracy: true,
    timeout: 60 * 1000, //refresh every 60s 
    maximumAge: 0
};

//style for map
const mapStyle = {
    hide: [
        {
            featureType: "poi",
            stylers: [{ visibility: "off" }],
        },
        {
            featureType: "transit",
            elementType: "labels.icon",
            stylers: [{ visibility: "off" }],
        },
    ]
};

//store restaurants to global variable 
function storeRestaurants(r) {
    restaurants = r.slice();
    console.log(restaurants);
    geoSetup();
}

//store userID
function storeUserID(i) {
    userID = i;
}

//store user's favorite
function storeUserFavorites(userFavoriteAL) {
    userFavorites = userFavoriteAL.slice();
}

//check if geolocation is supported. If supported, create map 
function geoSetup() {
    //check if geolocation is supported
    if (!navigator.geolocation) {
        alert("Your device does not support geolocation! This page will not work properly!");
    } else {
        // id = navigator.geolocation.watchPosition(success, error, option); //update position, will probably use this in final version
        id = navigator.geolocation.getCurrentPosition(success, error);
    }

    //success for getting position in geolocation 
    function success(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        createMap(); //if successful create the map 
    }

    //error in getting position for geolocation 
    function error() {
        console.log("fail to retrieve location");
    }
}

//function for creating the map
function createMap() {
    // get user location
    userLocation = { lat: latitude, lng: longitude };

    // center map on user 
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 15, //zoom level for street 
        center: userLocation,
    });

    //hide extra markers from google map api
    map.setOptions({ styles: mapStyle["hide"] })

    // geocoder
    geocoder = new google.maps.Geocoder();

    // populate restaurants if geocode address is valid 
    populateRestaurants();

    // user location marker
    //need to convert to string so we can call function 
    userLocation = latitude + ", " + longitude + "";
    geocodeAddress(userLocation, 1);
}

//function to populate nearby restaurants, n^2 
function populateRestaurants() {
    for (i = 0; i < restaurants.length; i++) {
        var isFavorite = false;
        
       


        console.log("address " + restaurants[i].address);
        geocodeAddress(restaurants[i].address, 0);
        
       // test(rgeo);
        /** 
        for (j = 0; j < userFavorites.length; j++) {
            if (userFavorites[j].restaurantID == restaurants[i].id) {
                j = userFavorites.length;
                isFavorite = true;
            }
        }

        if (isFavorite) {
            geocodeAddress(restaurants[i].address, 2);
        } else {
            geocodeAddress(restaurants[i].address, 0);
        }
        */

    }
}
//convert to readable address 
function convertUserLocationToReadableAddress() {
    geocoder.geocode({ location: userLocation })
        .then((response) => {
            if (response.results[0]) {
                userLocation = response.results[0].formatted_address;
            } else {
                console.log("location unknown");
            }
        })
        .catch((e) => window.alert("Geocoder failed due to: " + e));
}

//this function fixes a weird bug and is used to initial info display
//(userLocation has length of 0 unless we call this function)
//only happens for the condition in creating user marker 
function converUserLocationToReadableAddressSetup() {
    geocoder.geocode({ location: userLocation })
        .then((response) => {
            if (response.results[0]) {
                userLocation = response.results[0].formatted_address;
                var locationString = "";
                for (i = 0; i < userLocation.length; i++) {
                    locationString += userLocation[i];
                }
                document.getElementById("name").innerHTML = "Your position is " + locationString;
                userMarkerInfoHelper();
            } else {
                console.log("location unknown");
            }
        })
        .catch((e) => window.alert("Geocoder failed due to: " + e));
}

//add functionality to marker 
function markerFunctionality(marker, option) {
    if (option == 0) { //default restaurant 
        marker.addListener("click", () => {
            var i = parseInt(marker.getTitle());

            console.log(" THIS IS I" + i);
            document.getElementById("name").innerHTML = restaurants[i].name;
            document.getElementById("description").innerHTML = restaurants[i].description;
            document.getElementById("cuisine").innerHTML = "Cuisine: " + restaurants[i].cuisine;
            document.getElementById("phone").innerHTML = "Phone: " + restaurants[i].phone;
            document.getElementById("address").innerHTML = "Address " + restaurants[i].address;
            document.getElementById("link").innerHTML = "make a reservation";
            document.getElementById("link").href = "/addreservation/" + restaurants[i].id;
            document.getElementById("start").innerHTML = "Open from: " + restaurants[i].startTime;
            document.getElementById("end").innerHTML = "Close at: " + restaurants[i].endTime;


            document.getElementById("uid").value = userID;
            document.getElementById("rid").value = restaurants[i].id;
            document.getElementById("fsubmit").value = "favorite";

        });
    } else if (option == 2) { //favorite 
        marker.addListener("click", () => {
            var i = parseInt(marker.getTitle());
            document.getElementById("name").innerHTML = restaurants[i].name;
            document.getElementById("description").innerHTML = restaurants[i].description;
            document.getElementById("cuisine").innerHTML = "Cuisine: " + restaurants[i].cuisine;
            document.getElementById("phone").innerHTML = "Phone: " + restaurants[i].phone;
            document.getElementById("address").innerHTML = "Address " + restaurants[i].address;
            document.getElementById("link").innerHTML = "make a reservation";
            document.getElementById("link").href = "/addreservation/" + restaurants[i].id;
            document.getElementById("start").innerHTML = "Open from: " + restaurants[i].startTime;
            document.getElementById("end").innerHTML = "Close at: " + restaurants[i].endTime;



            document.getElementById("uid").value = userID;
            document.getElementById("rid").value = restaurants[i].id;
            document.getElementById("fsubmit").value = "unfavorite";
        });
    } else { //user 
        marker.addListener("click", () => {
            var locationString = "";
            for (i = 0; i < userLocation.length; i++) {
                locationString += userLocation[i];
            }
            document.getElementById("name").innerHTML = "Your position is " + locationString;
            userMarkerInfoHelper();
        });
    }
}

function userMarkerInfoHelper() {
    document.getElementById("cuisine").innerHTML = "";
    document.getElementById("phone").innerHTML = "";
    document.getElementById("address").innerHTML = "";
    document.getElementById("link").innerHTML = "";
    document.getElementById("link").href = "";
    document.getElementById("description").innerHTML = "";
    document.getElementById("start").innerHTML = "";
    document.getElementById("end").innerHTML = "";
}

function test(results) {
    //console.log("THIS IS I " + restaurantIndex);
    console.log(rgeo);
        /** 
        var marker = new google.maps.Marker({
            map: map,
            position: results[0].geometry.location,
            icon: "https://img.icons8.com/color/48/000000/restaurant-.png",
            Title: "" + restaurantIndex //convert to string 
        });

        console.log("INPUTTING I " + restaurantIndex);
        console.log();
        restaurantIndex++;

        markerFunctionality(marker, 0); // add functionality for markers 
        markers.push(marker);
        */
    

}


//geocoder messes up with indexes so global var that represents index is needed 
function geocodeAddress(address, option) {

    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status == 'OK') {
            //if valid add the marker 
            test1++;
            console.log(test1);
           // console.log("I OUTSIDE " + restaurantIndex);
            //  console.log("");

            

            if (option == 0) { //restaurant
                //test(results);
                console.log("lat is " + results[0].geometry.location);
                rgeo = results[0].geometry.location.lat();
                /** 
                var latDiff = Math.abs(Math.abs(latitude) - Math.abs(results[0].geometry.location.lat()));
                var lngDiff = Math.abs(Math.abs(longitude) - Math.abs(results[0].geometry.location.lng()));
                if (latDiff <= 0.1 && lngDiff <= 0.1) {
                    var marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        icon: "https://img.icons8.com/color/48/000000/restaurant-.png",
                        Title: "" + restaurantIndex //convert to string 
                    });

                  //  console.log("INPUTTING I " + restaurantIndex);
                 //   console.log();
                 //   restaurantIndex++;
                   
                    markerFunctionality(marker, 0); // add functionality for markers 
                    markers.push(marker);
              
                }
                */







            } else if (option == 2) { //favorite
                /** 
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location,
                    icon: "https://img.icons8.com/nolan/64/star.png",
                    Title: "" + restaurantIndex //convert to string 
                });
                restaurantIndex++;
                markerFunctionality(marker, 2); // add functionality for markers 
                markers.push(marker);
                */
            } else { //user markers 
                if (userMarker == null) { //null marker 
                    //update user coords 
                    userLocation = results[0].geometry.location;
                    latitude = results[0].geometry.location.lat();
                    longitude = results[0].geometry.location.lng();
                    converUserLocationToReadableAddressSetup();
                    userMarker = new google.maps.Marker({
                        position: userLocation,
                        map: map,
                        icon: "https://img.icons8.com/ios-filled/50/000000/men-age-group-4.png"
                    });
                    markerFunctionality(userMarker, 1);
                } else { //use same marker and change marker position
                    userLocation = results[0].geometry.location;
                    latitude = results[0].geometry.location.lat();
                    longitude = results[0].geometry.location.lng();
                    converUserLocationToReadableAddressSetup();
                    userMarker.setPosition(userLocation);
                    markerFunctionality(userMarker, 1);
                    deleteMarkers(); // have to update nearby restaurants, delete all markers then repopulate based on user location
                    populateRestaurants();
                }
            }
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

//delete restaurant markers 
function deleteMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// user manual address input
$(document).ready(function () {
    $("#submit").click(function () {
        var newPosition = $("#inputAddress").val();
        geocodeAddress(newPosition, 1);
    });
});

target = {
    latitude: 52.520007,
    longitude: 13.404954
};

// this is needed, dont delete
function initMap() {

}

//tracking code
/**
        if (target.latitude === latitude && target.longitude === longitude) {
            console.log("Reached loc");
        }
*/




// code for searching nearby restaurnts 
/**  // search nearby restaurant code
//search nearby restaurants
var request = {
    location: userLocation,
    radius: 5000,
    types: ['restaurant']
}

var service = new google.maps.places.PlacesService(map);
service.nearbySearch(request, callback);

//add markers to map
function callback(results, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            addRestaurantMarker(results[i]);
        }
    }
}

//sub function for adding markers
function addRestaurantMarker(place) {
    // var placeLoc = place.geometry.location;
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        icon: "https://img.icons8.com/color/48/000000/restaurant-.png"
    })

}
*/
