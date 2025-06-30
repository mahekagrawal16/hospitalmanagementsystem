   
   // View Button Click Event: Populate Modal with Prescription Data
    document.querySelectorAll('.btn-view').forEach(button => {
      button.addEventListener('click', function () {
        const prescription = JSON.parse(this.getAttribute('data-prescription'));
  
        // Populate modal with prescription details
        document.getElementById('modal-date').innerText = prescription.date;
        document.getElementById('modal-doctor').innerText = prescription.doctor;
        document.getElementById('modal-medication').innerText = prescription.medication;
        document.getElementById('modal-dosage').innerText = prescription.dosage;
        document.getElementById('modal-instructions').innerText = prescription.instructions;
        document.getElementById('modal-diagnosis').innerText = prescription.diagnosis;
  
        // Show modal
        const modal = new bootstrap.Modal(document.getElementById('prescriptionModal'));
        modal.show();
      });
    });
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