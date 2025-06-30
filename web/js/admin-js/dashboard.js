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
function confirmLogout(){
    alert("Are you sure you want to logout?");
}