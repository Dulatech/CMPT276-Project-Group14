


var id, target, options, latitude, longitude, map, geocoder, restaurants, restaurantIndex = 0;


// not working atm 
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

/** 
        if (target.latitude === latitude && target.longitude === longitude) {
            console.log("Reached loc");
        }
        */

function storeRestaurants(r) {
    // var test = document.getElementById("r").innerHTML;
    restaurants = r.slice();
    geoSetup();
}

function geoSetup() {
    //check if geolocation is supported
    if (!navigator.geolocation) {
        alert("Your device does not support geolocation!");
    } else {
        // id = navigator.geolocation.watchPosition(success, error, option); //update position, will probably use this in final version
        id = navigator.geolocation.getCurrentPosition(success, error);
    }

    //success for getting position in geolocation 
    function success(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        var accuracy = position.coords.accuracy;
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
    const userLocation = { lat: latitude, lng: longitude };

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
        geocodeAddress(restaurants[i].address);
        
    }
    
    // user location marker
    new google.maps.Marker({
        position: userLocation,
        map: map,
        icon: "https://img.icons8.com/ios-filled/50/000000/men-age-group-4.png"
    });

}



function markerFunctionality(marker) {
   // restaurantIndex = restaurantIndex - 1;
    marker.addListener("click", () => {
        var i = parseInt(marker.getTitle());
        document.getElementById("name").innerHTML = restaurants[i].name;
        document.getElementById("cuisine").innerHTML = restaurants[i].cuisine;
        document.getElementById("phone").innerHTML = restaurants[i].phone;
        document.getElementById("address").innerHTML = restaurants[i].address;
        document.getElementById("link").innerHTML = "make a reservation";
        document.getElementById("link").href = "/addreservation";
    });
    
   
}

//geocoder messes up with indexes so global var that represents index is needed 
function geocodeAddress(address) {
    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status == 'OK') {
            //if valid add the marker 
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location,
                icon: "https://img.icons8.com/color/48/000000/restaurant-.png",
                Title: "" + restaurantIndex //convert to string 
            });
            restaurantIndex++;
            markerFunctionality(marker); // add functionality for markers 
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}


target = {
    latitude: 52.520007,
    longitude: 13.404954
};

// this is needed, dont delete
function initMap() {

}



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
