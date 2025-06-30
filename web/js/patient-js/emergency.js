 function updateEmergencyContact() {
    const name = document.getElementById("emergency-name").value;
    const phone = document.getElementById("emergency-phone").value;
    alert(`Emergency contact updated:\nName: ${name}\nPhone: ${phone}`);
  }

  function uploadBiometric() {
    const fileInput = document.getElementById("biometric-image");
    if (fileInput.files.length === 0) {
      alert("Please select a biometric image to upload.");
      return;
    }
    const fileName = fileInput.files[0].name;
    alert(`Biometric image "${fileName}" uploaded successfully.`);
    // Optional: send image to server using FormData + fetch or AJAX
  }
  document.addEventListener('DOMContentLoaded', () => {
    const links = document.querySelectorAll('.sidebar a');
    const currentPage = window.location.pathname.split("/").pop();

    links.forEach(link => {
      const linkPage = link.getAttribute('href').split("/").pop();
      if (linkPage === currentPage) {
        link.classList.add("active");
      }
    });
  });
  function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }