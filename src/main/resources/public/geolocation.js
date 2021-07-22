var id, target, options, latitude, longitude, map, geocoder, restaurants, restaurantIndex = 0, userMarker, userLocation;

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
    geoSetup();
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
    for (i = 0; i < restaurants.length; i++) {
        geocodeAddress(restaurants[i].address, 0);
    }

    // user location marker
    //need to convert to string so we can call function 
    userLocation = latitude + ", " + longitude + "";
    geocodeAddress(userLocation, 1);
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
    if (option == 0) {
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
            console.log(restaurants[i].id);
            
        });
    } else {
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

//geocoder messes up with indexes so global var that represents index is needed 
function geocodeAddress(address, option) {
    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status == 'OK') {
            //if valid add the marker 
            if (option == 0) { //restaurant 
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location,
                    icon: "https://img.icons8.com/color/48/000000/restaurant-.png",
                    Title: "" + restaurantIndex //convert to string 
                });
                restaurantIndex++;
                markerFunctionality(marker, 0); // add functionality for markers 
            } else { //user markers 
                if (userMarker == null) { //null marker 
                    userLocation = results[0].geometry.location;
                    converUserLocationToReadableAddressSetup();
                    userMarker = new google.maps.Marker({
                        position: userLocation,
                        map: map,
                        icon: "https://img.icons8.com/ios-filled/50/000000/men-age-group-4.png"
                    });
                    markerFunctionality(userMarker, 1);
                } else { //use same marker and change marker position
                    userLocation = results[0].geometry.location;
                    converUserLocationToReadableAddressSetup();
                   // userMarkerInfoHelper();
                    userMarker.setPosition(userLocation);
                    markerFunctionality(userMarker, 1);
                }
            }
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
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
