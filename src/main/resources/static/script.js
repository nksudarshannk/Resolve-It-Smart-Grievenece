function login() {
    let user = document.getElementById("username").value.trim();
    let pass = document.getElementById("password").value.trim();

    if (user === "admin" && pass === "1234") {
        window.location.href = "user-portal.html";
    } else {
        alert("Invalid username or password");
    }
}

// toggle anonymous/public
document.addEventListener("DOMContentLoaded", () => {
    const radios = document.getElementsByName("type");
    const publicSection = document.getElementById("publicSection");

    if (radios) {
        radios.forEach(radio => {
            radio.addEventListener("change", () => {
                publicSection.style.display = radio.value === "anonymous" ? "none" : "block";
            });
        });
    }
});

function submitComplaint() {
    let subject = document.getElementById("subject").value.trim();
    let desc = document.getElementById("description").value.trim();

    if (!subject || !desc) {
        alert("Please fill subject and description");
        return;
    }

    // Gather form data
    let isAnonymous = document.querySelector('input[name="type"]:checked').value === "anonymous";
    
    let complaintData = {
        name: isAnonymous ? "Anonymous" : document.getElementById("name").value.trim(),
        email: isAnonymous ? "" : document.getElementById("email").value.trim(),
        category: document.getElementById("category").value,
        urgency: document.getElementById("urgency").value,
        subject: subject,
        description: desc,
        anonymous: isAnonymous
    };

    // Submit to backend API
    fetch('/api/complaints', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(complaintData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert("✅ Complaint Submitted Successfully!\n\nYour Complaint ID: " + data.complaintId + "\n\nPlease save this ID to track your complaint.");
            window.location.href = "home.html";
        } else {
            alert("❌ Error: " + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("❌ Failed to submit complaint. Please try again.");
    });
}
