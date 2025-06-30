function viewPatient(userId) {
    // Populate patient modal with relevant data
    document.getElementById('patientName').innerText = 'Jane Doe'; // Replace with actual data
    document.getElementById('patientEmail').innerText = 'jane@example.com'; // Replace with actual data
    document.getElementById('patientPhone').innerText = '9876543210'; // Replace with actual data
    document.getElementById('patientAddress').innerText = '123 Street, City'; // Replace with actual data
    document.getElementById('patientDob').innerText = '01-01-1990'; // Replace with actual data
    document.getElementById('patientGender').innerText = 'Female'; // Replace with actual data
    document.getElementById('patientConditions').innerText = 'Hypertension'; // Replace with actual data
  }

  function viewDoctor(userId) {
    // Populate doctor modal with relevant data
    document.getElementById('doctorName').innerText = 'Dr. A Sharma'; // Replace with actual data
    document.getElementById('doctorEmail').innerText = 'docsharma@example.com'; // Replace with actual data
    document.getElementById('doctorSpecialization').innerText = 'Cardiologist'; // Replace with actual data
    document.getElementById('doctorExperience').innerText = '10 years'; // Replace with actual data
    document.getElementById('doctorEducation').innerText = 'MBBS, MD'; // Replace with actual data
    document.getElementById('doctorHospital').innerText = 'City Hospital'; // Replace with actual data
  }

  function viewHospital(userId) {
    // Populate hospital modal with relevant data
    document.getElementById('hospitalName').innerText = 'City Hospital'; // Replace with actual data
    document.getElementById('hospitalCity').innerText = 'Mumbai'; // Replace with actual data
    document.getElementById('hospitalContact').innerText = '1234567890'; // Replace with actual data
    document.getElementById('hospitalAddress').innerText = '456 Main Road'; // Replace with actual data
    document.getElementById('hospitalRating').innerText = '4.5 Stars'; // Replace with actual data
  }

  function deletePatientByName(nameToDelete) {
  if (confirm(`Are you sure you want to delete patient "${nameToDelete}"?`)) {
    const table = document.querySelector(".main-content .table-striped:nth-of-type(1) tbody");
    const rows = table.querySelectorAll("tr");
    let found = false;

    rows.forEach(row => {
      if (row.cells[1].innerText === nameToDelete) {
        row.remove();
        found = true;
        alert(`Patient "${nameToDelete}" has been successfully deleted.`);
      }
    });

    if (!found) {
      alert(`Patient "${nameToDelete}" not found.`);
    }
  }
}

function deleteDoctorByName(nameToDelete) {
  if (confirm(`Are you sure you want to delete doctor "${nameToDelete}"?`)) {
    const table = document.querySelector(".main-content .table-striped:nth-of-type(2) tbody");
    const rows = table.querySelectorAll("tr");
    let found = false;

    rows.forEach(row => {
      if (row.cells[1].innerText === nameToDelete) {
        row.remove();
        found = true;
        alert(`Doctor "${nameToDelete}" has been successfully deleted.`);
      }
    });

    if (!found) {
      alert(`Doctor "${nameToDelete}" not found.`);
    }
  }
}

function deleteHospitalByName(nameToDelete) {
  if (confirm(`Are you sure you want to delete hospital "${nameToDelete}"?`)) {
    const table = document.querySelector(".main-content .table-striped:nth-of-type(3) tbody");
    const rows = table.querySelectorAll("tr");
    let found = false;

    rows.forEach(row => {
      if (row.cells[1].innerText === nameToDelete) {
        row.remove();
        found = true;
        alert(`Hospital "${nameToDelete}" has been successfully deleted.`);
      }
    });

    if (!found) {
      alert(`Hospital "${nameToDelete}" not found.`);
    }
  }
}
function confirmLogout(){
    alert("Are you sure you want to logout?");
}