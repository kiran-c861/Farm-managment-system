/**
 * Mobile Sidebar Toggle - shared across all pages
 * Adds hamburger button + overlay dynamically for mobile
 */
(function () {
    // Inject overlay element
    const overlay = document.createElement('div');
    overlay.className = 'sidebar-overlay';
    overlay.id = 'sidebarOverlay';
    document.body.appendChild(overlay);

    // Inject hamburger button into top-header
    const header = document.querySelector('.top-header');
    if (header) {
        const hamburger = document.createElement('button');
        hamburger.className = 'hamburger-btn';
        hamburger.id = 'hamburgerBtn';
        hamburger.setAttribute('aria-label', 'Toggle menu');
        hamburger.innerHTML = '<i class="fas fa-bars"></i>';
        header.insertBefore(hamburger, header.firstChild);

        const sidebar = document.querySelector('.sidebar');

        function openSidebar() {
            sidebar.classList.add('open');
            overlay.classList.add('active');
            hamburger.innerHTML = '<i class="fas fa-times"></i>';
            document.body.style.overflow = 'hidden';
        }

        function closeSidebar() {
            sidebar.classList.remove('open');
            overlay.classList.remove('active');
            hamburger.innerHTML = '<i class="fas fa-bars"></i>';
            document.body.style.overflow = '';
        }

        hamburger.addEventListener('click', function () {
            if (sidebar.classList.contains('open')) closeSidebar();
            else openSidebar();
        });

        overlay.addEventListener('click', closeSidebar);

        // Close sidebar when a nav item is clicked (mobile UX)
        document.querySelectorAll('.nav-item').forEach(function (item) {
            item.addEventListener('click', function () {
                if (window.innerWidth <= 768) closeSidebar();
            });
        });

        // Close on resize to desktop
        window.addEventListener('resize', function () {
            if (window.innerWidth > 768) closeSidebar();
        });
    }
})();
