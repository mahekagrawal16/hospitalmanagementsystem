// Filter hospital appointments based on hospital name
  document.getElementById('hospitalFilter').addEventListener('change', function () {
    const filterValue = this.value;
    const rows = document.querySelectorAll('#appointmentTable tr');

    rows.forEach(row => {
      const hospitalName = row.cells[1].textContent.toLowerCase();
      if (filterValue === 'all' || hospitalName.includes(filterValue)) {
        row.style.display = '';
      } else {
        row.style.display = 'none';
      }
    });
  });

  // View Appointment functionality
  function viewAppointment(patient, doctor, hospital, date, status) {
    const appointmentDetails = `
      <p><strong>Patient Name:</strong> ${patient}</p>
      <p><strong>Doctor Name:</strong> ${doctor}</p>
      <p><strong>Hospital Name:</strong> ${hospital}</p>
      <p><strong>Appointment Date:</strong> ${date}</p>
      <p><strong>Status:</strong> ${status}</p>
    `;
    document.getElementById("appointmentDetails").innerHTML = appointmentDetails;
    new bootstrap.Modal(document.getElementById("viewAppointmentModal")).show();
  }

  // Update Appointment Status
  function updateStatus(btn) {
    const row = btn.closest("tr");
    const statusCell = row.querySelector("td:nth-child(6) span");

    let currentStatus = statusCell.textContent.trim().toLowerCase();
    const statuses = ['confirmed', 'pending', 'cancelled'];
    const nextStatus = statuses[(statuses.indexOf(currentStatus) + 1) % statuses.length];

    statusCell.textContent = nextStatus.charAt(0).toUpperCase() + nextStatus.slice(1);
    statusCell.className = `badge bg-${nextStatus === 'confirmed' ? 'success' : nextStatus === 'pending' ? 'warning' : 'danger'}`;
  }

  // Delete Appointment functionality
  function deleteAppointment(btn) {
    if (confirm("Are you sure you want to delete this appointment?")) {
      const row = btn.closest("tr");
      row.remove();
    }
  }
function confirmLogout(){
    alert("Are you sure you want to logout?");
}