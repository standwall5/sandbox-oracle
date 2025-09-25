# Heroku Deployment Guide for Peso Job Application System

## Prerequisites

1. **Heroku CLI**: Install from https://devcenter.heroku.com/articles/heroku-cli
2. **Git**: Make sure Git is installed and this project is in a Git repository
3. **Maven**: Ensure Maven is installed (you already have this working)
4. **Java 21**: Confirm Java 21 is installed

## Step-by-Step Deployment Process

### 1. Login to Heroku

```bash
heroku login
```

### 2. Create a New Heroku Application (Philippines Region)

```bash
heroku create peso-jobs-app --region eu
```

Replace `peso-jobs-app` with your preferred app name (must be unique across Heroku).

**Region Note**: For Philippines users, use `--region us` for closest performance. Heroku regions available:

- `us` (Virginia, USA) - Closest to Philippines
- `eu` (Dublin, Ireland) - Alternative option

For optimal Philippines performance, you can also consider these alternatives:

- **Railway**: Supports Asia-Pacific regions
- **Google Cloud Run**: Has asia-southeast1 (Singapore) region
- **AWS Lambda/Elastic Beanstalk**: ap-southeast-1 (Singapore) region

### 3. Add PostgreSQL Database

```bash
heroku addons:create heroku-postgresql:essential-0 --app peso-jobs-app
```

**Database Region**: The PostgreSQL addon will be created in the same region as your app (US region for Philippines users).

### 4. Get Database Connection Information

```bash
heroku config --app peso-jobs-app
```

This will show your `DATABASE_URL` which your app will automatically use.

### 5. Initialize the Database Schema

After deployment, you'll need to run the database setup script. You can do this via Heroku CLI:

```bash
heroku pg:psql --app peso-jobs-app < heroku_database_setup.sql
```

### 6. Commit All Changes to Git

```bash
git add .
git commit -m "Deploy Peso Job Application System to Heroku with Java 21 and PostgreSQL"
```

### 7. Deploy to Heroku

```bash
git push heroku main
```

If your default branch is `master`, use:

```bash
git push heroku master
```

### 8. Open Your Application

```bash
heroku open --app peso-jobs-app
```

## Configuration Files Created

### 1. `pom.xml`

- Configured for Java 21
- Includes all necessary dependencies
- Added Heroku Maven plugin
- Configured webapp-runner for deployment

### 2. `Procfile`

```
web: java -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
```

### 3. `system.properties`

```
java.runtime.version=21
```

### 4. `HerokuDatabaseConfig.java`

- Automatically detects Heroku environment
- Uses `DATABASE_URL` environment variable
- Falls back to local PostgreSQL for development

## Database Configuration

Your app will automatically switch between:

- **Local Development**: PostgreSQL at `localhost:5432/sandbox-application`
- **Heroku Production**: Uses `DATABASE_URL` environment variable

## Environment Variables

Heroku automatically provides:

- `DATABASE_URL`: PostgreSQL connection string
- `PORT`: The port your app should listen on

## Troubleshooting

### Check Logs

```bash
heroku logs --tail --app peso-jobs-app
```

### Restart the Application

```bash
heroku restart --app peso-jobs-app
```

### Check Configuration

```bash
heroku config --app peso-jobs-app
```

### Database Issues

```bash
# Connect to database
heroku pg:psql --app peso-jobs-app

# Check database info
heroku pg:info --app peso-jobs-app
```

## Local Testing

Before deploying, you can test locally:

```bash
# Build the WAR file
mvn clean package

# Run with webapp-runner locally
java -jar target/dependency/webapp-runner.jar --port 8080 target/*.war
```

## Post-Deployment Steps

1. **Initialize Database**: Run the SQL setup script
2. **Test All Features**: Verify user registration, login, job posting, etc.
3. **Monitor Logs**: Check for any errors in the Heroku logs
4. **Configure Domain**: (Optional) Add a custom domain

## File Upload Configuration

The app is configured to handle file uploads with:

- Max file size: 10MB
- Max request size: 50MB
- Temporary directory: Automatically managed by Heroku

## Cost Considerations

- **Essential PostgreSQL**: $5/month
- **Web Dyno**: Free tier available, $7/month for hobby tier
- **Total estimated cost**: $5-12/month depending on dyno selection

## Success Indicators

Your deployment is successful when:

1. `git push heroku main` completes without errors
2. `heroku open` shows your application
3. You can access the homepage at your Heroku URL
4. Database operations work (user registration, login, etc.)

## Next Steps After Deployment

1. Set up continuous deployment from GitHub
2. Configure monitoring and alerts
3. Set up automated backups for the database
4. Consider adding Redis for session management (optional)
5. Add custom domain and SSL certificate

Your application will be available at: `https://peso-jobs-app.herokuapp.com`
(Replace with your actual app name)
