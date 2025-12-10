# 🚀 Quick Start Guide - ResolveIT Platform

## 📋 Prerequisites
- Java 25 installed
- Maven installed (comes with project as `mvnw`)
- Your favorite web browser

## ▶️ Running the Application

### Windows:
```cmd
cd "c:\Users\Sudharshan N K\Downloads\demo (1)\demo"
mvnw.cmd spring-boot:run
```

### Mac/Linux:
```bash
cd "/path/to/demo"
./mvnw spring-boot:run
```

## 🌐 Access URLs

Once the application starts (look for "Started DemoApplication" in console):

### 🏠 Landing Page
**URL:** http://localhost:8080/home.html
- Overview of ResolveIT platform
- Learn about features and benefits
- Quick access to all portals

### 👤 User Portal (NEW!)
**URL:** http://localhost:8080/user-portal.html
- **Two submission modes:**
  - 🕶️ **Anonymous:** No registration needed, submit privately
  - 👤 **Registered User:** Login/Register for full tracking
- **Features:**
  - Complete complaint form with category, urgency, description
  - File upload support (images, documents up to 10MB)
  - Unique complaint ID generation
  - Success confirmation

### 🔍 Track Complaint (NEW!)
**URL:** http://localhost:8080/track-complaint.html
- Enter your complaint ID (e.g., CMP-2025-001)
- View complete complaint details
- See real-time status updates
- **Visual timeline** showing all status changes
- View uploaded media files
- Track resolution progress

### 🛡️ Admin Dashboard
**URL:** http://localhost:8080/admin-login.html
- Full complaint management system
- 7 functional modules

## 🎯 Test Demo Accounts

### Regular Users (for user-portal.html):
Create your own by clicking "Register" or test anonymously!

### Admin Accounts (for admin-login.html):
| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| staff | staff123 | STAFF |
| senior | senior123 | SENIOR_ADMIN |

## 📝 Testing the Complete Flow

### As a User:
1. Visit `http://localhost:8080/user-portal.html`
2. Click **"Anonymous"** mode (easiest for testing)
3. Fill in the complaint form:
   - Category: Infrastructure
   - Urgency: High
   - Subject: "Test Complaint"
   - Description: "This is a test complaint"
4. Optionally upload a file (image/document)
5. Click **"Submit Complaint"**
6. **Copy the Complaint ID** shown (e.g., CMP-2025-001)
7. Visit `http://localhost:8080/track-complaint.html`
8. Enter your complaint ID
9. View the status and timeline!

### As an Admin:
1. Visit `http://localhost:8080/admin-login.html`
2. Login with: `admin` / `admin123`
3. You'll see the dashboard with statistics
4. Click **"All Complaints"** in sidebar
5. Find your test complaint
6. Click actions to:
   - Update status
   - Assign to staff
   - Add internal notes
   - Send user replies
   - Change priority

## 🎨 All Available Pages

### Public Pages:
- `home.html` - Landing page ⭐ START HERE
- `user-portal.html` - Submit complaints (NEW!) ⭐
- `track-complaint.html` - Track status (NEW!) ⭐
- `submit.html` - Alternative simple form
- `track.html` - Alternative tracking page

### Admin Pages:
- `admin-login.html` - Admin authentication
- `enhanced-admin.html` - Full dashboard with 7 modules
- `admin.html` - Alternative admin panel

## 🔥 Key Features to Test

### MILESTONE 1: User Authentication & Submission ✅
- [ ] Anonymous submission
- [ ] User registration
- [ ] User login
- [ ] File upload (try uploading an image!)
- [ ] Complaint ID generation

### MILESTONE 2: Status Tracking & Timeline ✅
- [ ] Track by complaint ID
- [ ] View status timeline
- [ ] See admin comments
- [ ] View uploaded files

### MILESTONE 3: Admin Dashboard ✅
- [ ] Admin login
- [ ] View all complaints
- [ ] Assign complaints
- [ ] Update status with comments
- [ ] Add internal notes
- [ ] Send user replies

### MILESTONE 4: Escalation (Structure Ready) ✅
- [ ] Mark complaint for escalation
- [ ] View escalated complaints

### MILESTONE 5: Reports & Analytics ✅
- [ ] View dashboard statistics
- [ ] Filter complaints by status
- [ ] Export data (CSV/JSON)

## 📱 Mobile Responsive

All pages are mobile-friendly! Try accessing from your phone.

## 🗂️ Database

H2 in-memory database auto-configured:
- No setup needed
- Data resets on restart
- Access H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:complaintdb`
  - Username: `sa`
  - Password: (leave empty)

## 🛑 Stopping the Application

Press `Ctrl+C` in the terminal where the application is running.

## 🎯 Quick Test Script

```bash
# 1. Start application
mvnw.cmd spring-boot:run

# 2. Wait for "Started DemoApplication"

# 3. Open browser to:
http://localhost:8080/home.html

# 4. Click "Submit Your Complaint"

# 5. Choose Anonymous mode

# 6. Fill form and submit

# 7. Note the Complaint ID

# 8. Click "Track" and enter ID

# 9. Login as admin to manage it
```

## 📊 What You'll See

### User Portal Features:
- ✅ Beautiful gradient purple theme
- ✅ Mode selection (Anonymous/Registered)
- ✅ Professional forms with validation
- ✅ Drag-drop file upload
- ✅ Success confirmation with tracking ID

### Tracking Page Features:
- ✅ Search by complaint ID
- ✅ Complete complaint details display
- ✅ Visual timeline with animations
- ✅ Status color coding (New/Under Review/Resolved)
- ✅ Media files grid display

### Admin Dashboard Features:
- ✅ Statistics cards
- ✅ 7 functional modules
- ✅ Complaint management
- ✅ Assignment system
- ✅ Communications
- ✅ Reports & analytics

## 🎉 Success Indicators

Application running correctly when you see:
```
Started DemoApplication in X.XXX seconds
```

Pages loading correctly:
- ✅ Home page shows "ResolveIT" with gradient background
- ✅ User portal shows mode selection
- ✅ Tracking page has search box
- ✅ Admin login shows purple theme

## ⚠️ Troubleshooting

**Port 8080 already in use?**
```cmd
# Windows: Kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Application won't start?**
- Check Java version: `java -version` (should be 25)
- Check Maven: `mvn -version`
- Clean and rebuild: `mvnw.cmd clean install`

**Pages not loading?**
- Ensure application started successfully
- Check console for errors
- Verify URL: `http://localhost:8080/` (not https)

## 📚 More Information

See `MILESTONE_IMPLEMENTATION_GUIDE.md` for complete technical documentation.

---

**🎯 Ready to go! Start with http://localhost:8080/home.html** 🚀
