@echo off
echo Building and deploying to Heroku...

echo Step 1: Building with Maven...
call mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo Maven build failed!
    exit /b 1
)

echo Step 2: Deploying to Heroku...
echo Make sure you have:
echo 1. Heroku CLI installed
echo 2. Logged in to Heroku (heroku login)
echo 3. Created a Heroku app (heroku create your-app-name)
echo 4. Added PostgreSQL addon (heroku addons:create heroku-postgresql:essential-0)

echo Run these commands manually:
echo heroku create peso-jobs-app
echo heroku addons:create heroku-postgresql:essential-0
echo git add .
echo git commit -m "Deploy to Heroku"
echo git push heroku main

echo Build completed successfully!
echo WAR file: target/sandbox-oracle.war
pause