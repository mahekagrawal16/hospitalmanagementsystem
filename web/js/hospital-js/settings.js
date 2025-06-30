   // Save Hospital Logo and Type
    function saveLogoType() {
      const logo = document.getElementById("hospital-logo").files[0];
      const type = document.getElementById("hospital-type").value;

      if (logo) {
        localStorage.setItem("hospital-logo", logo.name);
      }
      localStorage.setItem("hospital-type", type);

      alert("Hospital Logo & Type saved successfully!");
      return false;  // Prevent page refresh
    }

    // Save Hospital Info
    function saveHospitalInfo() {
      const name = document.getElementById("hospital-name").value;
      const address = document.getElementById("hospital-address").value;
      const email = document.getElementById("hospital-email").value;
      const phone = document.getElementById("hospital-phone").value;

      // Save data to localStorage (simulating save)
      localStorage.setItem("hospital-name", name);
      localStorage.setItem("hospital-address", address);
      localStorage.setItem("hospital-email", email);
      localStorage.setItem("hospital-phone", phone);

      alert("Hospital Info saved successfully!");
      return false;  // Prevent page refresh
    }

    // Save Admin Info
    function saveAdminInfo() {
      const name = document.getElementById("admin-name").value;
      const phone = document.getElementById("admin-phone").value;
      const email = document.getElementById("admin-email").value;
      const password = document.getElementById("admin-password").value;

      // Save data to localStorage (simulating save)
      localStorage.setItem("admin-name", name);
      localStorage.setItem("admin-phone", phone);
      localStorage.setItem("admin-email", email);
      localStorage.setItem("admin-password", password);

      alert("Admin Info saved successfully!");
      return false;  // Prevent page refresh
    }

    // Save Security Settings
    function saveSecuritySettings() {
      const enable2fa = document.getElementById("enable-2fa").checked;
      const ipRestriction = document.getElementById("ip-restriction").value;
      const auditLog = document.getElementById("audit-log").value;

      // Save security settings to localStorage
      localStorage.setItem("2fa-enabled", enable2fa);
      localStorage.setItem("ip-restriction", ipRestriction);
      localStorage.setItem("audit-log", auditLog);

      alert("Security Settings saved successfully!");
      return false;  // Prevent page refresh
    }

    // Generate PDF Report
    function generateReport() {
      const { jsPDF } = window.jspdf;
      const doc = new jsPDF();

      doc.text("Hospital Report", 20, 10);
      doc.text("Hospital Name: " + localStorage.getItem("hospital-name"), 20, 20);
      doc.text("Hospital Address: " + localStorage.getItem("hospital-address"), 20, 30);
      doc.text("Hospital Email: " + localStorage.getItem("hospital-email"), 20, 40);
      doc.text("Hospital Phone: " + localStorage.getItem("hospital-phone"), 20, 50);
      doc.text("Admin Name: " + localStorage.getItem("admin-name"), 20, 60);
      doc.text("Admin Phone: " + localStorage.getItem("admin-phone"), 20, 70);
      doc.text("Admin Email: " + localStorage.getItem("admin-email"), 20, 80);

      // Save PDF
      doc.save("hospital_report.pdf");
    }
    function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }