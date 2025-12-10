// ========================================
// Enhanced Admin Dashboard JavaScript
// ========================================

const API_BASE = 'http://localhost:8080/api';

// Current state
let currentSection = 'dashboard';
let currentComplaintsList = [];
let currentEscalationsList = [];
let currentAdmin = null;

// ========================================
// Session Management
// ========================================
function checkSession() {
    const sessionData = sessionStorage.getItem('adminSession');
    
    if (!sessionData) {
        // No session, redirect to login
        window.location.href = 'admin-login.html';
        return false;
    }
    
    try {
        currentAdmin = JSON.parse(sessionData);
        
        // Check if session is expired (24 hours)
        const loginTime = new Date(currentAdmin.loginTime);
        const now = new Date();
        const hoursDiff = (now - loginTime) / (1000 * 60 * 60);
        
        if (hoursDiff > 24) {
            sessionStorage.removeItem('adminSession');
            window.location.href = 'admin-login.html';
            return false;
        }
        
        // Update UI with admin info
        document.getElementById('adminName').textContent = currentAdmin.fullName;
        document.getElementById('adminRole').textContent = currentAdmin.role;
        
        return true;
        
    } catch (error) {
        console.error('Session error:', error);
        sessionStorage.removeItem('adminSession');
        window.location.href = 'admin-login.html';
        return false;
    }
}

function logout() {
    if (confirm('Are you sure you want to logout?')) {
        sessionStorage.removeItem('adminSession');
        showNotification('Logged out successfully', 'success');
        setTimeout(() => {
            window.location.href = 'admin-login.html';
        }, 1000);
    }
}

// ========================================
// Navigation
// ========================================
function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Remove active class from all nav items
    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });
    
    // Show selected section
    const section = document.getElementById(sectionId + '-section');
    if (section) {
        section.classList.add('active');
    }
    
    // Add active class to corresponding nav item
    const navItem = document.querySelector(`[data-section="${sectionId}"]`);
    if (navItem) {
        navItem.classList.add('active');
    }
    
    currentSection = sectionId;
    
    // Load section data
    loadSectionData(sectionId);
}

function loadSectionData(sectionId) {
    switch(sectionId) {
        case 'dashboard':
            loadDashboardStats();
            loadRecentComplaints();
            break;
        case 'complaints':
            loadAllComplaints();
            break;
        case 'assignments':
            loadAssignments();
            loadAdmins();
            break;
        case 'escalations':
            loadEscalations();
            break;
        case 'communications':
            loadCommunications();
            break;
        case 'reports':
            loadReports();
            break;
        case 'export':
            // Export section is static, no data to load
            break;
    }
}

// ========================================
// Dashboard Section
// ========================================
async function loadDashboardStats() {
    try {
        const response = await fetch(`${API_BASE}/reports/dashboard`);
        const data = await response.json();
        
        // Update stats cards
        const totalElement = document.getElementById('totalComplaints');
        const underReviewElement = document.getElementById('underReviewCount');
        const resolvedElement = document.getElementById('resolvedCount');
        const escalatedElement = document.getElementById('escalatedCount');
        
        if (totalElement) totalElement.textContent = data.totalComplaints || 0;
        if (underReviewElement) underReviewElement.textContent = data.statusDistribution?.UNDER_REVIEW || 0;
        if (resolvedElement) resolvedElement.textContent = data.statusDistribution?.RESOLVED || 0;
        if (escalatedElement) escalatedElement.textContent = data.escalatedCount || 0;
        
        // You can add chart rendering here using Chart.js
        // renderStatusChart(data.statusDistribution);
        
    } catch (error) {
        console.error('Error loading dashboard stats:', error);
        showNotification('Error loading dashboard statistics', 'error');
    }
}

async function loadRecentComplaints() {
    try {
        const response = await fetch(`${API_BASE}/complaints`);
        const complaints = await response.json();
        
        // Show only recent 10
        const recentComplaints = complaints.slice(0, 10);
        
        const tbody = document.querySelector('#recent-complaints-table tbody');
        tbody.innerHTML = '';
        
        if (recentComplaints.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 30px; color: #999;">No complaints found. Submit a complaint to get started.</td></tr>';
            return;
        }
        
        recentComplaints.forEach(complaint => {
            const row = createComplaintRow(complaint, false);
            tbody.appendChild(row);
        });
        
    } catch (error) {
        console.error('Error loading recent complaints:', error);
        const tbody = document.querySelector('#recent-complaints-table tbody');
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 30px; color: #f44336;">Error loading data. Please refresh the page.</td></tr>';
    }
}

function refreshData() {
    loadSectionData(currentSection);
    showNotification('Data refreshed successfully', 'success');
}

// ========================================
// Complaints Section
// ========================================
async function loadAllComplaints() {
    try {
        const response = await fetch(`${API_BASE}/complaints`);
        currentComplaintsList = await response.json();
        
        renderComplaintsTable(currentComplaintsList);
        
    } catch (error) {
        console.error('Error loading complaints:', error);
        showNotification('Error loading complaints', 'error');
    }
}

function renderComplaintsTable(complaints) {
    const tbody = document.querySelector('#complaints-table tbody');
    tbody.innerHTML = '';
    
    if (complaints.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 20px;">No complaints found</td></tr>';
        return;
    }
    
    complaints.forEach(complaint => {
        const row = createComplaintRow(complaint, true);
        tbody.appendChild(row);
    });
}

function createComplaintRow(complaint, includeActions = false) {
    const row = document.createElement('tr');
    
    const statusClass = getStatusClass(complaint.status);
    const categoryClass = getCategoryClass(complaint.category);
    const urgencyClass = getUrgencyClass(complaint.urgency);
    
    row.innerHTML = `
        <td>#${complaint.id}</td>
        <td>${complaint.category}</td>
        <td><span class="badge ${statusClass}">${complaint.status}</span></td>
        <td><span class="badge ${urgencyClass}">${complaint.urgency}</span></td>
        <td>${complaint.assignedTo ? complaint.assignedTo.fullName : 'Unassigned'}</td>
        <td>${formatDate(complaint.createdAt)}</td>
        ${includeActions ? `
            <td>
                <button class="btn-action" onclick="viewComplaintDetails(${complaint.id})">View</button>
                <button class="btn-action" onclick="openAssignModal(${complaint.id})">Assign</button>
                <button class="btn-action" onclick="openStatusModal(${complaint.id})">Update</button>
            </td>
        ` : ''}
    `;
    
    return row;
}

function filterComplaints() {
    const statusFilter = document.getElementById('statusFilter');
    const categoryFilter = document.getElementById('categoryFilter');
    
    let filtered = currentComplaintsList;
    
    if (statusFilter && statusFilter.value && statusFilter.value !== 'all') {
        filtered = filtered.filter(c => c.status === statusFilter.value);
    }
    
    if (categoryFilter && categoryFilter.value && categoryFilter.value !== 'all') {
        filtered = filtered.filter(c => c.category === categoryFilter.value);
    }
    
    renderComplaintsTable(filtered);
}

async function viewComplaintDetails(complaintId) {
    try {
        const response = await fetch(`${API_BASE}/complaints/${complaintId}`);
        const complaint = await response.json();
        
        // Load related data
        const notesResponse = await fetch(`${API_BASE}/communication/notes/${complaintId}`);
        const notes = await notesResponse.json();
        
        const repliesResponse = await fetch(`${API_BASE}/communication/replies/${complaintId}`);
        const replies = await repliesResponse.json();
        
        // Show modal with details
        showComplaintDetailsModal(complaint, notes, replies);
        
    } catch (error) {
        console.error('Error loading complaint details:', error);
        showNotification('Error loading complaint details', 'error');
    }
}

