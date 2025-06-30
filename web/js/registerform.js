
  document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registrationForm");
    const roleSelect = document.getElementById("role");

    showRoleFields("");

    roleSelect.addEventListener("change", (e) => {
      showRoleFields(e.target.value);
    });

    form.addEventListener("submit", function (event) {
      event.preventDefault();

      // === Common fields ===
      const fullName = document.getElementById("fullName").value.trim();
      const email = document.getElementById("email").value.trim();
      const phone = document.getElementById("phone").value.trim();
      const password = document.getElementById("password").value;
      const confirmPassword = document.getElementById("confirmPassword").value;
      const gender = document.querySelector('input[name="gender"]:checked');
      const dob = document.getElementById("dob").value;
      const address = document.getElementById("address").value.trim();
      const terms = document.getElementById("terms").checked;
      const privacy = document.getElementById("privacy").checked;
      const role = roleSelect.value;

      // === Basic Validation ===
      if (!fullName || !email || !phone || !password || !confirmPassword || !gender || !dob || !address) {
        alert("Please fill in all required fields.");
        return;
      }

      if (!validateEmail(email)) {
        alert("Please enter a valid email address.");
        return;
      }

      if (!/^\d{10}$/.test(phone)) {
        alert("Please enter a valid 10-digit phone number.");
        return;
      }

      if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
      }

      if (!terms || !privacy) {
        alert("You must accept the Terms and Privacy Policy.");
        return;
      }

      // === Role-specific validation ===
      if (role === "") {
        alert("Please select a role.");
        return;
      }

      if (role === "patient") {
        const emergencyContactName = document.getElementById("emergencyContactName").value.trim();
        const emergencyContactPhone = document.getElementById("emergencyContactPhone").value.trim();
        const bloodType = document.getElementById("bloodType").value.trim();
        const allergies = document.getElementById("allergies").value.trim();
        const medicalHistory = document.getElementById("medicalHistory").value.trim();
        const preferredHospital = document.getElementById("preferredHospital").value.trim();
        const insuranceProvider = document.getElementById("insuranceProvider").value.trim();
        
        if (!emergencyContactName || !emergencyContactPhone || !bloodType || !allergies || !medicalHistory || !preferredHospital || !insuranceProvider) {
          alert("Please fill in all the patient details.");
          return;
        }

        if (!/^\d{10}$/.test(emergencyContactPhone)) {
          alert("Please enter a valid emergency phone number.");
          return;
        }
      }

      if (role === "doctor") {
        const specialization = document.getElementById("specialization").value.trim();
        const licenseNumber = document.getElementById("licenseNumber").value.trim();
        const qualifications = document.getElementById("qualifications").value.trim();
        const yearsExperience = document.getElementById("yearsExperience").value.trim();
        const hospitalAffiliation = document.getElementById("hospitalAffiliation").value.trim();
        const doctorProfilePic = document.getElementById("doctorProfilePic").value.trim();
        const availability = document.getElementById("availability").value.trim();

            if (!specialization || !licenseNumber || !qualifications || !yearsExperience || !hospitalAffiliation || !doctorProfilePic || !availability) {
          alert("Please fill in all the doctor details.");
          return;
        }
      }

      if (role === "hospital") {
        const hospitalName = document.getElementById("hospitalName").value.trim();
        const hospitalContact = document.getElementById("hospitalContact").value.trim();
        const hospitalAddress = document.getElementById("hospitalAddress").value.trim();
        const hospitalType = document.getElementById("hospitalType").value.trim();
        const affiliatedDoctors = document.getElementById("affiliatedDoctors").value.trim();
        const hospitalLogo = document.getElementById("hospitalLogo").value.trim();

        if (!hospitalName || !hospitalContact || !hospitalAddress || !hospitalType || !affiliatedDoctors || !hospitalLogo) {
          alert("Please fill in all the hospital details.");
          return;
        }

        if (!/^\d{10}$/.test(hospitalContact)) {
          alert("Please enter a valid hospital contact number.");
          return;
        }
      }

      if (role === "admin") {
        const adminCode = document.getElementById("adminCode").value.trim();
        const roleDescription = document.getElementById("roleDescription").value.trim();
        if (!adminCode || !roleDescription) {
          alert("Please fill in  all the admin details.");
          return;
        }
      }

      // === Final Submit ===
      form.submit();
    });
  });

  function showRoleFields(role) {
    const roles = ['patient', 'doctor', 'hospital', 'admin'];
    roles.forEach(r => {
      const section = document.getElementById(r + "Fields");
      if (section) section.style.display = "none";
    });

    if (role) {
      const selected = document.getElementById(role + "Fields");
      if (selected) selected.style.display = "block";
    }
  }

  function validateEmail(email) {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(email);
  }