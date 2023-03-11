setTimeout(function() {
  document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById("flash-message").classList.add("avisible");
  });

  // Hide the flash message after 5 seconds
  setTimeout(function() {
    document.getElementById("flash-message").classList.remove("avisible");
    setTimeout(function() {
      document.getElementById("flash-message").classList.add("ahidden");
    }, 200);
  }, 2000);
});