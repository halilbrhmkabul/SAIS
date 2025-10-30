/**
 * Main Application JavaScript
 * Utility functions ve form helpers
 */

document.addEventListener('DOMContentLoaded', function() {
    console.log('SAIS Application initialized');
});

// Utility functions can be added here

// Smooth scroll to top
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

// Show loading indicator
function showLoading(message = 'Yükleniyor...') {
    // This can be integrated with PrimeFaces BlockUI
    console.log('Loading:', message);
}

// Hide loading indicator
function hideLoading() {
    console.log('Loading complete');
}

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('tr-TR', {
        style: 'currency',
        currency: 'TRY'
    }).format(amount);
}

// Format date
function formatDate(date) {
    return new Intl.DateTimeFormat('tr-TR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    }).format(new Date(date));
}

// Confirm dialog helper
function confirmAction(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// Copy to clipboard
async function copyToClipboard(text) {
    try {
        await navigator.clipboard.writeText(text);
        showNotification('Panoya kopyalandı', 'success');
    } catch (err) {
        console.error('Kopyalama hatası:', err);
    }
}

// Show notification (can be replaced with PrimeFaces growl)
function showNotification(message, type = 'info') {
    console.log(`[${type.toUpperCase()}] ${message}`);
    // Integrate with PrimeFaces growl if available
}

// Print current page
function printPage() {
    window.print();
}

// Export data (placeholder)
function exportData(format = 'excel') {
    console.log('Exporting data as:', format);
    // Implement actual export logic
}

// Theme switcher (if needed)
function toggleTheme() {
    const body = document.body;
    body.classList.toggle('dark-theme');
    const isDark = body.classList.contains('dark-theme');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
}

// Initialize theme on load
document.addEventListener('DOMContentLoaded', function() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
    }
    
    // Dialog scroll control
    initDialogScrollControl();
});

// Dialog scroll control için observer
function initDialogScrollControl() {
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            if (mutation.type === 'childList') {
                const dialog = document.querySelector('.ui-dialog:not(.ui-helper-hidden)');
                const overlay = document.querySelector('.ui-widget-overlay');
                
                if (dialog && overlay) {
                    // Dialog açık - body scroll'unu engelle
                    document.body.style.overflow = 'hidden';
                    document.body.style.position = 'fixed';
                    document.body.style.width = '100%';
                    document.body.style.height = '100%';
                } else {
                    // Dialog kapalı - body scroll'unu restore et
                    document.body.style.overflow = '';
                    document.body.style.position = '';
                    document.body.style.width = '';
                    document.body.style.height = '';
                }
            }
        });
    });
    
    observer.observe(document.body, {
        childList: true,
        subtree: true
    });
}

// Dialog açıldığında body scroll'unu kilitle
function lockBodyScroll() {
    document.body.style.overflow = 'hidden';
    document.body.style.position = 'fixed';
    document.body.style.width = '100%';
    document.body.style.height = '100%';
    document.body.style.top = '-' + window.scrollY + 'px';
}

// Dialog kapandığında body scroll'unu restore et
function unlockBodyScroll() {
    const scrollY = document.body.style.top;
    document.body.style.overflow = '';
    document.body.style.position = '';
    document.body.style.width = '';
    document.body.style.height = '';
    document.body.style.top = '';
    
    if (scrollY) {
        window.scrollTo(0, parseInt(scrollY || '0') * -1);
    }
}

// Dialog açıldığında arama input'unu temizle
function aramaTemizle() {
    setTimeout(function() {
        const aramaInput = document.querySelector('#tumMuracaatlarDialog\\:aramaInput_input');
        if (aramaInput) {
            aramaInput.value = '';
        }
        // Alternative selector
        const aramaInput2 = document.querySelector('input[id*="aramaInput"]');
        if (aramaInput2) {
            aramaInput2.value = '';
        }
    }, 100);
}

// Müracaat raporu indirme
function downloadMuracaatRapor(muracaatId) {
    // Form oluştur ve submit et
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/sais/pages/muracaat.xhtml';
    form.style.display = 'none';
    
    // ViewState ekle
    const viewState = document.querySelector('input[name="javax.faces.ViewState"]');
    if (viewState) {
        const viewStateInput = document.createElement('input');
        viewStateInput.type = 'hidden';
        viewStateInput.name = 'javax.faces.ViewState';
        viewStateInput.value = viewState.value;
        form.appendChild(viewStateInput);
    }
    
    // Müracaat ID parametresi
    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'muracaatId';
    idInput.value = muracaatId;
    form.appendChild(idInput);
    
    // Action parametresi
    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'raporAction';
    actionInput.value = 'downloadRapor';
    form.appendChild(actionInput);
    
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}

