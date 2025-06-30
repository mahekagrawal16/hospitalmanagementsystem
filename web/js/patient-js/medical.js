   function handleEdit(btn) {
        const form = btn.closest('form');
        const input = form.querySelector('input[name="value"]');
        const oldVal = input.value.split('||')[0];
        const newVal = prompt("Edit item:", oldVal);
        if (newVal && newVal !== oldVal) {
            input.value = oldVal + "||" + newVal;
            form.submit();
        }
    }
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
  function confirmLogout() {
    return confirm('Are you sure you want to logout?');
  }