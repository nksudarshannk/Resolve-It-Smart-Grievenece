const API_BASE_URL = '/api/complaints';

let allComplaints = [];
let currentFilter = 'ALL';
let currentComplaintId = null;

// Load stats and complaints when page loads
document.addEventListener('DOMContentLoaded', () => {
    loadStats();
    loadComplaints();
});

// Load statistics
function loadStats() {
    fetch(`${API_BASE_URL}/stats`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('statNew').textContent = data.new || 0;
            document.getElementById('statReview').textContent = data.underReview || 0;
            document.getElementById('statResolved').textContent = data.resolved || 0;
            document.getElementById('statTotal').textContent = data.total || 0;
        })
        .catch(error => {
            console.error('Error loading stats:', error);
        });
}

// Load all complaints
function loadComplaints() {
    fetch(API_BASE_URL)
        .then(response => response.json())
        .then(data => {
            allComplaints = data;
            displayComplaints(allComplaints);
        })
        .catch(error => {
            console.error('Error loading complaints:', error);
            document.getElementById('complaintsTableBody').innerHTML = 
                '<tr><td colspan="7" style="text-align: center; color: red;">Error loading complaints</td></tr>';
        });
}

// Display complaints in table
function displayComplaints(complaints) {
    const tbody = document.getElementById('complaintsTableBody');
    
    if (complaints.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 40px;">No complaints found</td></tr>';
        return;
    }
    
    tbody.innerHTML = complaints.map(complaint => `
        <tr>
            <td><strong>${complaint.complaintId}</strong></td>
            <td>${complaint.subject}</td>
            <td>${complaint.category}</td>
            <td><span class="urgency-badge urgency-${complaint.urgency.toLowerCase()}">${complaint.urgency}</span></td>
            <td><span class="status-badge status-${complaint.status.toLowerCase()}">${formatStatus(complaint.status)}</span></td>
            <td>${formatDate(complaint.submittedAt)}</td>
            <td>
                <button onclick="viewComplaint(${complaint.id})" class="btn-action">View & Update</button>
            </td>
        </tr>
    `).join('');
}

// Filter complaints by status
function filterComplaints(status) {
    currentFilter = status;
    
    // Update active tab
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    if (status === 'ALL') {
        displayComplaints(allComplaints);
    } else {
        const filtered = allComplaints.filter(c => c.status === status);
        displayComplaints(filtered);
    }
}

// View complaint details
function viewComplaint(id) {
    currentComplaintId = id;
    
    fetch(`${API_BASE_URL}/${id}`)
        .then(response => response.json())
        .then(complaint => {
            // Populate modal
            document.getElementById('modalComplaintId').textContent = complaint.complaintId;
            document.getElementById('modalStatus').textContent = formatStatus(complaint.status);
            document.getElementById('modalStatus').className = `status-badge status-${complaint.status.toLowerCase()}`;
            document.getElementById('modalSubmitted').textContent = formatDateTime(complaint.submittedAt);
            document.getElementById('modalUpdated').textContent = formatDateTime(complaint.lastUpdatedAt);
            document.getElementById('modalName').textContent = complaint.anonymous ? 'Anonymous' : complaint.name;
            document.getElementById('modalEmail').textContent = complaint.anonymous ? 'N/A' : complaint.email;
            document.getElementById('modalCategory').textContent = complaint.category;
            document.getElementById('modalUrgency').textContent = complaint.urgency;
            document.getElementById('modalSubject').textContent = complaint.subject;
            document.getElementById('modalDescription').textContent = complaint.description;
            
            // Set current status in dropdown
            document.getElementById('newStatus').value = complaint.status;
            
            // Load timeline
            loadTimeline(id);
            
            // Show modal
            document.getElementById('complaintModal').style.display = 'block';
        })
        .catch(error => {
            console.error('Error loading complaint:', error);
            alert('Error loading complaint details');
        });
}

// Load timeline
function loadTimeline(complaintId) {
    fetch(`${API_BASE_URL}/${complaintId}/timeline`)
        .then(response => response.json())
        .then(updates => {
            const timeline = document.getElementById('timeline');
            
            if (updates.length === 0) {
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
        })
        .catch(error => {
            console.error('Error loading timeline:', error);
            document.getElementById('timeline').innerHTML = 
                '<p style="text-align: center; color: red;">Error loading timeline</p>';
        });
}

// Update complaint status
function updateStatus() {
    const newStatus = document.getElementById('newStatus').value;
    const comment = document.getElementById('adminComment').value.trim();
    
    if (!comment) {
        alert('Please add a comment about this status change');
        return;
    }
    
    const statusUpdate = {
        status: newStatus,
        comment: comment,
        adminUsername: 'admin'
    };
    
    fetch(`${API_BASE_URL}/${currentComplaintId}/status`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(statusUpdate)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('✅ Status updated successfully!');
            document.getElementById('adminComment').value = '';
            
            // Reload data
            loadStats();
            loadComplaints();
            viewComplaint(currentComplaintId);
        } else {
            alert('❌ Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error updating status:', error);
        alert('❌ Failed to update status. Please try again.');
    });
}

// Close modal
function closeModal() {
    document.getElementById('complaintModal').style.display = 'none';
    currentComplaintId = null;
}

// Utility functions
function formatStatus(status) {
    return status.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString();
}

function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('complaintModal');
    if (event.target === modal) {
        closeModal();
    }
}
