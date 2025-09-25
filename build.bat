@echo off
echo Building sandbox-oracle project with Java 21...

REM Set JAVA_HOME to use Java 21 LTS
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

REM Create upload directory for file handling
if not exist "C:\temp\uploads" mkdir "C:\temp\uploads"

REM Create build directory
if not exist "build\classes" mkdir "build\classes"
if not exist "build\war" mkdir "build\war"

echo Compiling Java files...

REM Compile all Java files except the problematic PostDAO.java
"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*" -sourcepath src\main\java src\main\java\sandbox\model\*.java
if errorlevel 1 goto :error

"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\sandbox\dao\HistDAO.java src\main\java\sandbox\dao\SearchDAO.java src\main\java\sandbox\dao\UserDAO.java src\main\java\sandbox\dao\PostDAO.java
if errorlevel 1 goto :error

"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\sandbox\web\LoginServlet.java src\main\java\sandbox\web\LoginServletCompany.java src\main\java\sandbox\web\Logout.java src\main\java\sandbox\web\RegisterServlet.java src\main\java\sandbox\web\UserServlet.java
if errorlevel 1 goto :error

"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\test\testConnection.java
if errorlevel 1 goto :error

echo Compilation successful!

echo Copying webapp files...
xcopy "src\main\webapp\*" "build\war\" /E /I /Y

echo Copying compiled classes...
if not exist "build\war\WEB-INF\classes" mkdir "build\war\WEB-INF\classes"
xcopy "build\classes\*" "build\war\WEB-INF\classes\" /E /I /Y

echo Creating WAR file...
cd build\war
"%JAVA_HOME%\bin\jar.exe" -cf ..\sandbox-oracle.war *
if errorlevel 1 (
    echo Warning: jar command failed, creating ZIP instead
    powershell "Compress-Archive -Path * -DestinationPath ..\sandbox-oracle.war -Force"
)
cd ..\..

echo Deploying to Tomcat...
copy "build\sandbox-oracle.war" "C:\Users\johnp\.rsp\redhat-community-server-connector\runtimes\installations\tomcat-11.0.0-M6\apache-tomcat-11.0.0-M6\webapps\"
echo Also deploying to standard Tomcat 10.1...
copy "build\sandbox-oracle.war" "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\"

echo Build completed successfully!
echo WAR file: build\sandbox-oracle.war
echo Access your app at: http://localhost:8080/sandbox-oracle/
goto :end

:error
echo Build failed!
exit /b 1

:end