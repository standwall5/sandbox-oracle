@echo off
setlocal enabledelayedexpansion
echo Building Sandbox Oracle Job Portal with PostgreSQL support...

REM Set variables
set JAVA_HOME=C:\Program Files\Java\jdk-21
set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 11.0.0-M6
set TOMCAT_HOME2=C:\Program Files\Apache Software Foundation\Tomcat 10.1
set PROJECT_ROOT=%cd%
set SRC_DIR=%PROJECT_ROOT%\src\main\java
set WEB_DIR=%PROJECT_ROOT%\src\main\webapp
set BUILD_DIR=%PROJECT_ROOT%\build
set CLASSES_DIR=%BUILD_DIR%\classes
set LIB_DIR=%WEB_DIR%\WEB-INF\lib

REM Clean and create build directories
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%CLASSES_DIR%"

REM Download PostgreSQL JDBC driver if not exists
if not exist "%LIB_DIR%\postgresql-42.7.1.jar" (
    echo Downloading PostgreSQL JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://jdbc.postgresql.org/download/postgresql-42.7.1.jar' -OutFile '%LIB_DIR%\postgresql-42.7.1.jar'"
)

REM Build classpath
set CLASSPATH=%CLASSES_DIR%
for %%f in ("%LIB_DIR%\*.jar") do set CLASSPATH=!CLASSPATH!;%%f

REM Compile Java sources in stages
echo Compiling model classes...
"%JAVA_HOME%\bin\javac" -cp "%CLASSPATH%" -d "%CLASSES_DIR%" "%SRC_DIR%\sandbox\model\*.java"
if errorlevel 1 (
    echo Model compilation failed!
    pause
    exit /b 1
)

echo Compiling config classes...
"%JAVA_HOME%\bin\javac" -cp "%CLASSPATH%" -d "%CLASSES_DIR%" "%SRC_DIR%\sandbox\config\*.java"
if errorlevel 1 (
    echo Config compilation failed!
    pause
    exit /b 1
)

echo Compiling DAO classes...
"%JAVA_HOME%\bin\javac" -cp "%CLASSPATH%" -d "%CLASSES_DIR%" "%SRC_DIR%\sandbox\dao\*.java"
if errorlevel 1 (
    echo DAO compilation failed!
    pause
    exit /b 1
)

echo Compiling servlet classes...
"%JAVA_HOME%\bin\javac" -cp "%CLASSPATH%" -d "%CLASSES_DIR%" "%SRC_DIR%\sandbox\web\*.java"
if errorlevel 1 (
    echo Servlet compilation failed!
    pause
    exit /b 1
)

echo Compiling test classes...
"%JAVA_HOME%\bin\javac" -cp "%CLASSPATH%" -d "%CLASSES_DIR%" "%SRC_DIR%\test\*.java"
if errorlevel 1 (
    echo Test compilation failed (non-critical)
)

REM Create WAR structure
echo Creating WAR file structure...
mkdir "%BUILD_DIR%\war"
mkdir "%BUILD_DIR%\war\WEB-INF"
mkdir "%BUILD_DIR%\war\WEB-INF\classes"
mkdir "%BUILD_DIR%\war\WEB-INF\lib"

REM Copy compiled classes
xcopy /e /i "%CLASSES_DIR%\*" "%BUILD_DIR%\war\WEB-INF\classes\"

REM Copy web content
xcopy /e /i "%WEB_DIR%\*" "%BUILD_DIR%\war\"

REM Create WAR file
echo Creating WAR file...
cd "%BUILD_DIR%\war"
"%JAVA_HOME%\bin\jar" -cvf "..\sandbox-oracle.war" *
cd "%PROJECT_ROOT%"

REM Deploy to Tomcat servers
echo Deploying to Tomcat servers...

REM Deploy to Tomcat 11.0.0-M6
if exist "%TOMCAT_HOME%\webapps\sandbox-oracle.war" del "%TOMCAT_HOME%\webapps\sandbox-oracle.war"
if exist "%TOMCAT_HOME%\webapps\sandbox-oracle" rmdir /s /q "%TOMCAT_HOME%\webapps\sandbox-oracle"
copy "%BUILD_DIR%\sandbox-oracle.war" "%TOMCAT_HOME%\webapps\"

REM Deploy to Tomcat 10.1
if exist "%TOMCAT_HOME2%\webapps\sandbox-oracle.war" del "%TOMCAT_HOME2%\webapps\sandbox-oracle.war"
if exist "%TOMCAT_HOME2%\webapps\sandbox-oracle" rmdir /s /q "%TOMCAT_HOME2%\webapps\sandbox-oracle"
copy "%BUILD_DIR%\sandbox-oracle.war" "%TOMCAT_HOME2%\webapps\"

echo Build and deployment complete!
echo WAR file size:
dir "%BUILD_DIR%\sandbox-oracle.war"
echo.
echo Application URLs:
echo http://localhost:8080/sandbox-oracle/
echo http://localhost:8082/sandbox-oracle/
echo.
echo PostgreSQL Database Setup:
echo 1. Create database 'sandbox_jobs' in PostgreSQL
echo 2. Create user 'sandbox_user' with password 'sandbox_password'
echo 3. Run postgresql_setup.sql in pgAdmin
echo 4. Update database credentials in DatabaseConfig.java if needed
pause