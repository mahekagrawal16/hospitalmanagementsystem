    // Register new doctor
    function registerDoctor() {
      const name = document.getElementById('doctor-name').value;
      const specialization = document.getElementById('specialization').value;
      const age = document.getElementById('doctor-age').value;
      const contact = document.getElementById('contact-number').value;
      const address = document.getElementById('address').value;
      const qualification = document.getElementById('qualification').value;
      
      if (name && specialization && age && contact && address && qualification) {
        // Add new doctor to table
        const table = document.getElementById('doctor-table').getElementsByTagName('tbody')[0];
        const row = table.insertRow();
        row.innerHTML = `<td>${name}</td><td>${specialization}</td><td>${age}</td><td>${contact}</td><td>${qualification}</td><td><button class="btn btn-sm btn-warning" onclick="editDoctor(this)">Edit</button><button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#doctorScheduleModal" onclick="setDoctorSchedule('${name}')">Schedule</button><button class="btn btn-sm btn-danger" onclick="confirmDeleteDoctor(this)">Delete</button></td>`;
        
        alert('Doctor successfully registered!');
      } else {
        alert('Please fill out all the fields.');
      }
    }

    let editingRow = null;

function editDoctor(button) {
  editingRow = button.closest("tr");

  const cells = editingRow.querySelectorAll("td");

document.getElementById("edit-doctorName").value = cells[0].innerText.trim();
document.getElementById("edit-specialization").value = cells[1].innerText.trim();
document.getElementById("edit-age").value = cells[2].innerText.trim();
document.getElementById("edit-contact").value = cells[3].innerText.trim();
document.getElementById("edit-qualification").value = cells[4].innerText.trim();


  document.getElementById("editDoctorForm").classList.remove("was-validated");
  const modal = new bootstrap.Modal(document.getElementById("editDoctorModal"));
  modal.show();
}

function saveDoctorDetails() {
  const form = document.getElementById("editDoctorForm");

  form.classList.add("was-validated");
  if (!form.checkValidity()) {
    return;
  }

  const cells = editingRow.querySelectorAll("td");

  cells[0].innerText = document.getElementById("edit-doctorName").value.trim();
cells[1].innerText = document.getElementById("edit-specialization").value.trim();
cells[2].innerText = document.getElementById("edit-age").value.trim();
cells[3].innerText = document.getElementById("edit-contact").value.trim();
cells[4].innerText = document.getElementById("edit-qualification").value.trim();

alert('Schedule updated successfully!');
  bootstrap.Modal.getInstance(document.getElementById('editDoctorModal')).hide();
}

    // Delete doctor
    function confirmDeleteDoctor(button) {
      if (confirm('Are you sure you want to delete this doctor?')) {
        const row = button.closest('tr');
        row.remove();
      }
    }
    // Add doctor schedule
    function addSchedule(event) {
      event.preventDefault();
      
      const doctorName = document.getElementById('doctor-name-schedule').value;
      const day = document.getElementById('schedule-day').value;
      const time = document.getElementById('schedule-time').value;
      
      if (doctorName && day && time) {
        const table = document.getElementById('schedule-table').getElementsByTagName('tbody')[0];
        const row = table.insertRow();
        row.innerHTML = `<td>${doctorName}</td><td>${day}</td><td>${time}</td><td><button class="btn btn-sm btn-warning" onclick="editSchedule(this)">Edit</button><button class="btn btn-sm btn-danger" onclick="removeSchedule(this)">Remove</button></td>`;
        
        // Reset the form
        document.getElementById('doctor-schedule-form').reset();
        alert('Schedule added successfully!');
      } else {
        alert('Please fill out all fields for the schedule.');
      }
    }
    // Open Edit Schedule Modal
    function openEditScheduleModal(button) {
      const row = button.closest('tr');
      const doctorName = row.cells[0].textContent;
      const day = row.cells[1].textContent;
      const time = row.cells[2].textContent;

      // Pre-fill the modal with schedule details
      document.getElementById('edit-doctor-name').value = doctorName;
      document.getElementById('edit-schedule-day').value = day;
      document.getElementById('edit-schedule-time').value = time;

      // Show the modal
      var myModal = new bootstrap.Modal(document.getElementById('editScheduleModal'), {});
      myModal.show();
    }

    // Update schedule
    function updateSchedule() {
      const doctorName = document.getElementById('edit-doctor-name').value;
      const day = document.getElementById('edit-schedule-day').value;
      const time = document.getElementById('edit-schedule-time').value;

      if (doctorName && day && time) {
        const table = document.getElementById('schedule-table').getElementsByTagName('tbody')[0];
        const rows = table.rows;

        // Update the correct row with new schedule
        for (let i = 0; i < rows.length; i++) {
          if (rows[i].cells[0].textContent === doctorName && rows[i].cells[1].textContent === day) {
            rows[i].cells[2].textContent = time;
            break;
          }
        }

        alert('Schedule updated successfully!');
        var myModal = bootstrap.Modal.getInstance(document.getElementById('editScheduleModal'));
        myModal.hide();
      } else {
        alert('Please fill in all fields.');
      }
    }

    // Remove schedule
    function removeSchedule(button) {
      if (confirm('Are you sure you want to remove this schedule?')) {
        const row = button.closest('tr');
        row.remove();
      }
    }
    function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }