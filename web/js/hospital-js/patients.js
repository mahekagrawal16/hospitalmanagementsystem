    // Register new patient function
    function registerPatient() {
      const name = document.getElementById('patient-name').value;
      const age = document.getElementById('age').value;
      const contact = document.getElementById('contact').value;
      const address = document.getElementById('address').value;

      // Basic validation
      if (!name || !age || !contact || !address) {
        alert("All fields are required!");
        return;
      }

      if (!/^\d{10}$/.test(contact)) {
        alert("Please enter a valid 10-digit contact number.");
        return;
      }

      const patientTable = document.getElementById('patient-table').getElementsByTagName('tbody')[0];
      const newRow = patientTable.insertRow();

      newRow.innerHTML = `
        <td>${name}</td>
        <td>${age}</td>
        <td>Not yet assigned</td>
        <td>${contact}</td>
        <td>${new Date().toLocaleDateString()}</td>
        <td>
          <button class="btn btn-sm btn-warning" onclick="editPatient(this)">Edit</button>
          <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#notifyModal" onclick="setPatientForNotify('${name}', '${contact}')">Notify</button>
          <button class="btn btn-sm btn-secondary" data-bs-toggle="modal" data-bs-target="#uploadReportModal" onclick="setPatientForUpload('${name}')">Upload Report</button>
        </td>
      `;

      // Reset form
      document.getElementById('patient-form').reset();
    }

    // Set patient data for notification modal
    function setPatientForNotify(name, contact) {
      document.getElementById('notifyPatientName').innerText = `Sending notification to ${name} (${contact})`;
    }

    // Send notification (stub function)
    function sendNotification() {
      const message = document.getElementById('message').value;
      if (!message) {
        alert("Message cannot be empty.");
        return;
      }
      alert("Notification sent successfully!");
    }

    // Set patient data for upload report modal
    function setPatientForUpload(name) {
      console.log(`Ready to upload report for ${name}`);
    }

    // Upload report (stub function)
    function uploadReport() {
      const file = document.getElementById('reportFile').files[0];
      if (!file) {
        alert("Please choose a file to upload.");
        return;
      }
      alert(`Report for ${file.name} uploaded successfully.`);
    }

    // Edit patient details
    function editPatient(button) {
      const row = button.closest('tr');
      const cells = row.getElementsByTagName('td');
      
      document.getElementById('edit-name').value = cells[0].innerText;
      document.getElementById('edit-age').value = cells[1].innerText;
      document.getElementById('edit-contact').value = cells[3].innerText;
      document.getElementById('edit-disease').value = cells[2].innerText;

      // Show modal
      new bootstrap.Modal(document.getElementById('editPatientModal')).show();
    }

function updatePatientDetails() {
  const name = document.getElementById('edit-name').value;
  const age = document.getElementById('edit-age').value;
  const contact = document.getElementById('edit-contact').value;
  const disease = document.getElementById('edit-disease').value;

  // Basic validation
  if (!name || !age || !contact || !disease) {
    alert("All fields are required!");
    return;
  }

  const patientTable = document.getElementById('patient-table').getElementsByTagName('tbody')[0];
  const rows = patientTable.getElementsByTagName('tr');
  for (let row of rows) {
    const cells = row.getElementsByTagName('td');
    if (cells[0].innerText === name) {
      cells[1].innerText = age;
      cells[2].innerText = contact; // <-- fixed
      cells[3].innerText = disease; // <-- fixed
      break;
    }
  }
  alert('Patient updated successfully!');
  // Close modal
  bootstrap.Modal.getInstance(document.getElementById('editPatientModal')).hide();
}

    function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }