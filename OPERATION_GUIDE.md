# 🎯 ResolveIT - Complete Operation Guide

## 📖 HOW TO OPERATE EVERY FEATURE IN DEPTH

This guide explains **exactly** how to use every feature of your ResolveIT platform with detailed steps and visual flows.

---

## 🚀 STEP 1: STARTING THE APPLICATION

### Starting the Server:

```
┌─────────────────────────────────────────────────┐
│ 1. Open Command Prompt (cmd)                   │
├─────────────────────────────────────────────────┤
│                                                 │
│ > cd "c:\Users\Sudharshan N K\Downloads\       │
│       demo (1)\demo"                            │
│                                                 │
│ > mvnw.cmd spring-boot:run                     │
│                                                 │
│   [Wait 30-60 seconds...]                      │
│                                                 │
│   ✅ SUCCESS MESSAGE:                          │
│   "Started DemoApplication in 45.232 seconds"  │
│                                                 │
│   Server is now running on:                    │
│   http://localhost:8080                        │
└─────────────────────────────────────────────────┘
```

**What happens behind the scenes:**
- Java compiles your code
- Spring Boot starts embedded Tomcat server
- H2 database initializes in memory
- All REST APIs become available
- Static HTML pages are served

---

## 🏠 STEP 2: ACCESSING THE HOME PAGE

### Opening the Landing Page:

```
┌─────────────────────────────────────────────────┐
│ Open Browser → Type URL                        │
├─────────────────────────────────────────────────┤
│                                                 │
│ URL: http://localhost:8080/home.html          │
│                                                 │
│ OR just: http://localhost:8080                 │
│ (it auto-redirects to home.html)              │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│            🎯 ResolveIT                        │
│   Smart Digital Grievance Redressal Platform   │
├─────────────────────────────────────────────────┤
│                                                 │
│   Purple Gradient Background                   │
│                                                 │
│   [About] [Features] [Portals] [Track]        │
│                                                 │
│   "Transform grievance handling from           │
│    manual slow process into digital..."        │
│                                                 │
│   [📝 Submit Your Complaint]  [📖 Learn More] │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Navigation Options on Home Page:**

1. **"Submit Your Complaint"** button → Takes you to `user-portal.html`
2. **"Track"** link in header → Takes you to `track-complaint.html`
3. **"Admin Login"** button → Takes you to `admin-login.html`
4. Scroll down to see:
   - About ResolveIT section
   - Key features (6 cards)
   - Benefits breakdown
   - Portal access cards

---

## 📝 STEP 3: SUBMITTING A COMPLAINT (USER FLOW)

### 3.1 Opening User Portal

```
METHOD 1: Click "Submit Your Complaint" on home page
METHOD 2: Direct URL: http://localhost:8080/user-portal.html

┌─────────────────────────────────────────────────┐
│          🎯 ResolveIT                          │
│     Smart Grievance Redressal Platform         │
├─────────────────────────────────────────────────┤
│                                                 │
│         Choose Submission Mode                  │
│                                                 │
│   ┌──────────────┐      ┌──────────────┐      │
│   │              │      │              │      │
│   │  🕶️ ANONYMOUS │      │ 👤 REGISTERED │      │
│   │              │      │   USER       │      │
│   │ Submit without│      │ Login or    │      │
│   │ revealing    │      │ Register to │      │
│   │ identity     │      │ submit      │      │
│   │              │      │              │      │
│   │ ✓ Complete   │      │ ✓ Track all │      │
│   │   privacy    │      │   complaints│      │
│   │ ✓ No login   │      │ ✓ Email     │      │
│   │   needed     │      │   alerts    │      │
│   │ ✓ Track with │      │ ✓ Priority  │      │
│   │   ID         │      │   support   │      │
│   │              │      │              │      │
│   └──────────────┘      └──────────────┘      │
│                                                 │
└─────────────────────────────────────────────────┘
```

**DECISION POINT:** Which mode to choose?

| Choose ANONYMOUS when: | Choose REGISTERED when: |
|----------------------|------------------------|
| ✓ Want complete privacy | ✓ Want to track multiple complaints |
| ✓ Sensitive complaint | ✓ Want email notifications |
| ✓ Don't want account | ✓ Want priority support |
| ✓ Quick submission | ✓ Want complaint history |

---

### 3.2 OPTION A: ANONYMOUS SUBMISSION (EASIEST)

```
Step 1: Click "🕶️ ANONYMOUS" button

┌─────────────────────────────────────────────────┐
│          Submit Your Complaint                  │
├─────────────────────────────────────────────────┤
│                                                 │
│ Your Name (Optional for anonymous)             │
│ [________________]                              │
│                                                 │
│ Email (Optional for anonymous)                 │
│ [________________]                              │
│                                                 │
│ Phone (Optional)                               │
│ [________________]                              │
│                                                 │
│ Category *                                     │
│ [Select Category ▼]                            │
│   - Infrastructure                             │
│   - Service Quality                            │
│   - Staff Behavior                             │
│   - Financial                                  │
│   - Safety & Security                          │
│   - Cleanliness                                │
│   - Other                                      │
│                                                 │
│ Urgency Level *                                │
│ [Select Urgency ▼]                             │
│   - Low                                        │
│   - Medium                                     │
│   - High                                       │
│   - Critical                                   │
│                                                 │
│ Subject *                                      │
│ [________________]                              │
│                                                 │
│ Description *                                  │
│ [_____________________________________]        │
│ [_____________________________________]        │
│ [_____________________________________]        │
│                                                 │
│ Attachments (Images/Documents)                 │
│ ┌─────────────────────────────────────┐       │
│ │  📎 Click to upload supporting files│       │
│ │  Max 5 files, up to 10MB each       │       │
│ └─────────────────────────────────────┘       │
│                                                 │
│         [Submit Complaint]                     │
│                                                 │
│ Already have ID? Track existing complaint      │
└─────────────────────────────────────────────────┘
```

**Step-by-Step Instructions:**

1. **Name** (Optional): Type "Anonymous User" or leave blank
2. **Email** (Optional): Leave blank or provide for updates
3. **Phone** (Optional): Leave blank or provide
4. **Category** (Required): Click dropdown, select "Infrastructure"
5. **Urgency** (Required): Click dropdown, select "High"
6. **Subject** (Required): Type "Broken Street Light at Main Road"
7. **Description** (Required): Type detailed complaint:
   ```
   The street light near Main Road Junction has been 
   non-functional for the past 3 days. This creates 
   safety issues during night time. Immediate attention 
   required.
   ```
8. **Attachments** (Optional): Click "📎 Click to upload"
   - Browse and select image file (photo of broken light)
   - File appears in list below upload area
   - Can upload up to 5 files
9. **Click "Submit Complaint"** button

---

### 3.3 WHAT HAPPENS AFTER SUBMISSION

```
┌─────────────────────────────────────────────────┐
│   ✅ Complaint Submitted Successfully!         │
├─────────────────────────────────────────────────┤
│                                                 │
│     Your Complaint ID: CMP-2025-001           │
│                                                 │
│   Please save this ID to track your complaint  │
│                                                 │
│   [Submit Another Complaint]                   │
│                                                 │
└─────────────────────────────────────────────────┘
```

**IMPORTANT:** Write down or screenshot your Complaint ID!
- Format: `CMP-YYYY-NNN` (CMP-2025-001, CMP-2025-002, etc.)
- This ID is your tracking number
- You'll need it to check status

**Behind the Scenes:**
```
Frontend (JavaScript)               Backend (Spring Boot)
─────────────────────              ────────────────────────
Submit button clicked
   │
   ├─> Validate form fields
   │
   ├─> Create JSON object:
   │   {
   │     "category": "Infrastructure",
   │     "urgency": "High",
   │     "subject": "Broken Street Light",
   │     "description": "...",
   │     "anonymous": true
   │   }
   │                                ComplaintController receives
   ├─> POST /api/complaints    ──> 
   │                                   │
   │                                   ├─> ComplaintService
   │                                   │
   │                                   ├─> Generate ID (CMP-2025-001)
   │                                   │
   │                                   ├─> Save to H2 database
   │                                   │
   │                                   ├─> Create StatusLog (NEW)
   │                                   │
   ├─< Return complaint object  <─────┤
   │   with ID                         │
   │
   ├─> If files selected:
   │   For each file:
   │     POST /api/complaints/1/upload
   │                                Upload to uploads/ folder
   │                                Save MediaUpload record
   │
   └─> Display success message
```

---

### 3.4 OPTION B: REGISTERED USER SUBMISSION

```
Step 1: Click "👤 REGISTERED USER" button

┌─────────────────────────────────────────────────┐
│     [Login]  [Register] ← Tabs                 │
├─────────────────────────────────────────────────┤
│                                                 │
│  IF NEW USER - Click "Register" Tab:           │
│                                                 │
│  Full Name *                                   │
│  [________________]                             │
│                                                 │
│  Email *                                       │
│  [________________]                             │
│                                                 │
│  Password *                                    │
│  [________________]                             │
│                                                 │
│  User Type                                     │
│  [Citizen ▼]                                   │
│    - Citizen                                   │
│    - Student                                   │
│    - Employee                                  │
│    - Customer                                  │
│                                                 │
│  [Register]                                    │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Registration Flow:**

1. Click "Register" tab
2. Fill form:
   - Name: "John Smith"
   - Email: "john@example.com"
   - Password: "password123"
   - Type: "Citizen"
3. Click "Register" button

**What happens:**
```
POST /api/users/register
{
  "name": "John Smith",
  "email": "john@example.com",
  "password": "password123",
  "role": "CITIZEN"
}

Backend:
├─> UserService.registerUser()
├─> Check if email exists
├─> Save User to database
└─> Return success message
```

4. After registration, switch to "Login" tab
5. Enter email + password
6. Click "Login"

**Login Flow:**
```
POST /api/users/login
{
  "email": "john@example.com",
  "password": "password123"
}

Backend:
├─> UserService.authenticateUser()
├─> Find user by email
├─> Compare passwords
├─> Return user object

Frontend:
├─> Save user in sessionStorage
└─> Show complaint form with pre-filled details
```

6. Complaint form opens with:
   - Name and email already filled
   - Fields locked (read-only)
   - Submit complaint as before

**Benefit of Registered User:**
- Can view all your complaints later
- Receive notifications (structure ready)
- Priority support
- Complaint history

---

## 🔍 STEP 4: TRACKING A COMPLAINT

### 4.1 Opening Track Page

```
METHOD 1: Click "Track" in home page header
METHOD 2: Direct URL: http://localhost:8080/track-complaint.html
METHOD 3: After submission, click "Track existing complaint"

┌─────────────────────────────────────────────────┐
│       🔍 Track Your Complaint                  │
│   Enter your complaint ID to view real-time    │
│          status and timeline                    │
├─────────────────────────────────────────────────┤
│                                                 │
│          Enter Complaint ID                     │
│                                                 │
│  [CMP-2025-001________________]  [🔎 Track]   │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Step-by-Step:**

1. Type your complaint ID: `CMP-2025-001`
2. Press Enter OR click "🔎 Track Status" button

---

### 4.2 Viewing Complaint Details

```
After clicking Track, page expands:

┌─────────────────────────────────────────────────┐
│    📋 Complaint Details                        │
├─────────────────────────────────────────────────┤
│                                                 │
│  Complaint ID        │  CMP-2025-001           │
│  Current Status      │  🟠 UNDER REVIEW        │
│  Category           │  Infrastructure          │
│  Urgency            │  High                    │
│  Submitted On       │  January 13, 2025        │
│  Last Updated       │  January 15, 2025        │
│                                                 │
│  Subject: Broken Street Light at Main Road     │
│                                                 │
│  The street light near Main Road Junction...   │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Status Colors:**
- 🔵 **NEW** - Blue badge (just submitted)
- 🟠 **UNDER_REVIEW** - Orange badge (being worked on)
- 🟢 **RESOLVED** - Green badge (completed)

---

### 4.3 Viewing Timeline (MILESTONE 2 FEATURE)

```
Below complaint details, you see animated timeline:

┌─────────────────────────────────────────────────┐
│    📅 Status Timeline                          │
├─────────────────────────────────────────────────┤
│                                                 │
│  ●───────── UNDER REVIEW (Latest)              │
│  │          Jan 15, 2025 at 10:30 AM           │
│  │          "Assigned to maintenance team       │
│  │           for inspection"                    │
│  │          Updated by: Admin (admin)           │
│  │                                              │
│  │                                              │
│  ●───────── NEW                                 │
│             Jan 13, 2025 at 4:45 PM            │
│             "Complaint submitted successfully"  │
│             Updated by: System                  │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Timeline Features:**
- **Top = Latest update** (most recent)
- **Bottom = First submission** (oldest)
- **Active dot** (filled circle) = current status
- Each entry shows:
  - Status name
  - Date and time
  - Admin comment (if any)
  - Who made the update

**Visual Elements:**
- Vertical line connecting all dots
- Smooth slide-in animation when loaded
- Color gradient on timeline line
- Hover effects on timeline items

---

### 4.4 Viewing Uploaded Files

```
If complaint has attachments:

┌─────────────────────────────────────────────────┐
│    📎 Attachments                              │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌──────┐  ┌──────┐  ┌──────┐                │
│  │ 🖼️   │  │ 📄   │  │ 🖼️   │                │
│  │IMG01 │  │DOC01 │  │IMG02 │                │
│  └──────┘  └──────┘  └──────┘                │
│   Click to download                            │
│                                                 │
└─────────────────────────────────────────────────┘
```

**File Icons:**
- 🖼️ = Images (jpg, png, gif)
- 📄 = Documents (pdf, doc, docx)
- 🎥 = Videos (mp4, avi)

**To download:** Click on any file card

---

## 🛡️ STEP 5: ADMIN OPERATIONS

### 5.1 Admin Login

```
URL: http://localhost:8080/admin-login.html

┌─────────────────────────────────────────────────┐
│          🛡️ Admin Login                        │
│      Complaint Management System                │
├─────────────────────────────────────────────────┤
│                                                 │
│  Username                                      │
│  [________________]                             │
│                                                 │
│  Password                                      │
│  [________________] 👁️                         │
│                                                 │
│  [ ] Remember me                               │
│                                                 │
│  [Login]                                       │
│                                                 │
│  ─────────── Quick Login ───────────           │
│                                                 │
│  [Admin] [Staff] [Senior Admin]                │
│                                                 │
│  Demo Credentials:                             │
│  Admin: admin / admin123                       │
│  Staff: staff / staff123                       │
│  Senior: senior / senior123                    │
└─────────────────────────────────────────────────┘
```

**Login Steps:**

1. Enter username: `admin`
2. Enter password: `admin123` (click 👁️ to show/hide)
3. Click "Login" button

**OR use Quick Login:**
- Click "[Admin]" button → auto-fills credentials
- Click "[Staff]" button → auto-fills staff credentials
- Click "[Senior Admin]" button → auto-fills senior credentials

**What happens:**
```
POST /api/admins/login
{
  "username": "admin",
  "password": "admin123"
}

Backend:
├─> AdminService.authenticateAdmin()
├─> Find admin by username
├─> Compare passwords
├─> Return admin object with role

Frontend:
├─> Save admin in sessionStorage
├─> Set session expiry (24 hours)
└─> Redirect to enhanced-admin.html
```

---

### 5.2 Admin Dashboard Overview

```
After login, you see:

┌──────────────────────────────────────────────────────────────┐
│ 🛡️ ResolveIT Admin Dashboard          Admin: John | Logout  │
├─────────────┬────────────────────────────────────────────────┤
│             │  📊 DASHBOARD STATISTICS                      │
│ Dashboard   │                                                │
│   (active)  │  ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐        │
│             │  │  45 │  │  12 │  │  28 │  │  5  │        │
│ All         │  │Total│  │ New │  │Under│  │Esca │        │
│ Complaints  │  │     │  │     │  │Revw │  │lted │        │
│             │  └─────┘  └─────┘  └─────┘  └─────┘        │
│ Assignments │                                                │
│             │  📋 Recent Complaints                         │
│ Escalations │  ┌───┬──────────┬─────────┬────────┬──────┐ │
│             │  │ID │ Subject  │ Status  │ Date   │Action│ │
│ Communica-  │  ├───┼──────────┼─────────┼────────┼──────┤ │
│ tions       │  │001│ Street.. │ Under R │ Jan 13 │View ││ │
│             │  │002│ Water... │ New     │ Jan 14 │View ││ │
│ Reports     │  └───┴──────────┴─────────┴────────┴──────┘ │
│             │                                                │
│ Export      │                                                │
│             │                                                │
│             │                                                │
│ [Logout]    │                                                │
└─────────────┴────────────────────────────────────────────────┘
```

**Dashboard Components:**

**Left Sidebar (Navigation):**
- Dashboard (overview)
- All Complaints (list view)
- Assignments (assign to staff)
- Escalations (escalated cases)
- Communications (notes & replies)
- Reports (analytics)
- Export (download data)
- Logout button

**Main Area:**
- Statistics cards (live counts)
- Recent complaints table
- Quick action buttons

---

### 5.3 Viewing All Complaints

```
Click "All Complaints" in sidebar:

┌──────────────────────────────────────────────────┐
│  📋 All Complaints Management                   │
├──────────────────────────────────────────────────┤
│                                                  │
│  Filter: [All Status ▼] [All Categories ▼]     │
│                                                  │
│  ┌────┬────────────┬──────┬────────┬────────┐  │
│  │ID  │Subject     │Status│Category│Actions │  │
│  ├────┼────────────┼──────┼────────┼────────┤  │
│  │001 │Street Light│Under │Infrast.│[View]  │  │
│  │    │broken      │Review│        │[Assign]│  │
│  │    │            │      │        │[Update]│  │
│  ├────┼────────────┼──────┼────────┼────────┤  │
│  │002 │Water leak  │New   │Service │[View]  │  │
│  │    │in building │      │        │[Assign]│  │
│  │    │            │      │        │[Update]│  │
│  └────┴────────────┴──────┴────────┴────────┘  │
└──────────────────────────────────────────────────┘
```

**Filter Options:**
- **Status Filter:** All / NEW / UNDER_REVIEW / RESOLVED
- **Category Filter:** All / Infrastructure / Service / etc.

**Action Buttons:**
- **[View]** - See full complaint details
- **[Assign]** - Assign to staff member
- **[Update]** - Change status

---

### 5.4 ASSIGNING A COMPLAINT (MILESTONE 3)

```
Click [Assign] button on any complaint:

┌─────────────────────────────────────────────────┐
│     Assign Complaint #001                      │
├─────────────────────────────────────────────────┤
│                                                 │
│  Assign To:                                    │
│  [Select Staff Member ▼]                       │
│    - John Doe (Staff)                          │
│    - Jane Smith (Staff)                        │
│    - Bob Johnson (Senior Admin)                │
│                                                 │
│  Assignment Notes:                             │
│  [_____________________________________]       │
│                                                 │
│  [Assign] [Cancel]                             │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Steps:**
1. Select staff member from dropdown
2. Add notes: "Please inspect street light immediately"
3. Click "Assign" button

**What happens:**
```
PUT /api/complaints/1/assign
{
  "adminId": 2,
  "assignedBy": "admin"
}

Backend:
├─> Find complaint by ID
├─> Find admin by ID
├─> Set complaint.assignedTo = admin
├─> Create StatusLog entry
├─> Save to database

Result:
├─> Complaint now shows "Assigned to: John Doe"
├─> Staff member can see it in their queue
└─> Timeline updated with assignment
```

---

### 5.5 UPDATING COMPLAINT STATUS (MILESTONE 3)

```
Click [Update] button on any complaint:

┌─────────────────────────────────────────────────┐
│     Update Complaint Status #001               │
├─────────────────────────────────────────────────┤
│                                                 │
│  Current Status: NEW                           │
│                                                 │
│  New Status:                                   │
│  [UNDER_REVIEW ▼]                              │
│    - NEW                                       │
│    - UNDER_REVIEW                              │
│    - RESOLVED                                  │
│                                                 │
│  Comment for User (visible in timeline):       │
│  [_____________________________________]       │
│  [_____________________________________]       │
│                                                 │
│  "Your complaint has been assigned to our      │
│   maintenance team for immediate inspection"   │
│                                                 │
│  [Update Status] [Cancel]                      │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Steps:**
1. Select new status: "UNDER_REVIEW"
2. Add comment for user (this appears in timeline)
3. Click "Update Status"

**What happens:**
```
PUT /api/complaints/1/status
{
  "status": "UNDER_REVIEW",
  "comment": "Assigned to maintenance team...",
  "adminUsername": "admin"
}

Backend:
├─> Validate status transition (NEW → UNDER_REVIEW ✅)
├─> Update complaint.status
├─> Update complaint.lastUpdatedAt
├─> Create StatusUpdate record
├─> Create StatusLog entry
├─> Save to database

Timeline Update:
└─> User sees new entry when tracking:
    "● UNDER_REVIEW
     Jan 15, 2025 at 10:30 AM
     Assigned to maintenance team...
     Updated by: admin"
```

---

### 5.6 ADDING INTERNAL NOTES (MILESTONE 3)

```
Click "Communications" in sidebar:

┌─────────────────────────────────────────────────┐
│     📝 Internal Notes & User Replies           │
├─────────────────────────────────────────────────┤
│                                                 │
│  [Add Internal Note] [Send User Reply]         │
│                                                 │
│  Select Complaint: [CMP-2025-001 ▼]           │
│                                                 │
└─────────────────────────────────────────────────┘

Click [Add Internal Note]:

┌─────────────────────────────────────────────────┐
│     Add Internal Note                          │
│     (Visible only to admin/staff)              │
├─────────────────────────────────────────────────┤
│                                                 │
│  Complaint: CMP-2025-001                       │
│                                                 │
│  Note:                                         │
│  [_____________________________________]       │
│  [_____________________________________]       │
│                                                 │
│  "Contacted electricity department.            │
│   Repair scheduled for tomorrow 2 PM"          │
│                                                 │
│  [Add Note] [Cancel]                           │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Internal Notes are:**
- ✅ Private (users cannot see them)
- ✅ For staff communication
- ✅ Track investigation progress
- ✅ Share updates between admins

---

### 5.7 SENDING USER REPLY (MILESTONE 3)

```
Click [Send User Reply]:

┌─────────────────────────────────────────────────┐
│     Send Reply to User                         │
│     (User will see this in timeline)           │
├─────────────────────────────────────────────────┤
│                                                 │
│  Complaint: CMP-2025-001                       │
│                                                 │
│  Reply Message:                                │
│  [_____________________________________]       │
│  [_____________________________________]       │
│                                                 │
│  "Thank you for reporting. Our maintenance     │
│   team will fix the street light tomorrow."    │
│                                                 │
│  [Send Reply] [Cancel]                         │
│                                                 │
└─────────────────────────────────────────────────┘
```

**User Replies are:**
- ✅ Public (users see them when tracking)
- ✅ Appear in timeline
- ✅ Keep users informed
- ✅ Build transparency

---

### 5.8 ESCALATING COMPLAINTS (MILESTONE 4)

```
Click "Escalations" in sidebar:

┌─────────────────────────────────────────────────┐
│     ⚡ Escalation Management                   │
├─────────────────────────────────────────────────┤
│                                                 │
│  Unresolved Complaints (>5 days):              │
│                                                 │
│  ┌────┬─────────┬─────┬─────┬────────────┐    │
│  │ID  │Subject  │Days │Urgency│Action    │    │
│  ├────┼─────────┼─────┼───────┼──────────┤    │
│  │003 │Pothole  │ 7   │High   │[Escalate]│    │
│  │005 │Garbage  │ 6   │Medium │[Escalate]│    │
│  └────┴─────────┴─────┴───────┴──────────┘    │
│                                                 │
│  Escalated Complaints:                         │
│                                                 │
│  ┌────┬─────────┬──────────┬──────────┐       │
│  │ID  │Subject  │Escalated │Assigned  │       │
│  │    │         │To        │On        │       │
│  ├────┼─────────┼──────────┼──────────┤       │
│  │001 │Road     │Bob John. │Jan 10    │       │
│  │    │Damage   │(Senior)  │          │       │
│  └────┴─────────┴──────────┴──────────┘       │
└─────────────────────────────────────────────────┘
```

**Escalation Triggers:**
- **Manual:** Click [Escalate] button
- **Automatic:** System detects complaints unresolved >5 days

**Click [Escalate]:**
```
┌─────────────────────────────────────────────────┐
│     Escalate Complaint #003                    │
├─────────────────────────────────────────────────┤
│                                                 │
│  Escalate To (Senior Admin):                   │
│  [Select Senior ▼]                             │
│    - Bob Johnson (Senior Admin)                │
│    - Sarah Williams (Senior Admin)             │
│                                                 │
│  Reason for Escalation:                        │
│  [_____________________________________]       │
│  [_____________________________________]       │
│                                                 │
│  "Complaint unresolved for 7 days.             │
│   Requires senior management attention."       │
│                                                 │
│  [Escalate] [Cancel]                           │
│                                                 │
└─────────────────────────────────────────────────┘
```

---

### 5.9 VIEWING REPORTS (MILESTONE 5)

```
Click "Reports" in sidebar:

┌─────────────────────────────────────────────────┐
│     📊 Reports & Analytics                     │
├─────────────────────────────────────────────────┤
│                                                 │
│  Performance Metrics:                          │
│                                                 │
│  ┌────────────────┐  ┌────────────────┐       │
│  │ Resolution Rate│  │ Avg Response   │       │
│  │                │  │ Time           │       │
│  │     82.5%      │  │   2.3 days     │       │
│  └────────────────┘  └────────────────┘       │
│                                                 │
│  ┌────────────────┐  ┌────────────────┐       │
│  │ Pending Count  │  │ Escalation Rate│       │
│  │                │  │                │       │
│  │      8         │  │     11.2%      │       │
│  └────────────────┘  └────────────────┘       │
│                                                 │
│  Complaint Trends by Category:                │
│  [Chart would show here]                       │
│                                                 │
│  Status Distribution:                          │
│  [Pie chart would show here]                   │
│                                                 │
└─────────────────────────────────────────────────┘
```

---

### 5.10 EXPORTING DATA (MILESTONE 5)

```
Click "Export" in sidebar:

┌─────────────────────────────────────────────────┐
│     📤 Export Complaint Data                   │
├─────────────────────────────────────────────────┤
│                                                 │
│  Export Format:                                │
│  ○ CSV (Excel compatible)                      │
│  ● JSON (For developers)                       │
│  ○ PDF (Coming soon)                           │
│                                                 │
│  Filter Data:                                  │
│  Status: [All ▼]                               │
│  Category: [All ▼]                             │
│                                                 │
│  Date Range:                                   │
│  From: [2025-01-01]  To: [2025-01-31]         │
│                                                 │
│  [Download Export]                             │
│                                                 │
└─────────────────────────────────────────────────┘
```

**Export Steps:**
1. Select format (CSV or JSON)
2. Choose filters (optional)
3. Set date range (optional)
4. Click "Download Export"

**File downloads to your computer:**
- CSV: `complaints_export_2025-01-31.csv`
- JSON: `complaints_export_2025-01-31.json`

---

## 🔄 COMPLETE USER JOURNEY FLOW

### Scenario: Citizen Reports Broken Light → Admin Fixes It

