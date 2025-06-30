 // View Record
    document.querySelectorAll('.view-record').forEach(btn => {
      btn.addEventListener('click', function () {
        const row = this.closest('tr');
        const id = row.dataset.id;
        const name = row.dataset.name;
        const age = row.dataset.age;
        const condition = row.dataset.condition;
        const lastVisit = row.dataset.lastVisit;
  
        document.getElementById('viewId').textContent = id;
        document.getElementById('viewName').textContent = name;
        document.getElementById('viewAge').textContent = age;
        document.getElementById('viewCondition').textContent = condition;
        document.getElementById('viewLastVisit').textContent = lastVisit;
  
        // Placeholder values for additional info
        document.getElementById('viewContact').textContent = id === "P1001" ? "+91-9998887777" : "+91-8887776666";
        document.getElementById('viewHistory').textContent = id === "P1001" ? 
          "Diagnosed with Type 2 Diabetes. On Metformin. No recent complications." : 
          "Heart disease diagnosed in 2021. On beta blockers. Regular exercise recommended.";
  
        new bootstrap.Modal(document.getElementById('viewModal')).show();
      });
    });
  
    // Edit Record
    document.querySelectorAll('.edit-record').forEach(btn => {
      btn.addEventListener('click', function () {
        const row = this.closest('tr');
        document.getElementById('editId').value = row.dataset.id;
        document.getElementById('editName').value = row.dataset.name;
        document.getElementById('editAge').value = row.dataset.age;
        document.getElementById('editCondition').value = row.dataset.condition;
        document.getElementById('editLastVisit').value = row.dataset.lastVisit;
        new bootstrap.Modal(document.getElementById('editModal')).show();
      });
    });
  
    // Save Changes with Validation
    document.getElementById('editForm').addEventListener('submit', function (e) {
      e.preventDefault();
      const id = document.getElementById('editId').value;
      const name = document.getElementById('editName').value.trim();
      const age = document.getElementById('editAge').value.trim();
      const condition = document.getElementById('editCondition').value.trim();
      const lastVisit = document.getElementById('editLastVisit').value.trim();
  
      if (!name || !condition || isNaN(age) || age < 1 || !lastVisit) {
        alert("Please provide valid Name, Age, Condition, and Last Visit Date.");
        return;
      }
  
      const row = [...document.querySelectorAll('tr')].find(r => r.dataset.id === id);
      row.dataset.name = name;
      row.dataset.age = age;
      row.dataset.condition = condition;
      row.dataset.lastVisit = lastVisit;
      row.children[1].textContent = name;
      row.children[2].textContent = age;
      row.children[3].textContent = condition;
      row.children[4].textContent = lastVisit;
  
      bootstrap.Modal.getInstance(document.getElementById('editModal')).hide();
    });
  
    // Filter function
    function applyPatientFilter() {
      const search = document.getElementById('searchInput').value.toLowerCase();
      const conditionSelect = document.getElementById('conditionFilter');
      const condition = conditionSelect.value;
  
      document.querySelectorAll('#recordsTable tbody tr').forEach(row => {
        const name = row.dataset.name.toLowerCase();
        const id = row.dataset.id.toLowerCase();
        const cond = row.dataset.condition;
        const visitDate = row.dataset.lastVisit;
  
        const searchMatch = name.includes(search) || id.includes(search);
        const conditionMatch = !condition || cond === condition;
  
        row.style.display = searchMatch && conditionMatch ? "" : "none";
      });
    }
  
    // Enable clicking the first filter option
    document.getElementById('conditionFilter').addEventListener('change', applyPatientFilter);
   function confirmLogout(){
    alert("Are you sure you want to logout?");
}