function showComplaintDetailsModal(complaint, notes, replies) {
    let detailsHTML = `
        <div class="modal-content" style="max-width: 800px; max-height: 80vh; overflow-y: auto;">
            <div class="modal-header">
                <h2>Complaint #${complaint.id} Details</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <h3>Basic Information</h3>
                <p><strong>Name:</strong> ${complaint.name}</p>
                <p><strong>Email:</strong> ${complaint.email}</p>
                <p><strong>Phone:</strong> ${complaint.phone}</p>
                <p><strong>Category:</strong> ${complaint.category}</p>
                <p><strong>Status:</strong> <span class="badge ${getStatusClass(complaint.status)}">${complaint.status}</span></p>
                <p><strong>Urgency:</strong> <span class="badge ${getUrgencyClass(complaint.urgency)}">${complaint.urgency}</span></p>
                <p><strong>Priority:</strong> ${complaint.priority || 'Not set'}</p>
                <p><strong>Assigned To:</strong> ${complaint.assignedTo ? complaint.assignedTo.fullName : 'Unassigned'}</p>
                <p><strong>Created:</strong> ${formatDate(complaint.createdAt)}</p>
                
                <h3>Description</h3>
                <p>${complaint.description}</p>
                
                ${complaint.isEscalated ? `
                    <h3>Escalation Info</h3>
                    <p style="color: #f44336;">⚠️ This complaint has been escalated</p>
                    <p><strong>Escalated At:</strong> ${formatDate(complaint.escalatedAt)}</p>
                    <p><strong>Days Unresolved:</strong> ${complaint.daysUnresolved}</p>
                ` : ''}
                
                <h3>Internal Notes (${notes.length})</h3>
                <div class="notes-list">
                    ${notes.length > 0 ? notes.map(note => `
                        <div class="note-item">
                            <p><strong>${note.createdBy}</strong> - ${formatDate(note.createdAt)}</p>
                            <p>${note.note}</p>
                        </div>
                    `).join('') : '<p>No internal notes</p>'}
                </div>
                
                <h3>User Replies (${replies.length})</h3>
                <div class="replies-list">
                    ${replies.length > 0 ? replies.map(reply => `
                        <div class="reply-item">
                            <p><strong>${reply.repliedBy}</strong> - ${formatDate(reply.repliedAt)}</p>
                            <p>${reply.message}</p>
                            <p><em>${reply.sentToUser ? '✓ Sent to user' : '✗ Not sent'}</em></p>
                        </div>
                    `).join('') : '<p>No replies sent</p>'}
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = detailsHTML;
    modal.style.display = 'flex';
}

// ========================================
// Assignments Section
// ========================================
let adminsList = [];

async function loadAdmins() {
    try {
        const response = await fetch(`${API_BASE}/admins/active`);
        adminsList = await response.json();
    } catch (error) {
        console.error('Error loading admins:', error);
    }
}

async function loadAssignments() {
    await loadAllComplaints();
    await loadAdmins();
    
    // Populate admin select in assignment table
    const adminSelect = document.getElementById('assign-admin-select');
    if (adminSelect) {
        adminSelect.innerHTML = '<option value="">Select Admin...</option>' +
            adminsList.map(admin => `<option value="${admin.id}">${admin.fullName} (${admin.role})</option>`).join('');
    }
}

function openAssignModal(complaintId) {
    const complaint = currentComplaintsList.find(c => c.id === complaintId);
    if (!complaint) return;
    
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Assign Complaint #${complaintId}</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <p><strong>Category:</strong> ${complaint.category}</p>
                <p><strong>Urgency:</strong> ${complaint.urgency}</p>
                <p><strong>Description:</strong> ${complaint.description}</p>
                
                <div class="form-group">
                    <label>Assign To:</label>
                    <select id="assign-admin-id" class="form-control">
                        <option value="">Select Admin...</option>
                        ${adminsList.map(admin => `
                            <option value="${admin.id}" ${complaint.assignedTo && complaint.assignedTo.id === admin.id ? 'selected' : ''}>
                                ${admin.fullName} (${admin.role} - ${admin.department})
                            </option>
                        `).join('')}
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Priority (1-5):</label>
                    <input type="number" id="assign-priority" min="1" max="5" value="${complaint.priority || 3}" class="form-control">
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitAssignment(${complaintId})">Assign</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitAssignment(complaintId) {
    const adminId = document.getElementById('assign-admin-id').value;
    const priority = document.getElementById('assign-priority').value;
    
    if (!adminId) {
        showNotification('Please select an admin', 'error');
        return;
    }
    
    try {
        // Assign complaint
        const assignResponse = await fetch(`${API_BASE}/complaints/${complaintId}/assign?adminId=${adminId}&assignedBy=system`, {
            method: 'PUT'
        });
        
        if (!assignResponse.ok) throw new Error('Assignment failed');
        
        // Update priority
        const priorityResponse = await fetch(`${API_BASE}/complaints/${complaintId}/priority?priority=${priority}&updatedBy=system`, {
            method: 'PUT'
        });
        
        if (!priorityResponse.ok) throw new Error('Priority update failed');
        
        showNotification('Complaint assigned successfully!', 'success');
        closeModal();
        loadSectionData(currentSection);
        
    } catch (error) {
        console.error('Error assigning complaint:', error);
        showNotification('Error assigning complaint', 'error');
    }
}

function openStatusModal(complaintId) {
    const complaint = currentComplaintsList.find(c => c.id === complaintId);
    if (!complaint) return;
    
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Update Status - Complaint #${complaintId}</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Current Status:</label>
                    <p><span class="badge ${getStatusClass(complaint.status)}">${complaint.status}</span></p>
                </div>
                
                <div class="form-group">
                    <label>New Status:</label>
                    <select id="new-status" class="form-control">
                        <option value="NEW" ${complaint.status === 'NEW' ? 'selected' : ''}>NEW</option>
                        <option value="UNDER_REVIEW" ${complaint.status === 'UNDER_REVIEW' ? 'selected' : ''}>UNDER REVIEW</option>
                        <option value="RESOLVED" ${complaint.status === 'RESOLVED' ? 'selected' : ''}>RESOLVED</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Admin Comment:</label>
                    <textarea id="status-comment" rows="3" class="form-control" placeholder="Add a comment about this status change..."></textarea>
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitStatusUpdate(${complaintId})">Update Status</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitStatusUpdate(complaintId) {
    const newStatus = document.getElementById('new-status').value;
    const comment = document.getElementById('status-comment').value;
    
    try {
        const response = await fetch(`${API_BASE}/complaints/${complaintId}/status?status=${newStatus}&adminComment=${encodeURIComponent(comment)}`, {
            method: 'PUT'
        });
        
        if (!response.ok) throw new Error('Status update failed');
        
        showNotification('Status updated successfully!', 'success');
        closeModal();
        loadSectionData(currentSection);
        
    } catch (error) {
        console.error('Error updating status:', error);
        showNotification('Error updating status', 'error');
    }
}

// ========================================
// Escalations Section
// ========================================
async function loadEscalations() {
    try {
        const response = await fetch(`${API_BASE}/escalations`);
        currentEscalationsList = await response.json();
        
        renderEscalationsTable(currentEscalationsList);
        
    } catch (error) {
        console.error('Error loading escalations:', error);
        showNotification('Error loading escalations', 'error');
    }
}

function renderEscalationsTable(escalations) {
    const tbody = document.querySelector('#escalations-table tbody');
    tbody.innerHTML = '';
    
    if (escalations.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 20px;">No escalations found</td></tr>';
        return;
    }
    
    escalations.forEach(escalation => {
        const row = document.createElement('tr');
        
        const statusClass = escalation.status === 'RESOLVED' ? 'status-resolved' : 
                           escalation.status === 'IN_PROGRESS' ? 'status-under-review' : 'status-new';
        
        row.innerHTML = `
            <td>#${escalation.id}</td>
            <td>#${escalation.complaint.id}</td>
            <td>${escalation.reason}</td>
            <td>${escalation.escalatedBy}</td>
            <td>${escalation.escalatedTo}</td>
            <td><span class="badge ${statusClass}">${escalation.status}</span></td>
            <td>
                <button class="btn-action" onclick="updateEscalationStatus(${escalation.id})">Update</button>
            </td>
        `;
        
        tbody.appendChild(row);
    });
}

async function openEscalateModal() {
    await loadAllComplaints();
    
    // Filter unresolved complaints
    const unresolvedComplaints = currentComplaintsList.filter(c => c.status !== 'RESOLVED');
    
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Escalate Complaint</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Select Complaint:</label>
                    <select id="escalate-complaint-id" class="form-control">
                        <option value="">Select complaint...</option>
                        ${unresolvedComplaints.map(c => `
                            <option value="${c.id}">
                                #${c.id} - ${c.category} (${c.status}) ${c.isEscalated ? '[Already Escalated]' : ''}
                            </option>
                        `).join('')}
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Escalate To:</label>
                    <select id="escalate-to" class="form-control">
                        <option value="">Select senior admin...</option>
                        ${adminsList.filter(a => a.role === 'SENIOR_ADMIN').map(admin => `
                            <option value="${admin.username}">${admin.fullName}</option>
                        `).join('')}
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Reason:</label>
                    <textarea id="escalate-reason" rows="3" class="form-control" placeholder="Reason for escalation..."></textarea>
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitEscalation()">Escalate</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitEscalation() {
    const complaintId = document.getElementById('escalate-complaint-id').value;
    const escalatedTo = document.getElementById('escalate-to').value;
    const reason = document.getElementById('escalate-reason').value;
    
    if (!complaintId || !escalatedTo || !reason) {
        showNotification('Please fill all fields', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/escalations`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                complaintId: parseInt(complaintId),
                reason: reason,
                escalatedBy: 'admin',
                escalatedTo: escalatedTo
            })
        });
        
        if (!response.ok) throw new Error('Escalation failed');
        
        showNotification('Complaint escalated successfully!', 'success');
        closeModal();
        loadEscalations();
        
    } catch (error) {
        console.error('Error escalating complaint:', error);
        showNotification('Error escalating complaint', 'error');
    }
}

