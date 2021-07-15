var id, target, options;

function geoTest() {

    if (!navigator.geolocation) {
        console.log("not supported");
    } else {
        console.log("supported");
        id = navigator.geolocation.watchPosition(success, error);
    }

    function success(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

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
      center: { lat: -34.397, lng: 150.644 },
      zoom: 8,
    });
  }