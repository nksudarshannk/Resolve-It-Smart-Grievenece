const API_BASE_URL = '/api/complaints';

// Track complaint by ID
function trackComplaint() {
    const trackingId = document.getElementById('trackingId').value.trim();
    
    if (!trackingId) {
        alert('Please enter a Complaint ID');
        return;
    }
    
    // Hide previous results
    document.getElementById('trackResult').style.display = 'none';
    document.getElementById('trackError').style.display = 'none';
    
    fetch(`${API_BASE_URL}/track/${trackingId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Complaint not found');
            }
            return response.json();
        })
        .then(data => {
            displayComplaintDetails(data.complaint);
            displayTimeline(data.statusUpdates);
            updateStatusFlow(data.complaint.status);
            
            document.getElementById('trackResult').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('trackError').style.display = 'block';
        });
}

// Display complaint details
function displayComplaintDetails(complaint) {
    document.getElementById('trackComplaintId').textContent = complaint.complaintId;
    document.getElementById('trackStatus').textContent = formatStatus(complaint.status);
    document.getElementById('trackStatus').className = `status-badge status-${complaint.status.toLowerCase()}`;
    document.getElementById('trackSubject').textContent = complaint.subject;
    document.getElementById('trackCategory').textContent = complaint.category;
    document.getElementById('trackUrgency').textContent = complaint.urgency;
    document.getElementById('trackSubmitted').textContent = formatDateTime(complaint.submittedAt);
    document.getElementById('trackDescription').textContent = complaint.description;
}

// Display timeline
function displayTimeline(updates) {
    const timeline = document.getElementById('trackTimeline');
    
    if (!updates || updates.length === 0) {
        timeline.innerHTML = '<p style="text-align: center; color: #888;">No status updates yet</p>';
        return;
    }
    
    timeline.innerHTML = updates.map(update => `
        <div class="timeline-item">
            <div class="timeline-marker"></div>
            <div class="timeline-content">
                <div class="timeline-header">
                    <strong>${formatStatus(update.toStatus)}</strong>
                    <span class="timeline-date">${formatDateTime(update.timestamp)}</span>
                </div>
                ${update.fromStatus ? `<p class="status-change">Changed from: ${formatStatus(update.fromStatus)}</p>` : ''}
                ${update.adminComment ? `<p class="admin-comment">💬 ${update.adminComment}</p>` : ''}
                <p class="updated-by">Updated by: ${update.updatedBy}</p>
            </div>
        </div>
    `).join('');
}

// Update status flow visualization
function updateStatusFlow(currentStatus) {
    // Reset all steps
    document.querySelectorAll('.flow-step').forEach(step => {
        step.classList.remove('active', 'completed');
    });
    
    // Determine which steps to highlight
    const statusOrder = ['NEW', 'UNDER_REVIEW', 'RESOLVED'];
    const currentIndex = statusOrder.indexOf(currentStatus);
    
    statusOrder.forEach((status, index) => {
        const step = document.getElementById(`step-${status}`);
        if (index < currentIndex) {
            step.classList.add('completed');
        } else if (index === currentIndex) {
            step.classList.add('active');
        }
    });
}

// Utility functions
function formatStatus(status) {
    return status.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
}

// Allow Enter key to trigger search
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('trackingId').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            trackComplaint();
        }
    });
});