```
┌─────────────────────────────────────────────────┐
│  DAY 1: CITIZEN SUBMITS COMPLAINT              │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Citizen visits user-portal.html            │
│  2. Chooses ANONYMOUS mode                     │
│  3. Fills form:                                │
│     - Category: Infrastructure                 │
│     - Urgency: High                            │
│     - Subject: "Broken Street Light"           │
│     - Description: "Light not working..."      │
│     - Uploads: photo.jpg                       │
│  4. Clicks Submit                              │
│  5. Gets ID: CMP-2025-001                      │
│  6. Writes it down                             │
│                                                 │
│  ✅ Status: NEW                                │
│  📅 Timeline: "Complaint submitted"            │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  DAY 1 (30 min later): ADMIN REVIEWS           │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Admin logs in (admin/admin123)             │
│  2. Sees new complaint in dashboard            │
│  3. Clicks "View" to read details              │
│  4. Clicks "Assign" button                     │
│  5. Assigns to "John Doe (Staff)"              │
│  6. Updates status to UNDER_REVIEW             │
│  7. Adds comment: "Assigned to maintenance"    │
│                                                 │
│  ✅ Status: UNDER_REVIEW                       │
│  📅 Timeline: Added "Assigned to maintenance"  │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  DAY 1 (evening): CITIZEN CHECKS STATUS        │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Citizen visits track-complaint.html        │
│  2. Enters CMP-2025-001                        │
│  3. Sees status: UNDER_REVIEW                  │
│  4. Reads timeline:                            │
│     "Assigned to maintenance team"             │
│  5. Feels informed!                            │
│                                                 │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  DAY 2: STAFF WORKS ON IT                      │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Staff logs in                              │
│  2. Sees assigned complaint                    │
│  3. Adds internal note:                        │
│     "Contacted electric dept. Repair tomorrow" │
│  4. Sends user reply:                          │
│     "We'll fix it tomorrow by 2 PM"            │
│                                                 │
│  📅 Timeline: User sees reply                  │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  DAY 3: WORK COMPLETED                         │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Staff updates status to RESOLVED           │
│  2. Adds comment:                              │
│     "Street light repaired and tested"         │
│                                                 │
│  ✅ Status: RESOLVED                           │
│  📅 Timeline: "Repaired and tested"            │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  DAY 3 (evening): CITIZEN CONFIRMS             │
├─────────────────────────────────────────────────┤
│                                                 │
│  1. Citizen checks status again                │
│  2. Sees RESOLVED                              │
│  3. Reads: "Street light repaired"             │
│  4. Goes to location, verifies it's working    │
│  5. Happy! Problem solved!                     │
│                                                 │
│  🎉 COMPLAINT LIFECYCLE COMPLETE               │
└─────────────────────────────────────────────────┘
```

---

## 🎯 QUICK REFERENCE: ALL URLS

| Page | URL | Purpose |
|------|-----|---------|
| **Landing** | `/home.html` | Platform overview |
| **Submit** | `/user-portal.html` | Submit complaints ⭐ |
| **Track** | `/track-complaint.html` | Track status ⭐ |
| **Admin** | `/admin-login.html` | Admin login |
| **Dashboard** | `/enhanced-admin.html` | Admin panel |

## 🔑 QUICK REFERENCE: DEMO LOGINS

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Staff | staff | staff123 |
| Senior | senior | senior123 |

## 📱 MOBILE ACCESS

All pages work on mobile phones! Just visit same URLs on your phone browser.

---

## 🎉 YOU'RE NOW AN EXPERT!

You now know how to operate every feature of ResolveIT:

✅ Submit complaints (anonymous or registered)
✅ Upload files
✅ Track with timeline
✅ Admin login
✅ View all complaints
✅ Assign to staff
✅ Update status
✅ Add notes
✅ Send replies
✅ Escalate cases
✅ View reports
✅ Export data

**Start the application and try it out!** 🚀

```bash
mvnw.cmd spring-boot:run
```

Then open: **http://localhost:8080/home.html**
