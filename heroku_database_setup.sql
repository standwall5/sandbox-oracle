-- Peso Job Application PostgreSQL Database Setup Script
-- Run this script after deploying to Heroku with PostgreSQL addon

-- Create tables for the Peso job application system
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    address TEXT,
    profile_image BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS companies (
    company_id SERIAL PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    company_email VARCHAR(100) UNIQUE NOT NULL,
    company_phone VARCHAR(20),
    company_address TEXT,
    company_description TEXT,
    company_website VARCHAR(255),
    company_logo BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS job_posts (
    job_id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES companies(company_id),
    job_title VARCHAR(100) NOT NULL,
    job_description TEXT,
    job_requirements TEXT,
    salary_range VARCHAR(50),
    job_location VARCHAR(100),
    job_type VARCHAR(50),
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    closing_date DATE,
    status VARCHAR(20) DEFAULT 'active'
);

CREATE TABLE IF NOT EXISTS applications (
    application_id SERIAL PRIMARY KEY,
    job_id INTEGER REFERENCES job_posts(job_id),
    user_id INTEGER REFERENCES users(user_id),
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'pending',
    cover_letter TEXT
);

CREATE TABLE IF NOT EXISTS resumes (
    resume_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    objective TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS skills (
    skill_id SERIAL PRIMARY KEY,
    resume_id INTEGER REFERENCES resumes(resume_id),
    skill_name VARCHAR(100) NOT NULL,
    skill_level VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS contact (
    contact_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    contact_type VARCHAR(50),
    contact_value VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS company_contact (
    contact_id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES companies(company_id),
    contact_type VARCHAR(50),
    contact_value VARCHAR(255)
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_job_posts_company ON job_posts(company_id);
CREATE INDEX IF NOT EXISTS idx_applications_job ON applications(job_id);
CREATE INDEX IF NOT EXISTS idx_applications_user ON applications(user_id);
CREATE INDEX IF NOT EXISTS idx_resumes_user ON resumes(user_id);

-- Insert sample data (optional)
INSERT INTO companies (company_name, company_email, company_description) 
VALUES ('Peso Sample Company', 'contact@peso-sample.com', 'A sample company for Peso job application testing')
ON CONFLICT (company_email) DO NOTHING;

COMMIT;