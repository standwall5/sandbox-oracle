@echo off
echo Building sandbox-oracle project with Java 21 LTS...

REM Set JAVA_HOME explicitly to Java 21 LTS
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

echo Using Java 21 LTS: %JAVA_HOME%
"%JAVA_HOME%\bin\java" -version

REM Create build directory
if not exist "build\classes" mkdir "build\classes"
if not exist "build\war" mkdir "build\war"

echo Compiling Java files with Java 21...

REM Clean previous builds
if exist "build\classes\*" del /q /s "build\classes\*"

REM Compile all Java files
echo Compiling model classes...
"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*" -sourcepath src\main\java src\main\java\sandbox\model\*.java
if errorlevel 1 goto :error

echo Compiling DAO classes...
"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\sandbox\dao\HistDAO.java src\main\java\sandbox\dao\SearchDAO.java src\main\java\sandbox\dao\UserDAO.java src\main\java\sandbox\dao\PostDAO.java
if errorlevel 1 goto :error

echo Compiling servlet classes...
"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\sandbox\web\LoginServlet.java src\main\java\sandbox\web\LoginServletCompany.java src\main\java\sandbox\web\Logout.java src\main\java\sandbox\web\RegisterServlet.java src\main\java\sandbox\web\UserServlet.java
if errorlevel 1 goto :error

echo Compiling test classes...
"%JAVA_HOME%\bin\javac" -d build\classes -cp "src\main\webapp\WEB-INF\lib\*;build\classes" -sourcepath src\main\java src\main\java\test\testConnection.java
if errorlevel 1 goto :error

echo Compilation successful with Java 21!

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

echo.
echo ========================================
echo BUILD COMPLETED SUCCESSFULLY WITH JAVA 21!
echo ========================================
echo WAR file: build\sandbox-oracle.war
echo Access your app at: http://localhost:8080/sandbox-oracle/
echo.
echo Java 21 LTS Features Available:
echo - Enhanced pattern matching
echo - Record patterns
echo - String templates (preview)
echo - Virtual threads
echo - Improved performance and security
echo ========================================
goto :end

:error
echo.
echo ========================================
echo BUILD FAILED!
echo ========================================
echo Please check the compilation errors above.
echo Make sure Java 21 is properly installed.
exit /b 1

:end