function downloadLogs() {
    const csv = `ID,User,Action,Timestamp\n1,John Doe,Booked appointment,2025-04-23 09:30\n2,Jane Smith,Cancelled appointment,2025-04-22 14:10\n3,Dr. Patel,Updated schedule,2025-04-22 10:45`;
    const blob = new Blob([csv], { type: 'text/csv' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'user_activity_logs.csv';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  function exportReport() {
    const report = `Hospital,Appointments,Doctors,Rating\nCity Care,420,35,4.5\nHealthPlus,320,25,4.2\nMediHeal,290,30,4.3`;
    const blob = new Blob([report], { type: 'text/csv' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'hospital_performance.csv';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
function confirmLogout(){
    alert("Are you sure you want to logout?");
}