# 🎓 College Gatepass Management System (Backend)

A backend system built using **Java and Spring Boot** to digitize the traditional college gatepass process.  
This project allows students to request leave, authorities to approve/reject requests, and guards to verify student exit/entry securely.

⏱️ **Development Time:** Completed backend within **10 days**

---

## 🚀 Features

### 👨‍🎓 Student
- Submit gatepass/leave requests  
- View request status (Approved / Rejected / Pending)
- Genrates QR for each leave request
- Change Password

### 🧑‍💼 Admin / Authority
- View all student requests  
- Approve or reject gatepass requests  
- Manage students and roles
- Creates Student with Temp password
- See history of leaves
- handles passout students
    

### 🛂 Guard
- Verify approved gatepass before allowing exit/entry
- Verify QR from student  

### 🔐 Security
- JWT-based authentication  
- Role-based authorization (Student, Admin, Faculty, Guard)
- Implemented Refresh token strategy for maintaining long term login sessions  

---

## 🏗️ Architecture

This project follows **Layered Architecture**:

