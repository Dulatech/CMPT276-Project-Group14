document.getElementById("submit-button").addEventListener("click", submitForm);

function initialize() {
  var input = document.getElementById("searchTextField");
  new google.maps.places.Autocomplete(input);
}

google.maps.event.addDomListener(window, "load", initialize);

function submitForm() {
  var formValid = document.forms["addrestaurant-form"].reportValidity();
  if (formValid) {
    // if (password === confirmPassword) {
    //   document.getElementById("passwordError").classList.add("visually-hidden");
    document.getElementById("addrestaurant-form").submit();
    // } else {
    //   document
    //     .getElementById("passwordError")
    //     .classList.remove("visually-hidden");
    // }
  }
}
