#!/usr/bin/env pwsh
# =============================================
# AgriFarm - One-Click Setup & Run Script
# Usage: .\start.ps1
# =============================================

$ErrorActionPreference = "Stop"

function Write-Step($msg) { Write-Host "`n✅ $msg" -ForegroundColor Green }
function Write-Info($msg) { Write-Host "   $msg" -ForegroundColor Cyan }
function Write-Warn($msg) { Write-Host "⚠️  $msg" -ForegroundColor Yellow }

Write-Host "`n🌾 AgriFarm - Setup & Run Script" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor DarkGreen

# ── Step 1: Check / Install Java ──────────────────────────────
Write-Step "Checking Java..."
$javaHome = $null
$possibleJava = @(
    "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot",
    "C:\Program Files\Eclipse Adoptium\jdk-17*",
    "C:\Program Files\Java\jdk-17*"
)
foreach ($p in $possibleJava) {
    $match = Get-Item $p -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($match) { $javaHome = $match.FullName; break }
}

if (-not $javaHome) {
    Write-Warn "Java 17 not found. Installing via winget..."
    winget install Microsoft.OpenJDK.17 --silent --accept-package-agreements --accept-source-agreements
    $javaHome = "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot"
}
$env:JAVA_HOME = $javaHome
$env:PATH += ";$javaHome\bin"
Write-Info "Java: $javaHome"

# ── Step 2: Check / Install Maven ─────────────────────────────
Write-Step "Checking Maven..."
$mavenBin = "C:\maven\apache-maven-3.9.6\bin"
if (-not (Test-Path "$mavenBin\mvn.cmd")) {
    Write-Warn "Maven not found. Downloading..."
    Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip" `
        -OutFile "$env:TEMP\maven.zip" -UseBasicParsing
    Expand-Archive -Path "$env:TEMP\maven.zip" -DestinationPath "C:\maven" -Force
    Write-Info "Maven installed to C:\maven"
}
$env:PATH += ";$mavenBin"
Write-Info "Maven: $mavenBin"

# ── Step 3: Check MySQL is running ────────────────────────────
Write-Step "Checking MySQL (port 3306)..."
$mysql = Test-NetConnection -ComputerName localhost -Port 3306 -WarningAction SilentlyContinue
if (-not $mysql.TcpTestSucceeded) {
    Write-Host "`n❌ MySQL is NOT running!" -ForegroundColor Red
    Write-Host "   Please open XAMPP Control Panel and Start MySQL, then run this script again." -ForegroundColor Yellow
    exit 1
}
Write-Info "MySQL is running on port 3306 ✅"

# ── Step 4: Create database if not exists ────────────────────
Write-Step "Setting up database..."
$mysqlExe = Get-Command mysql -ErrorAction SilentlyContinue
if ($mysqlExe) {
    Write-Info "Creating database 'farm_management'..."
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS farm_management CHARACTER SET utf8mb4;" 2>$null
    
    $schemaPath = "$PSScriptRoot\farm-backend\src\main\resources\schema.sql"
    Write-Info "Importing schema..."
    mysql -u root farm_management < $schemaPath 2>$null
    Write-Info "Database ready ✅"
} else {
    Write-Warn "mysql.exe not in PATH. Skipping auto-import."
    Write-Info "Please manually import schema.sql via phpMyAdmin at http://localhost/phpmyadmin"
}

# ── Step 5: Run Spring Boot Backend ──────────────────────────
Write-Step "Starting Spring Boot backend..."
Write-Info "Downloading Maven dependencies (first run may take 2-5 minutes)..."
Write-Info "Backend will start at http://localhost:8080"
Write-Host ""
Write-Host "📱 Once started, open farm-frontend/index.html with VS Code Live Server" -ForegroundColor Cyan
Write-Host "🔑 Login: admin / admin123" -ForegroundColor Cyan
Write-Host ""

Set-Location "$PSScriptRoot\farm-backend"
& "$mavenBin\mvn.cmd" clean spring-boot:run
