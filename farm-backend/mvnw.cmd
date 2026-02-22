@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------
@echo off

@setlocal
set ERROR_CODE=0

set MAVEN_PROJECTBASEDIR=%~dp0

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%"=="" goto OkJHome

for %%i in (java.exe) do (
  set "javaFound=%%~$PATH:i"
)
if not "%javaFound%"=="" (
  set "JAVA_HOME="
  goto OkJHome
)

echo.
echo Error: JAVA_HOME not found in your environment. >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:OkJHome
@REM ==== END VALIDATION ====

set MAVEN_WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
set MAVEN_WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

@REM Download wrapper jar if not present
if exist %MAVEN_WRAPPER_JAR% goto runWithJava

set MVNW_REPOURL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
echo Downloading Maven Wrapper from: %MVNW_REPOURL%
PowerShell -Command "&{"^
    "$webclient = new-object System.Net.WebClient;"^
    "if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
    "$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
    "}"^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%MVNW_REPOURL%', %MAVEN_WRAPPER_JAR%)"^
    "}"
if "%ERRORLEVEL%"=="0" goto runWithJava

echo Failed to download Maven Wrapper
goto error

:runWithJava
if "%JAVA_HOME%"=="" (
  set JAVA_EXE=java.exe
) else (
  set JAVA_EXE=%JAVA_HOME%/bin/java.exe
)

%JAVA_EXE% -classpath %MAVEN_WRAPPER_JAR% ^
  "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" ^
  org.apache.maven.wrapper.MavenWrapperMain %*

if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & exit /B %ERROR_CODE%
