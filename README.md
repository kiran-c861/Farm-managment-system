# 🌾 Agriculture Farm Management System

A full-stack farm management system built with **Spring Boot** (backend) and **HTML/CSS/JS** (frontend) with a premium dark-green theme.

---

## ⚡ One-Command Setup & Run

After cloning, just run:
```powershell
# Windows PowerShell (Run as Administrator)
.\start.ps1
```

This script will **automatically**:
- ✅ Install Java 17 (if not installed)
- ✅ Install Apache Maven (if not installed)
- ✅ Check MySQL is running
- ✅ Create the `farm_management` database + import schema
- ✅ Download all Java dependencies (via Maven)
- ✅ Start the backend on `http://localhost:8080`

> **Only requirement:** XAMPP must be installed and **MySQL must be running** in XAMPP Control Panel before running the script.

---

## 📋 Prerequisites — Install These First

| Tool | Download |
|---|---|
| **Java 17+** | [Microsoft OpenJDK 17](https://aka.ms/download-jdk/microsoft-jdk-17-windows-x64.msi) |
| **Apache Maven** | [maven.apache.org](https://maven.apache.org/download.cgi) → extract ZIP → add `bin/` to PATH |
| **XAMPP** (MySQL) | [apachefriends.org](https://www.apachefriends.org/) |
| **VS Code** | [code.visualstudio.com](https://code.visualstudio.com/) |
| **Live Server** (VS Code extension) | Search "Live Server" by Ritwick Dey in VS Code Extensions |

> **Quick Java install (Windows PowerShell):**
> ```powershell
> winget install Microsoft.OpenJDK.17
> ```
> **Quick Maven install (Windows PowerShell):**
> ```powershell
> Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip" -OutFile "$env:USERPROFILE\Downloads\maven.zip" -UseBasicParsing
> Expand-Archive -Path "$env:USERPROFILE\Downloads\maven.zip" -DestinationPath "C:\maven" -Force
> # Then add C:\maven\apache-maven-3.9.6\bin to your PATH environment variable
> ```

---

## 🚀 Setup & Run (Step-by-Step)

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/Agriculture-farm-managment.git
cd Agriculture-farm-managment
```

### 2. Setup MySQL Database
1. Open **XAMPP Control Panel** → Start **MySQL**
2. Open browser → go to `http://localhost/phpmyadmin`
3. Click **New** → create database named: `farm_management`
4. Click on `farm_management` → click **Import** tab
5. Choose file: `farm-backend/src/main/resources/schema.sql` → click **Go**

### 3. Configure Database Password (if needed)
Edit `farm-backend/src/main/resources/application.properties`:
```properties
spring.datasource.password=       ← leave blank for XAMPP default (no password)
# OR if you have a password set:
spring.datasource.password=your_password
```

### 4. Start the Backend
> ⚠️ **If Maven is not in PATH**, use full path: `C:\maven\apache-maven-3.9.6\bin\mvn`

**Option A – if you installed Maven normally:**
```bash
cd farm-backend
mvn clean spring-boot:run
```

**Option B – if Maven is not in PATH (Windows PowerShell):**
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot"
$env:PATH += ";$env:JAVA_HOME\bin;C:\maven\apache-maven-3.9.6\bin"
cd farm-backend
mvn clean spring-boot:run
```

Wait until you see: `Started FarmManagementApplication in X.XXX seconds` ✅

Backend is now live at: **http://localhost:8080**

### 5. Open the Frontend
1. Open the project in **VS Code**
2. Right-click `farm-frontend/index.html`
3. Click **"Open with Live Server"**
4. Browser opens at `http://127.0.0.1:5500`

### 6. Login
| Username | Password |
|---|---|
| `admin` | `admin123` |

---

## 📁 Project Structure
```
Agriculture-farm-managment/
├── farm-backend/              ← Spring Boot API (Port 8080)
│   ├── pom.xml
│   └── src/main/java/com/farmmanagement/
│       ├── model/             ← JPA Entities
│       ├── repository/        ← JPA Repositories
│       ├── service/           ← Business Logic
│       ├── controller/        ← REST Controllers
│       ├── security/          ← JWT + Spring Security
│       ├── config/            ← Jackson, CORS configs
│       └── exception/         ← Global Error Handler
│   └── src/main/resources/
│       ├── application.properties
│       └── schema.sql         ← DB Schema + seed data
└── farm-frontend/             ← HTML/CSS/JS Frontend
    ├── index.html             ← Login
    ├── dashboard.html
    ├── crops.html
    ├── livestock.html
    ├── inventory.html
    ├── workers.html
    ├── tasks.html
    ├── expenses.html
    ├── reports.html
    ├── css/style.css
    └── js/
        ├── api.js             ← HTTP client with JWT
        └── auth.js            ← Auth utilities
```

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/login` | Login → JWT token |
| `POST` | `/api/auth/register` | Register new user |
| `GET/POST` | `/api/crops/farm/{id}` | Crop CRUD |
| `GET/POST` | `/api/livestock/farm/{id}` | Livestock CRUD |
| `GET/POST` | `/api/inventory/farm/{id}` | Inventory CRUD |
| `GET` | `/api/inventory/farm/{id}/low-stock` | Low stock alert |
| `GET/POST` | `/api/workers/farm/{id}` | Worker CRUD |
| `GET/POST` | `/api/tasks/farm/{id}` | Task CRUD |
| `GET/POST` | `/api/expenses/farm/{id}` | Expense CRUD |
| `GET` | `/api/expenses/farm/{id}/profit-loss` | P&L report |
| `GET` | `/api/dashboard/{farmId}` | Dashboard stats |

All protected endpoints require header:
```
Authorization: Bearer <jwt-token>
```

---

## 🔐 Tech Stack

- **Backend:** Java 17, Spring Boot 3.2, Spring Security, JPA/Hibernate, JWT
- **Database:** MySQL 8 (XAMPP)
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla), Chart.js, FontAwesome
- **Build Tool:** Apache Maven

---

## ⚠️ Troubleshooting

| Problem | Solution |
|---|---|
| `mvn` not found | Add Maven `bin/` folder to your system PATH |
| `java` not found | Install Java 17, set `JAVA_HOME` |
| `Access denied for user root` | Set password to blank in `application.properties` |
| `Connection refused` port 3306 | Start MySQL in XAMPP Control Panel |
| CORS error in browser | Open frontend with Live Server (not `file://`) |