function updateEscalationStatus(escalationId) {
    const escalation = currentEscalationsList.find(e => e.id === escalationId);
    if (!escalation) return;
    
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Update Escalation #${escalationId}</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <p><strong>Complaint ID:</strong> #${escalation.complaint.id}</p>
                <p><strong>Reason:</strong> ${escalation.reason}</p>
                <p><strong>Current Status:</strong> ${escalation.status}</p>
                
                <div class="form-group">
                    <label>New Status:</label>
                    <select id="escalation-status" class="form-control">
                        <option value="PENDING" ${escalation.status === 'PENDING' ? 'selected' : ''}>PENDING</option>
                        <option value="IN_PROGRESS" ${escalation.status === 'IN_PROGRESS' ? 'selected' : ''}>IN PROGRESS</option>
                        <option value="RESOLVED" ${escalation.status === 'RESOLVED' ? 'selected' : ''}>RESOLVED</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Resolution Notes:</label>
                    <textarea id="escalation-resolution" rows="3" class="form-control" placeholder="Resolution details...">${escalation.resolution || ''}</textarea>
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitEscalationUpdate(${escalationId})">Update</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitEscalationUpdate(escalationId) {
    const status = document.getElementById('escalation-status').value;
    const resolution = document.getElementById('escalation-resolution').value;
    
    try {
        const response = await fetch(`${API_BASE}/escalations/${escalationId}?status=${status}&resolution=${encodeURIComponent(resolution)}`, {
            method: 'PUT'
        });
        
        if (!response.ok) throw new Error('Update failed');
        
        showNotification('Escalation updated successfully!', 'success');
        closeModal();
        loadEscalations();
        
    } catch (error) {
        console.error('Error updating escalation:', error);
        showNotification('Error updating escalation', 'error');
    }
}

