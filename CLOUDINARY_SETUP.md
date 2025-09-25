# Cloudinary Image Hosting Setup

This application uses Cloudinary for image hosting (profile pictures, company logos, and document uploads).

## 🚀 Quick Setup

### 1. Create Cloudinary Account
1. Go to [https://cloudinary.com/](https://cloudinary.com/)
2. Sign up for a **FREE account**
3. After signup, go to your Dashboard

### 2. Get Your Credentials
From your Cloudinary Dashboard, copy these values:
- **Cloud Name** (e.g., `your-cloud-name`)
- **API Key** (e.g., `123456789012345`)
- **API Secret** (e.g., `abcdef123456789`)

### 3. Configure Environment Variables

#### For Heroku (Production):
```bash
heroku config:set CLOUDINARY_CLOUD_NAME=your-cloud-name
heroku config:set CLOUDINARY_API_KEY=123456789012345
heroku config:set CLOUDINARY_API_SECRET=abcdef123456789
```

#### For Local Development:
Create a `.env` file in your project root (or set system environment variables):
```env
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=123456789012345
CLOUDINARY_API_SECRET=abcdef123456789
```

### 4. Alternative: Hardcode for Testing (NOT RECOMMENDED for production)
Edit `src/main/java/sandbox/config/CloudinaryConfig.java`:
```java
// Replace these lines with your actual credentials
if (cloudName == null) cloudName = "your-cloud-name"; 
if (apiKey == null) apiKey = "123456789012345"; 
if (apiSecret == null) apiSecret = "abcdef123456789";
```

## 📸 Features

### ✅ What's Included:
- **User Profile Pictures**: Automatically resized to 300x300px
- **Company Logos**: Optimized for 400x300px display
- **Resume/Document Upload**: Supports PDFs, DOCs, etc.
- **Automatic Optimization**: Images are compressed and optimized
- **CDN Delivery**: Fast global image delivery
- **Cloud Storage**: No more lost images on Heroku restarts!

### 🎯 Image Transformations:
- **Profile Pictures**: Square crop, 300x300px, JPEG format
- **Company Logos**: Fit resize, 400x300px, maintains aspect ratio
- **Auto Quality**: Cloudinary automatically optimizes image quality

## 🔧 Technical Details

### Cloudinary URLs
Images are stored with the following structure:
```
https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/peso-app/users/user_profile_123_1234567890.jpg
https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/peso-app/companies/company_logo_456_1234567890.jpg
https://res.cloudinary.com/your-cloud-name/raw/upload/v1234567890/peso-app/resumes/resume_789_1234567890.pdf
```

### Database Changes
- **users.icon**: Now stores full Cloudinary URL instead of filename
- **company.company_icon**: Now stores full Cloudinary URL instead of filename

### Folder Organization
- `peso-app/users/` - User profile pictures
- `peso-app/companies/` - Company logos  
- `peso-app/resumes/` - Resume/document files

## 🆓 Free Tier Limits
Cloudinary free tier includes:
- **25GB** storage
- **25GB** monthly bandwidth
- **1,000** transformations per month
- Perfect for small to medium applications!

## 🔍 Testing
After setup, upload a profile picture or company logo to test the integration. Check your Cloudinary Dashboard to see uploaded images.

## 🚨 Important Notes
1. **Never commit API secrets** to version control
2. **Use environment variables** for all deployments
3. **Old local images** will no longer work after Cloudinary migration
4. **Database URLs** will change from filenames to full Cloudinary URLs

## 📞 Support
If you encounter issues:
1. Check Cloudinary Dashboard for upload logs
2. Verify environment variables are set correctly
3. Check application logs for error messages