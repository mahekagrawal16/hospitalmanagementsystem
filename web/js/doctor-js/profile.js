// Form submission handling for each section
  document.getElementById('personalInfoForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Personal information saved!');
  });

  document.getElementById('contactInfoForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Contact information saved!');
  });

  document.getElementById('hospitalAffiliationForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Hospital affiliation saved!');
  });

  document.getElementById('availabilityForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Availability preferences saved!');
  });

  document.getElementById('socialLinksForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Social links saved!');
  });

  document.getElementById('passwordForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Password changed successfully!');
  });
 function confirmLogout(){
    alert("Are you sure you want to logout?");
}