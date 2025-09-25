-- PostgreSQL Database Setup for Peso Job Application System
-- This script creates all tables and inserts sample data

-- Drop existing tables if they exist (in correct order to handle foreign keys)
DROP TABLE IF EXISTS applications CASCADE;
DROP TABLE IF EXISTS skills CASCADE;
DROP TABLE IF EXISTS education CASCADE;
DROP TABLE IF EXISTS work_hist CASCADE;
DROP TABLE IF EXISTS resume CASCADE;
DROP TABLE IF EXISTS job_posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS company_contact CASCADE;
DROP TABLE IF EXISTS contact CASCADE;

-- Create Contact table (base contact information)
CREATE TABLE contact (
    contact_id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    specific_address TEXT,
    district VARCHAR(100),
    barangay VARCHAR(100)
);

-- Create Company Contact table (for company-specific contact info)
CREATE TABLE company_contact (
    contact_id INTEGER PRIMARY KEY REFERENCES contact(contact_id) ON DELETE CASCADE,
    province VARCHAR(100),
    city VARCHAR(100)
);

-- Create Company table
CREATE TABLE company (
    company_id SERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    description TEXT,
    company_icon VARCHAR(500),
    contact_id INTEGER REFERENCES contact(contact_id) ON DELETE CASCADE
);

-- Create Users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    description TEXT,
    contact_id INTEGER REFERENCES contact(contact_id) ON DELETE CASCADE,
    password VARCHAR(255) NOT NULL,
    icon VARCHAR(500),
    birth_date DATE,
    employee_id INTEGER DEFAULT 1
);

