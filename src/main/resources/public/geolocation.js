var id, target, options, latitude, longitude;

function geoTest() {

    if (!navigator.geolocation) {
        console.log("not supported");
    } else {
        console.log("supported");
        initMap();
        id = navigator.geolocation.watchPosition(success, error);
    }

    function success(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;

        if (target.latitude === latitude && target.longitude === longitude) {
            console.log("Reached loc");
        }
        
        console.log(latitude);
        console.log(longitude);
    }

    function error() {
        console.log("fail to retrieve location");
    }

}

target = {
    latitude: 52.520007,
    longitude: 13.404954
};

option = {
    enableHighAccuracy: false,
    timeout: 4000,
    maximumAge: 0
};


function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
      center: { lat: 100, lng: 100 },
      zoom: 8,
    });
  }