let editingRow = null; // To store the row being edited

// Add/Edit hospital functionality
document.getElementById("hospitalForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const name = document.getElementById("hospName").value;
    const city = document.getElementById("hospCity").value;
    const email = document.getElementById("hospEmail").value;
    const phone = document.getElementById("hospPhone").value;
    const status = document.getElementById("hospStatus").value;

    const table = document.getElementById("hospitalTable");

    if (editingRow) {
        // Update existing row (editing)
        const cells = editingRow.getElementsByTagName('td');
        cells[1].innerText = name;
        cells[2].innerText = city;
        cells[3].innerText = email;
        cells[4].innerHTML = `<span class="badge bg-${status === 'Approved' ? 'success' : status === 'Pending' ? 'warning' : 'danger'}">${status}</span>`;
        cells[5].innerText = phone;
        editingRow = null;
    } else {
        // Add new row
        const row = table.insertRow();
        row.innerHTML = `
        <td>${table.rows.length}</td>
        <td>${name}</td>
        <td>${city}</td>
        <td>${email}</td>
        <td><span class="badge bg-${status === 'Approved' ? 'success' : status === 'Pending' ? 'warning' : 'danger'}">${status}</span></td>
        <td>${phone}</td>
        <td>
          <button class="btn btn-sm btn-info" onclick="viewHospital('${table.rows.length}', '${name}', '${city}', '${email}', '${status}', '${phone}')">View</button>
          <button class="btn btn-sm btn-warning" onclick="editHospital(this)">Edit</button>
          <button class="btn btn-sm btn-danger" onclick="deleteHospital(this)">Delete</button>
        </td>
      `;
    }

    document.getElementById("hospitalForm").reset();
    const modal = bootstrap.Modal.getInstance(document.getElementById("addHospitalModal"));
    modal.hide();
});

// View hospital
function viewHospital(id, name, city, email, status, phone) {
    document.getElementById("hospitalDetails").innerHTML = `
      <p><strong>Hospital Name:</strong> ${name}</p>
      <p><strong>City:</strong> ${city}</p>
      <p><strong>Email:</strong> ${email}</p>
      <p><strong>Status:</strong> ${status}</p>
      <p><strong>Phone:</strong> ${phone}</p>
    `;
    new bootstrap.Modal(document.getElementById("viewHospitalModal")).show();
}

// Delete hospital
function deleteHospital(btn) {
    if (confirm("Are you sure you want to delete this hospital?")) {
        const row = btn.closest("tr");
        row.remove();
    }
}

// Edit hospital
function editHospital(btn) {
    const row = btn.closest("tr");
    const cells = row.querySelectorAll("td");
    const name = cells[1].innerText;
    const city = cells[2].innerText;
    const email = cells[3].innerText;
    const status = cells[4].innerText.trim();
    const phone = cells[5].innerText;

    document.getElementById("hospName").value = name;
    document.getElementById("hospCity").value = city;
    document.getElementById("hospEmail").value = email;
    document.getElementById("hospPhone").value = phone;
    document.getElementById("hospStatus").value = status;

    const modal = new bootstrap.Modal(document.getElementById("addHospitalModal"));
    modal.show();
    
    editingRow = row; // Store the row being edited
}
function confirmLogout(){
    alert("Are you sure you want to logout?");
}