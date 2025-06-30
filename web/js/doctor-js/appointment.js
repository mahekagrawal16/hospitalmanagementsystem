function applyFilter() {
      const search = document.getElementById('searchInput').value.toLowerCase();
      const status = document.getElementById('statusFilter').value;

      const rows = document.querySelectorAll('#appointmentsTable tbody tr');

      rows.forEach(row => {
        const name = row.children[0].textContent.toLowerCase();
        const date = row.children[1].textContent.toLowerCase();
        const statusText = row.children[3].textContent.toLowerCase();

        const searchMatch = name.includes(search) || date.includes(search);
        const statusMatch = status === "Filter by status" || statusText.includes(status);

        row.style.display = (searchMatch && statusMatch) ? '' : 'none';
      });
    }

    let currentAppointmentId = null;

    function viewAppointment(id) {
      currentAppointmentId = id;

      const row = document.querySelector(`tr[data-id="${id}"]`);
      const name = row.children[0].textContent;
      const date = row.children[1].textContent;
      const reason = row.children[2].textContent;
      const status = row.children[3].textContent;

      const details = `
        <strong>Patient Name:</strong> ${name} <br>
        <strong>Current Date & Time:</strong> ${date} <br>
        <strong>Reason:</strong> ${reason} <br>
        <strong>Status:</strong> ${status}
      `;

      document.getElementById('appointmentDetails').innerHTML = details;
      document.getElementById('rescheduleForm').style.display = "none";
      document.getElementById('saveRescheduleBtn').style.display = "none";
      document.getElementById('rescheduleBtn').style.display = "inline-block";

      const modal = new bootstrap.Modal(document.getElementById('viewAppointmentModal'));
      modal.show();
    }

    function showRescheduleForm() {
      document.getElementById('rescheduleForm').style.display = "block";
      document.getElementById('saveRescheduleBtn').style.display = "inline-block";
      document.getElementById('rescheduleBtn').style.display = "none";
    }

    function saveRescheduledDate() {
      const newDateTime = document.getElementById('newDateTime').value;
      const now = new Date();
      const selectedDate = new Date(newDateTime);

      if (!newDateTime) {
        alert("Please select a new date and time.");
        return;
      }

      if (selectedDate <= now) {
        alert("Please select a future date and time.");
        return;
      }

      const row = document.querySelector(`tr[data-id="${currentAppointmentId}"]`);
      row.children[1].textContent = selectedDate.toLocaleString();

      alert("Appointment rescheduled successfully!");
      const modal = bootstrap.Modal.getInstance(document.getElementById('viewAppointmentModal'));
      modal.hide();
    }
    
    function cancelAppointment(id) {
      const row = document.querySelector(`tr[data-id="${id}"]`);
      const statusBadge = row.querySelector('.status-badge');
      const currentStatus = row.getAttribute('data-status');

      if (currentStatus === 'completed' || currentStatus === 'cancelled') {
        alert("You cannot cancel this appointment as it has already been completed or cancelled.");
        return;
      }

      const confirmation = confirm("Are you sure you want to cancel this appointment?");
      if (confirmation) {
        row.setAttribute('data-status', 'cancelled');
        statusBadge.className = 'badge bg-danger status-badge';
        statusBadge.textContent = 'Cancelled';
      }
    }
function confirmLogout(){
    alert("Are you sure you want to logout?");
}