-- Create Job Posts table
CREATE TABLE job_posts (
    post_id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES company(company_id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    address TEXT,
    category VARCHAR(100),
    post_date DATE DEFAULT CURRENT_DATE
);

-- Create Resume table
CREATE TABLE resume (
    resume_id SERIAL PRIMARY KEY,
    address TEXT,
    workhist TEXT,
    educhist TEXT,
    skills TEXT,
    resdesc TEXT,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Work History table
CREATE TABLE work_hist (
    work_hist_id SERIAL PRIMARY KEY,
    resume_id INTEGER REFERENCES resume(resume_id) ON DELETE CASCADE,
    job_title VARCHAR(255),
    company_name VARCHAR(255),
    start_year INTEGER,
    end_year INTEGER
);

-- Create Education table
CREATE TABLE education (
    education_id SERIAL PRIMARY KEY,
    resume_id INTEGER REFERENCES resume(resume_id) ON DELETE CASCADE,
    school_name VARCHAR(255),
    degree VARCHAR(255),
    start_year INTEGER,
    end_year INTEGER
);

-- Create Skills table
CREATE TABLE skills (
    skill_id SERIAL PRIMARY KEY,
    resume_id INTEGER REFERENCES resume(resume_id) ON DELETE CASCADE,
    name VARCHAR(255)
);

-- Create Applications table (job applications)
CREATE TABLE applications (
    application_id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES job_posts(post_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    application_date DATE DEFAULT CURRENT_DATE,
    UNIQUE(post_id, user_id)
);

-- Insert sample contact data
INSERT INTO contact (email, contact_number, specific_address, district, barangay) VALUES
('john.doe@email.com', '09171234567', '123 Main St', 'District 1', 'Barangay Centro'),
('jane.smith@email.com', '09187654321', '456 Oak Ave', 'District 2', 'Barangay Norte'),
('mike.johnson@email.com', '09191112222', '789 Elm St', 'District 1', 'Barangay Sur'),
('sarah.williams@email.com', '09203334444', '321 Pine Rd', 'District 3', 'Barangay Este'),
('david.brown@email.com', '09155556666', '654 Cedar Ln', 'District 2', 'Barangay Oeste'),
('techcorp@company.com', '02-8123-4567', 'TechCorp Tower, BGC', 'Taguig', 'Fort Bonifacio'),
('innovate@startup.com', '02-8987-6543', 'Innovation Hub, Makati', 'Makati', 'Poblacion'),
('global@solutions.com', '02-8555-1234', 'Global Plaza, Ortigas', 'Pasig', 'Ortigas Center'),
('creative@agency.com', '02-8777-8888', 'Creative Spaces, QC', 'Quezon City', 'Diliman'),
('finance@corp.com', '02-8999-0000', 'Finance Tower, Alabang', 'Muntinlupa', 'Ayala Alabang');

-- Insert company contact data
INSERT INTO company_contact (contact_id, province, city) VALUES
(6, 'Metro Manila', 'Taguig'),
(7, 'Metro Manila', 'Makati'),
(8, 'Metro Manila', 'Pasig'),
(9, 'Metro Manila', 'Quezon City'),
(10, 'Metro Manila', 'Muntinlupa');

-- Insert sample companies
INSERT INTO company (company_name, description, company_icon, contact_id) VALUES
('TechCorp Solutions', 'Leading technology solutions provider specializing in enterprise software development and digital transformation.', 'company_1.jpg', 6),
('Innovate Startup', 'Fast-growing startup focused on AI and machine learning solutions for businesses.', 'company_2.jpg', 7),
('Global Systems Inc', 'International consulting firm providing strategic business solutions and system integration services.', 'company_3.jpg', 8),
('Creative Digital Agency', 'Full-service digital marketing agency specializing in web design, branding, and social media marketing.', 'company_4.jpg', 9),
('Finance Pro Corp', 'Financial services company offering accounting, auditing, and business advisory services.', 'company_5.jpg', 10);

-- Insert sample users
INSERT INTO users (first_name, last_name, description, contact_id, password, icon, birth_date, employee_id) VALUES
('John', 'Doe', 'Experienced software developer with 5+ years in full-stack development. Passionate about creating innovative solutions.', 1, 'password123', 'user_1.jpg', '1990-05-15', 1),
('Jane', 'Smith', 'Digital marketing specialist with expertise in SEO, content marketing, and social media strategy.', 2, 'password123', 'user_2.jpg', '1985-08-22', 1),
('Mike', 'Johnson', 'Senior project manager with PMP certification and experience leading cross-functional teams.', 3, 'password123', 'user_3.jpg', '1988-12-03', 1),
('Sarah', 'Williams', 'UX/UI designer focused on creating user-centered designs and improving user experience.', 4, 'password123', 'user_4.jpg', '1992-03-18', 1),
('David', 'Brown', 'Data analyst with strong background in Python, SQL, and machine learning algorithms.', 5, 'password123', 'user_5.jpg', '1987-11-25', 1);

-- Insert sample job posts
INSERT INTO job_posts (company_id, title, description, address, category, post_date) VALUES
(1, 'Senior Full Stack Developer', 'We are looking for an experienced full-stack developer to join our team. Must have experience with Java, React, and PostgreSQL.', 'BGC, Taguig City', 'Information Technology', '2024-01-15'),
(1, 'DevOps Engineer', 'Seeking a DevOps engineer to help streamline our deployment processes and manage cloud infrastructure.', 'BGC, Taguig City', 'Information Technology', '2024-01-20'),
(2, 'AI/ML Engineer', 'Join our innovative team to develop cutting-edge AI solutions. Experience with Python, TensorFlow, and PyTorch required.', 'Makati City', 'Information Technology', '2024-01-18'),
(2, 'Product Manager', 'Looking for a product manager to drive product strategy and work closely with engineering teams.', 'Makati City', 'Management', '2024-01-22'),
(3, 'Business Analyst', 'Seeking a business analyst to help improve business processes and requirements gathering.', 'Ortigas, Pasig City', 'Business Analysis', '2024-01-25'),
(3, 'Systems Integration Specialist', 'Need an experienced specialist to handle complex system integrations for enterprise clients.', 'Ortigas, Pasig City', 'Information Technology', '2024-01-28'),
(4, 'Graphic Designer', 'Creative graphic designer needed for various marketing materials and brand development.', 'Quezon City', 'Design', '2024-02-01'),
(4, 'Digital Marketing Manager', 'Experienced digital marketing manager to lead our marketing campaigns and strategy.', 'Quezon City', 'Marketing', '2024-02-03'),
(5, 'Financial Analyst', 'Looking for a detail-oriented financial analyst to support our growing client base.', 'Alabang, Muntinlupa', 'Finance', '2024-02-05'),
(5, 'Senior Accountant', 'CPA-licensed senior accountant needed for audit and accounting services.', 'Alabang, Muntinlupa', 'Finance', '2024-02-08');

-- Insert sample resumes
INSERT INTO resume (address, workhist, educhist, skills, resdesc, user_id) VALUES
('123 Main St, District 1, Barangay Centro', 'Software Developer at TechStart (2019-2023)', 'BS Computer Science, University of the Philippines (2015-2019)', 'Java, React, PostgreSQL, Spring Boot', 'Passionate full-stack developer with experience in enterprise applications', 1),
('456 Oak Ave, District 2, Barangay Norte', 'Marketing Specialist at DigitalCorp (2020-2023)', 'BS Marketing, Ateneo de Manila University (2016-2020)', 'SEO, Content Marketing, Google Analytics, Social Media', 'Results-driven marketing professional with proven track record', 2),
('789 Elm St, District 1, Barangay Sur', 'Project Manager at BuildCorp (2018-2023)', 'BS Industrial Engineering, De La Salle University (2014-2018)', 'Project Management, Agile, Scrum, Risk Management', 'Experienced project manager with PMP certification', 3),
('321 Pine Rd, District 3, Barangay Este', 'UI/UX Designer at CreativeLab (2019-2023)', 'BS Fine Arts, University of Santo Tomas (2015-2019)', 'Figma, Adobe Creative Suite, User Research, Prototyping', 'User-centered designer passionate about creating intuitive experiences', 4),
('654 Cedar Ln, District 2, Barangay Oeste', 'Data Analyst at DataCorp (2020-2023)', 'BS Statistics, University of the Philippines (2016-2020)', 'Python, SQL, Machine Learning, Tableau, R', 'Data-driven analyst with expertise in predictive modeling', 5);

-- Insert sample work history
INSERT INTO work_hist (resume_id, job_title, company_name, start_year, end_year) VALUES
(1, 'Software Developer', 'TechStart Inc', 2019, 2023),
(1, 'Junior Developer', 'CodeCraft', 2018, 2019),
(2, 'Marketing Specialist', 'DigitalCorp', 2020, 2023),
(2, 'Marketing Assistant', 'BrandWorks', 2019, 2020),
(3, 'Project Manager', 'BuildCorp', 2018, 2023),
(3, 'Assistant Manager', 'ConstructCo', 2017, 2018),
(4, 'UI/UX Designer', 'CreativeLab', 2019, 2023),
(4, 'Graphic Designer', 'DesignStudio', 2018, 2019),
(5, 'Data Analyst', 'DataCorp', 2020, 2023),
(5, 'Junior Analyst', 'InfoSystems', 2019, 2020);

-- Insert sample education
INSERT INTO education (resume_id, school_name, degree, start_year, end_year) VALUES
(1, 'University of the Philippines', 'BS Computer Science', 2015, 2019),
(1, 'TechCert Institute', 'Java Certification', 2019, 2019),
(2, 'Ateneo de Manila University', 'BS Marketing', 2016, 2020),
(2, 'Google', 'Digital Marketing Certificate', 2020, 2020),
(3, 'De La Salle University', 'BS Industrial Engineering', 2014, 2018),
(3, 'PMI', 'PMP Certification', 2019, 2019),
(4, 'University of Santo Tomas', 'BS Fine Arts', 2015, 2019),
(4, 'Coursera', 'UX Design Certificate', 2019, 2019),
(5, 'University of the Philippines', 'BS Statistics', 2016, 2020),
(5, 'DataCamp', 'Data Science Certificate', 2020, 2020);

-- Insert sample skills
INSERT INTO skills (resume_id, name) VALUES
(1, 'Java'), (1, 'React'), (1, 'PostgreSQL'), (1, 'Spring Boot'), (1, 'Git'),
(2, 'SEO'), (2, 'Content Marketing'), (2, 'Google Analytics'), (2, 'Social Media Marketing'), (2, 'Email Marketing'),
(3, 'Project Management'), (3, 'Agile'), (3, 'Scrum'), (3, 'Risk Management'), (3, 'Microsoft Project'),
(4, 'Figma'), (4, 'Adobe Photoshop'), (4, 'Adobe Illustrator'), (4, 'User Research'), (4, 'Prototyping'),
(5, 'Python'), (5, 'SQL'), (5, 'Machine Learning'), (5, 'Tableau'), (5, 'R Programming');

-- Insert sample applications
INSERT INTO applications (post_id, user_id, application_date) VALUES
(1, 1, '2024-01-16'),  -- John applied for Senior Full Stack Developer
(3, 5, '2024-01-19'),  -- David applied for AI/ML Engineer
(7, 4, '2024-02-02'),  -- Sarah applied for Graphic Designer
(9, 5, '2024-02-06'),  -- David applied for Financial Analyst
(2, 1, '2024-01-21'),  -- John applied for DevOps Engineer
(8, 2, '2024-02-04'),  -- Jane applied for Digital Marketing Manager
(5, 3, '2024-01-26'),  -- Mike applied for Business Analyst
(4, 3, '2024-01-23');  -- Mike applied for Product Manager

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(contact_id);
CREATE INDEX idx_job_posts_company ON job_posts(company_id);
CREATE INDEX idx_applications_user ON applications(user_id);
CREATE INDEX idx_applications_post ON applications(post_id);
CREATE INDEX idx_resume_user ON resume(user_id);
CREATE INDEX idx_work_hist_resume ON work_hist(resume_id);
CREATE INDEX idx_education_resume ON education(resume_id);
CREATE INDEX idx_skills_resume ON skills(resume_id);

-- Create views for commonly used queries
CREATE VIEW user_profile AS
SELECT 
    u.user_id,
    u.first_name,
    u.last_name,
    u.description,
    u.icon,
    u.birth_date,
    c.email,
    c.contact_number,
    c.specific_address,
    c.district,
    c.barangay
FROM users u
JOIN contact c ON u.contact_id = c.contact_id;

CREATE VIEW company_profile AS
SELECT 
    comp.company_id,
    comp.company_name,
    comp.description,
    comp.company_icon,
    c.email,
    c.contact_number,
    cc.province,
    cc.city,
    c.specific_address
FROM company comp
JOIN contact c ON comp.contact_id = c.contact_id
JOIN company_contact cc ON c.contact_id = cc.contact_id;

CREATE VIEW job_listings AS
SELECT 
    jp.post_id,
    jp.title,
    jp.description,
    jp.address,
    jp.category,
    jp.post_date,
    c.company_name,
    c.company_icon
FROM job_posts jp
JOIN company c ON jp.company_id = c.company_id;

-- Grant permissions (adjust as needed for your PostgreSQL setup)
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO your_username;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO your_username;

-- Display some sample data to verify
SELECT 'Companies' as table_name, count(*) as record_count FROM company
UNION ALL
SELECT 'Users', count(*) FROM users
UNION ALL
SELECT 'Job Posts', count(*) FROM job_posts
UNION ALL
SELECT 'Applications', count(*) FROM applications
UNION ALL
SELECT 'Resumes', count(*) FROM resume;

COMMIT;