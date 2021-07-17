var id, target, options, latitude, longitude, map;

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

document.addEventListener("DOMContentLoaded", function () {
    if (!navigator.geolocation) {
        console.log("not supported");
    } else {
        console.log("supported");
        // id = navigator.geolocation.watchPosition(success, error, option); //update position, will probably use this in final version
        id = navigator.geolocation.getCurrentPosition(success, error);
    }

    //success for getting position in geolocation 
    function success(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        var accuracy = position.coords.accuracy;
        initMap(); //if successful create the map 

        /** 
        if (target.latitude === latitude && target.longitude === longitude) {
            console.log("Reached loc");
        }
        */
        console.log("lat " + latitude);
        console.log("long " + longitude);
        console.log("acc " + accuracy);
    }

    //error in getting position for geolocation 
    function error() {
        console.log("fail to retrieve location");
    }

    //function for creating the map
    function initMap() {
        // get user location
        const userLocation = { lat: latitude, lng: longitude };

        // center map on user 
        map = new google.maps.Map(document.getElementById("map"), {
            zoom: 15, //zoom level for street 
            center: userLocation,
        });

        //hide extra markers from google map api
        map.setOptions({ styles: mapStyle["hide"] })

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

        // user location marker
        const marker = new google.maps.Marker({
            position: userLocation,
            map: map,
            icon: "https://img.icons8.com/ios-filled/50/000000/men-age-group-4.png"
        });
    }



});


target = {
    latitude: 52.520007,
    longitude: 13.404954
};

// this is needed, dont delete
function initMap() {

}




