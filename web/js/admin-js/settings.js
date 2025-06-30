    // Generic validation function
    function validateForm(formId) {
      const form = document.getElementById(formId);
      let isValid = true;
  
      Array.from(form.elements).forEach((field) => {
        if (field.required && !field.value.trim()) {
          isValid = false;
          document.getElementById(field.id + 'Error').style.display = 'block';
          field.classList.add('is-invalid');
        } else {
          const errorEl = document.getElementById(field.id + 'Error');
          if (errorEl) errorEl.style.display = 'none';
          field.classList.remove('is-invalid');
        }
      });
  
      return isValid;
    }
  
    // Form submission handler
    function handleFormSubmission(formId, message) {
      document.getElementById(formId).addEventListener('submit', function(e) {
        e.preventDefault();
        if (validateForm(formId)) {
          // Collect form data (for demo purpose, log to console)
          const formData = new FormData(this);
          const data = {};
          formData.forEach((value, key) => {
            data[key] = value;
          });
          console.log(`${message}`, data);
          alert(`${message}`);
        }
      });
    }
  
    // Initialize all forms
    handleFormSubmission('systemConfigForm', 'System Configuration saved!');
    handleFormSubmission('hospitalManagementForm', 'Hospital Management Settings saved!');
    handleFormSubmission('userPreferencesForm', 'User Preferences saved!');
    handleFormSubmission('securitySettingsForm', 'Security Settings saved!');
function confirmLogout(){
    alert("Are you sure you want to logout?");
}