async function autoEscalate() {
    const daysThreshold = prompt('Enter days threshold for auto-escalation:', '7');
    if (!daysThreshold) return;
    
    const escalatedTo = prompt('Enter senior admin username to escalate to:');
    if (!escalatedTo) return;
    
    try {
        const response = await fetch(`${API_BASE}/escalations/auto-escalate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                daysThreshold: parseInt(daysThreshold),
                escalatedTo: escalatedTo
            })
        });
        
        const result = await response.json();
        showNotification(`Auto-escalated ${result.length} complaints`, 'success');
        loadEscalations();
        
    } catch (error) {
        console.error('Error auto-escalating:', error);
        showNotification('Error in auto-escalation', 'error');
    }
}

// ========================================
// Communications Section
// ========================================
async function loadCommunications() {
    await loadAllComplaints();
}

function openNoteModal() {
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Add Internal Note</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Select Complaint:</label>
                    <select id="note-complaint-id" class="form-control">
                        <option value="">Select complaint...</option>
                        ${currentComplaintsList.map(c => `
                            <option value="${c.id}">#${c.id} - ${c.category}</option>
                        `).join('')}
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Internal Note (Private):</label>
                    <textarea id="note-content" rows="4" class="form-control" placeholder="Add private note visible only to staff..." maxlength="2000"></textarea>
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitNote()">Add Note</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitNote() {
    const complaintId = document.getElementById('note-complaint-id').value;
    const note = document.getElementById('note-content').value;
    
    if (!complaintId || !note) {
        showNotification('Please fill all fields', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/communication/notes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                complaintId: parseInt(complaintId),
                note: note,
                createdBy: 'admin'
            })
        });
        
        if (!response.ok) throw new Error('Failed to add note');
        
        showNotification('Internal note added successfully!', 'success');
        closeModal();
        
    } catch (error) {
        console.error('Error adding note:', error);
        showNotification('Error adding note', 'error');
    }
}

function openReplyModal() {
    let modalHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>Send Reply to User</h2>
                <button class="close-btn" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Select Complaint:</label>
                    <select id="reply-complaint-id" class="form-control">
                        <option value="">Select complaint...</option>
                        ${currentComplaintsList.map(c => `
                            <option value="${c.id}">#${c.id} - ${c.name} (${c.email})</option>
                        `).join('')}
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Reply Message (Public):</label>
                    <textarea id="reply-message" rows="4" class="form-control" placeholder="This message will be sent to the user..." maxlength="2000"></textarea>
                </div>
                
                <div class="modal-actions">
                    <button class="btn-primary" onclick="submitReply()">Send Reply</button>
                    <button class="btn-secondary" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
    `;
    
    const modal = document.getElementById('action-modal');
    modal.innerHTML = modalHTML;
    modal.style.display = 'flex';
}

async function submitReply() {
    const complaintId = document.getElementById('reply-complaint-id').value;
    const message = document.getElementById('reply-message').value;
    
    if (!complaintId || !message) {
        showNotification('Please fill all fields', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/communication/replies`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                complaintId: parseInt(complaintId),
                message: message,
                repliedBy: 'support_team'
            })
        });
        
        if (!response.ok) throw new Error('Failed to send reply');
        
        showNotification('Reply sent successfully!', 'success');
        closeModal();
        
    } catch (error) {
        console.error('Error sending reply:', error);
        showNotification('Error sending reply', 'error');
    }
}

// ========================================
// Reports Section
// ========================================
async function loadReports() {
    try {
        // Load dashboard stats
        const dashboardResponse = await fetch(`${API_BASE}/reports/dashboard`);
        const dashboardData = await dashboardResponse.json();
        
        // Load performance metrics
        const performanceResponse = await fetch(`${API_BASE}/reports/performance`);
        const performanceData = await performanceResponse.json();
        
        // Display stats
        displayReportStats(dashboardData, performanceData);
        
        // Load trend data
        const trendResponse = await fetch(`${API_BASE}/reports/trends?days=30`);
        const trendData = await trendResponse.json();
        
        // You can render charts here using Chart.js
        // renderTrendChart(trendData);
        
    } catch (error) {
        console.error('Error loading reports:', error);
        showNotification('Error loading reports', 'error');
    }
}

function displayReportStats(dashboard, performance) {
    // Update main report metrics
    document.getElementById('report-total').textContent = dashboard.totalComplaints || 0;
    document.getElementById('report-avg-time').textContent = performance.avgResolutionTime ? performance.avgResolutionTime.toFixed(1) + ' days' : '0 days';
    document.getElementById('report-resolution-rate').textContent = performance.resolutionRate ? performance.resolutionRate.toFixed(0) + '%' : '0%';
    document.getElementById('report-escalation-rate').textContent = performance.escalationRate ? performance.escalationRate.toFixed(0) + '%' : '0%';
    
    // Display trend data
    const trendElement = document.getElementById('trendData');
    if (trendElement && dashboard.statusDistribution) {
        trendElement.innerHTML = `
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 15px; margin-top: 15px;">
                <div style="padding: 15px; background: white; border-radius: 8px; text-align: center;">
                    <div style="font-size: 24px; font-weight: bold; color: #2196f3;">${dashboard.statusDistribution.NEW || 0}</div>
                    <div style="font-size: 13px; color: #666; margin-top: 5px;">New Complaints</div>
                </div>
                <div style="padding: 15px; background: white; border-radius: 8px; text-align: center;">
                    <div style="font-size: 24px; font-weight: bold; color: #ff9800;">${dashboard.statusDistribution.UNDER_REVIEW || 0}</div>
                    <div style="font-size: 13px; color: #666; margin-top: 5px;">Under Review</div>
                </div>
                <div style="padding: 15px; background: white; border-radius: 8px; text-align: center;">
                    <div style="font-size: 24px; font-weight: bold; color: #4caf50;">${dashboard.statusDistribution.RESOLVED || 0}</div>
                    <div style="font-size: 13px; color: #666; margin-top: 5px;">Resolved</div>
                </div>
            </div>
        `;
    }
}

// ========================================
// Export Section
// ========================================
async function exportData() {
    const formatElement = document.getElementById('export-format');
    const filterElement = document.getElementById('export-filter');
    
    if (!formatElement || !filterElement) {
        showNotification('Export form not found', 'error');
        return;
    }
    
    const format = formatElement.value;
    const filterType = filterElement.value;
    
    try {
        let url = `${API_BASE}/reports/export/${format}`;
        
        if (filterType && filterType !== 'all') {
            url += `?filterType=${filterType}`;
        }
        
        showNotification('Preparing export... Please wait', 'info');
        
        const response = await fetch(url);
        
        if (!response.ok) throw new Error('Export failed');
        
        if (format === 'csv') {
            const blob = await response.blob();
            const downloadUrl = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = downloadUrl;
            a.download = `complaints_export_${new Date().toISOString().split('T')[0]}.csv`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(downloadUrl);
            a.remove();
        } else {
            const data = await response.json();
            const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
            const downloadUrl = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = downloadUrl;
            a.download = `complaints_export_${new Date().toISOString().split('T')[0]}.json`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(downloadUrl);
            a.remove();
        }
        
        showNotification('✅ Data exported successfully!', 'success');
        
    } catch (error) {
        console.error('Error exporting data:', error);
        showNotification('❌ Error exporting data. Please try again.', 'error');
    }
}

// ========================================
// Utility Functions
// ========================================
function getStatusClass(status) {
    switch(status) {
        case 'NEW': return 'status-new';
        case 'UNDER_REVIEW': return 'status-under-review';
        case 'RESOLVED': return 'status-resolved';
        default: return '';
    }
}

function getCategoryClass(category) {
    const classes = {
        'Water Supply': 'category-water',
        'Electricity': 'category-electricity',
        'Road': 'category-road',
        'Garbage': 'category-garbage',
        'Other': 'category-other'
    };
    return classes[category] || '';
}

function getUrgencyClass(urgency) {
    switch(urgency) {
        case 'High': return 'urgency-high';
        case 'Medium': return 'urgency-medium';
        case 'Low': return 'urgency-low';
        default: return '';
    }
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function closeModal() {
    const modal = document.getElementById('action-modal');
    if (modal) {
        modal.style.display = 'none';
        modal.innerHTML = '';
    }
}

function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        background: ${type === 'success' ? '#4caf50' : type === 'error' ? '#f44336' : '#2196f3'};
        color: white;
        border-radius: 5px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        z-index: 10000;
        animation: slideIn 0.3s ease;
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Add animation styles
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    @keyframes slideOut {
        from { transform: translateX(0); opacity: 1; }
        to { transform: translateX(100%); opacity: 0; }
    }
    
    .note-item, .reply-item {
        background: #f5f5f5;
        padding: 10px;
        margin: 10px 0;
        border-radius: 5px;
        border-left: 3px solid #667eea;
    }
    
    .notes-list, .replies-list {
        max-height: 300px;
        overflow-y: auto;
    }
`;
document.head.appendChild(style);

// ========================================
// Initialize on page load
// ========================================
document.addEventListener('DOMContentLoaded', function() {
    // Check session first
    if (!checkSession()) {
        return;
    }
    
    // Initialize navigation
    const navButtons = document.querySelectorAll('.nav-item');
    navButtons.forEach(button => {
        button.addEventListener('click', function() {
            const section = this.getAttribute('data-section');
            if (section) {
                showSection(section);
            }
        });
    });
    
    // Load initial dashboard
    showSection('dashboard');
});
