let editRow = null;

  function getCurrentDateTime() {
    const now = new Date();
    return now.toLocaleString();
  }

  function openPrescriptionModal() {
    editRow = null;
    document.getElementById('prescriptionForm').reset();
    document.getElementById('prescriptionModalLabel').innerText = "New Prescription";
    document.getElementById('saveBtn').innerText = "Save Prescription";
    const modal = new bootstrap.Modal(document.getElementById('prescriptionModal'));
    modal.show();
  }

  document.getElementById('prescriptionForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const patientName = document.getElementById('patientName').value.trim();
    const medicine = document.getElementById('medicine').value.trim();
    const dosage = document.getElementById('dosage').value.trim();
    const frequency = document.getElementById('frequency').value.trim();
    const notes = document.getElementById('notes').value.trim();
    const nextVisit = document.getElementById('nextVisit').value.trim();
    const lastModified = getCurrentDateTime();

    if (!patientName || !medicine || !dosage || !frequency || !nextVisit) {
      alert("Please fill in all required fields.");
      return;
    }

    if (editRow) {
      const cells = editRow.children;
      cells[0].textContent = patientName;
      cells[1].textContent = medicine;
      cells[2].textContent = dosage;
      cells[3].textContent = frequency;
      cells[4].textContent = notes;
      editRow.setAttribute("data-nextvisit", nextVisit);
      editRow.setAttribute("data-modified", lastModified);
    } else {
      const row = document.createElement("tr");
      row.setAttribute("data-nextvisit", nextVisit);
      row.setAttribute("data-modified", lastModified);
      row.innerHTML = `
        <td>${patientName}</td>
        <td>${medicine}</td>
        <td>${dosage}</td>
        <td>${frequency}</td>
        <td>${notes}</td>
        <td>
          <button class="btn btn-sm btn-success" onclick="viewPrescription(this)">View</button>
          <button class="btn btn-sm btn-warning" onclick="editPrescription(this)">Edit</button>
          <button class="btn btn-sm btn-danger" onclick="deletePrescription(this)">Delete</button>
        </td>
      `;
      document.getElementById("prescriptionTable").appendChild(row);
    }

    bootstrap.Modal.getInstance(document.getElementById('prescriptionModal')).hide();
  });

  function editPrescription(btn) {
    editRow = btn.closest("tr");
    const cells = editRow.children;
    document.getElementById('patientName').value = cells[0].textContent;
    document.getElementById('medicine').value = cells[1].textContent;
    document.getElementById('dosage').value = cells[2].textContent;
    document.getElementById('frequency').value = cells[3].textContent;
    document.getElementById('notes').value = cells[4].textContent;
    document.getElementById('nextVisit').value = editRow.getAttribute('data-nextvisit') || '';

    document.getElementById('prescriptionModalLabel').innerText = "Edit Prescription";
    document.getElementById('saveBtn').innerText = "Update Prescription";
    const modal = new bootstrap.Modal(document.getElementById('prescriptionModal'));
    modal.show();
  }

  function deletePrescription(btn) {
    if (confirm("Are you sure you want to delete this prescription?")) {
      btn.closest("tr").remove();
    }
  }

  function viewPrescription(btn) {
    const row = btn.closest("tr");
    const cells = row.children;
    const nextVisit = row.getAttribute("data-nextvisit") || "Not available";
    const modified = row.getAttribute("data-modified") || "Not available";

    const html = `
      <strong>Patient:</strong> ${cells[0].textContent}<br>
      <strong>Medicine:</strong> ${cells[1].textContent}<br>
      <strong>Dosage:</strong> ${cells[2].textContent}<br>
      <strong>Frequency:</strong> ${cells[3].textContent}<br>
      <strong>Notes:</strong> ${cells[4].textContent}<br><br>
      <strong>Next Visit:</strong> ${nextVisit}<br>
      <strong>Last Modified:</strong> ${modified}
    `;
    document.getElementById('viewModalBody').innerHTML = html;
    const modal = new bootstrap.Modal(document.getElementById('viewModal'));
    modal.show();
    
  }
  function confirmLogout(){
    alert("Are you sure you want to logout?");
}