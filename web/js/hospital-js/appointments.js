function approveAppointment(button) {
      const row = button.closest('tr');
      const statusCell = row.querySelector('.status');
      if (statusCell.textContent === 'Approved') {
        alert("This appointment is already approved.");
        return;
      }
      statusCell.textContent = 'Approved';
      statusCell.className = 'badge bg-success status';

      // Disable the approve button, enable/disable others as needed
      button.disabled = true;
      button.classList.replace('btn-success', 'btn-secondary');
      button.textContent = 'Approved';
    }

    function cancelAppointment(button) {
      const row = button.closest('tr');
      const patientName = row.cells[1].textContent;

      const confirmCancel = confirm(`Are you sure you want to cancel the appointment for ${patientName}?`);
      if (confirmCancel) {
        row.remove(); // Or mark as cancelled
        alert("Appointment cancelled successfully.");
      }
    }
    function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }