# PostgreSQL Database Setup Guide

## Prerequisites

- PostgreSQL installed and running
- pgAdmin or psql command line tool
- PostgreSQL JDBC driver (will be downloaded automatically by build script)

## Step 1: Create Database and User

Connect to PostgreSQL as superuser and run these commands:

```sql
-- Create database
CREATE DATABASE sandbox_jobs;

-- Create user
CREATE USER sandbox_user WITH ENCRYPTED PASSWORD 'sandbox_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE sandbox_jobs TO sandbox_user;

-- Connect to the new database
\c sandbox_jobs

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO sandbox_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO sandbox_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO sandbox_user;
```

## Step 2: Run the Schema Script

1. Open pgAdmin
2. Connect to your PostgreSQL server
3. Navigate to the `sandbox_jobs` database
4. Open the Query Tool
5. Copy and paste the entire contents of `postgresql_setup.sql`
6. Execute the script

## Step 3: Verify Data

After running the script, you should see:

- 5 Companies
- 5 Users
- 10 Job Posts
- 8 Applications
- 5 Resumes

## Step 4: Update Java Application

The application is configured to connect with these settings:

- **Database**: `localhost:5432/sandbox_jobs`
- **Username**: `sandbox_user`
- **Password**: `sandbox_password`

If you need different settings, update the `DatabaseConfig.java` file.

## Step 5: Build and Deploy

Run the PostgreSQL build script:

```cmd
build-postgresql.bat
```

This will:

1. Download PostgreSQL JDBC driver
2. Compile all Java classes with PostgreSQL support
3. Create and deploy WAR file to Tomcat
4. Provide URLs to test the application

## Sample Data Overview

### Users:

- John Doe (Software Developer)
- Jane Smith (Digital Marketing)
- Mike Johnson (Project Manager)
- Sarah Williams (UX/UI Designer)
- David Brown (Data Analyst)

### Companies:

- TechCorp Solutions
- Innovate Startup
- Global Systems Inc
- Creative Digital Agency
- Finance Pro Corp

### Job Categories:

- Information Technology
- Marketing
- Management
- Design
- Finance
- Business Analysis

All users have complete profiles with work history, education, and skills data for realistic testing.

## Troubleshooting

**Connection Issues:**

- Verify PostgreSQL is running
- Check database name, username, and password
- Ensure PostgreSQL accepts connections on localhost:5432

**Permission Issues:**

- Make sure sandbox_user has proper privileges
- Grant sequence privileges if auto-increment fails

**JDBC Driver Issues:**

- Ensure postgresql-42.7.1.jar is in WEB-INF/lib
- Check Java classpath includes the driver
