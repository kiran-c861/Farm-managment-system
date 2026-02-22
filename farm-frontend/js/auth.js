/**
 * Auth utilities - login/logout, user display, and toast notifications.
 */

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}

function requireAuth() {
    if (!localStorage.getItem('token')) {
        window.location.href = 'index.html';
        return false;
    }
    return true;
}

function populateUserInfo() {
    const user = getCurrentUser();
    const nameEl = document.getElementById('user-name');
    const roleEl = document.getElementById('user-role');
    const avatarEl = document.getElementById('user-avatar');
    if (nameEl) nameEl.textContent = user.fullName || user.username || 'User';
    if (roleEl) roleEl.textContent = user.role || 'WORKER';
    if (avatarEl) avatarEl.textContent = (user.fullName || user.username || 'U')[0].toUpperCase();
}

// Toast notifications
function showToast(message, type = 'success') {
    const container = document.getElementById('toastContainer');
    if (!container) return;
    const icons = { success: 'fa-check-circle', error: 'fa-times-circle', warning: 'fa-exclamation-triangle' };
    const colors = { success: '#10b981', error: '#ef4444', warning: '#f59e0b' };
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `<i class="fas ${icons[type] || icons.success}" style="color:${colors[type]};font-size:18px;"></i><span style="font-size:14px;">${message}</span>`;
    container.appendChild(toast);
    setTimeout(() => { toast.style.opacity = '0'; toast.style.transform = 'translateX(100%)'; setTimeout(() => toast.remove(), 300); }, 3500);
}

// Sidebar navigation active state
function setActiveSidebarItem(page) {
    document.querySelectorAll('.nav-item').forEach(el => {
        el.classList.remove('active');
        if (el.dataset.page === page) el.classList.add('active');
    });
}

// Format date for display
function formatDate(dateStr) {
    if (!dateStr) return '—';
    return new Date(dateStr).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' });
}

// Format currency
function formatCurrency(amount) {
    if (amount == null) return '₹0';
    return '₹' + Number(amount).toLocaleString('en-IN', { minimumFractionDigits: 0 });
}

// Confirm delete dialog
function confirmDelete(message, onConfirm) {
    if (window.confirm(message || 'Are you sure you want to delete this record?')) {
        onConfirm();
    }
}
