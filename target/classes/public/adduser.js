document.getElementById("submit-button").addEventListener("click", submitForm);

function submitForm() {
  var password = document.getElementById("password").value;
  var confirmPassword = document.getElementById("confirmPassword").value;

  var formValid = document.forms["adduser-form"].reportValidity();
  if (formValid) {
    if (password === confirmPassword) {
      document.getElementById("passwordError").classList.add("visually-hidden");
      document.getElementById("adduser-form").submit();
    } else {
      document
        .getElementById("passwordError")
        .classList.remove("visually-hidden");
    }
  }
}
