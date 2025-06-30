function updateProfile() {
    const requiredFields = ["full-name", "email", "phone", "dob", "gender", "address", "blood-type", "emergency-contact", "insurance"];
    for (const field of requiredFields) {
      if (!document.getElementById(field).value.trim()) {
        alert("Please fill in all required fields.");
        return;
      }
    }
    alert("Profile updated successfully!");
  }

  function updateSettings() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    if (password && password !== confirmPassword) {
      alert("Passwords do not match!");
      return;
    }
    const notifications = document.getElementById("notifications").value;
    const privacy = document.getElementById("privacy").value;
    const twoFactor = document.getElementById("two-factor").value;
    alert(`Settings updated successfully!\nNotifications: ${notifications}\nPrivacy: ${privacy}\nTwo-Factor: ${twoFactor}`);
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
