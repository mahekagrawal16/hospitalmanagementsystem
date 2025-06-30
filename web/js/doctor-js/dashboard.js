document.getElementById('editProfileForm').addEventListener('submit', function(e) {
      e.preventDefault();

      // Basic validation for empty fields
      let valid = true;
      let inputs = document.querySelectorAll('#editProfileForm input');
      inputs.forEach(input => {
        if (input.value.trim() === '') {
          input.classList.add('is-invalid');
          valid = false;
        } else {
          input.classList.remove('is-invalid');
        }
      });

      if (valid) {
        alert('Profile updated successfully!');
        // You can make an AJAX request to save the changes to the server here
        // Close the modal
        let modal = bootstrap.Modal.getInstance(document.getElementById('editProfileModal'));
        modal.hide();
      }
    });
function confirmLogout(){
    alert("Are you sure you want to logout?");
}