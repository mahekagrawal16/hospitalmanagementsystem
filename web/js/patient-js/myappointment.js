   
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

   function openRescheduleModal(id, date, time) {
        document.getElementById('rescheduleAppointmentId').value = id;
        document.getElementById('rescheduleDate').value = date;
        document.getElementById('rescheduleTime').value = time;
        new bootstrap.Modal(document.getElementById('rescheduleModal')).show();
    }

    function openCancelModal(id) {
        document.getElementById('cancelAppointmentId').value = id;
        new bootstrap.Modal(document.getElementById('cancelModal')).show();
    }
  function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }