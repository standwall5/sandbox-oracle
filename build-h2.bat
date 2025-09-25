@echo off
echo Building Sandbox Oracle Job Portal with H2 Database (No PostgreSQL needed!)...

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

REM Create database directory
if not exist "%PROJECT_ROOT%\database" mkdir "%PROJECT_ROOT%\database"

REM Download H2 Database driver if not exists
if not exist "%LIB_DIR%\h2-2.2.224.jar" (
    echo Downloading H2 Database driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar' -OutFile '%LIB_DIR%\h2-2.2.224.jar'"
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
echo H2 Database Info:
echo - Database will be created automatically at: %PROJECT_ROOT%\database\sandbox_jobs.mv.db
echo - No external database setup required!
echo - Database console: http://localhost:8080/h2-console (if H2 console is enabled)
echo - Connection URL: jdbc:h2:./database/sandbox_jobs
echo - Username: sa
echo - Password: (blank)
echo.
echo The database will be populated with sample data automatically on first run!
pause