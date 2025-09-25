package sandbox.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import sandbox.config.CloudinaryConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Image Upload Service using Cloudinary
 * Handles all image upload operations for users and companies
 */
public class ImageUploadService {
    
    private static final Cloudinary cloudinary = CloudinaryConfig.getInstance();
    
    /**
     * Upload user profile image to Cloudinary
     * @param fileContent InputStream of the image file
     * @param userId User ID for unique naming
     * @return Cloudinary image URL if successful, null if failed
     */
    public static String uploadUserImage(InputStream fileContent, int userId) {
        try {
            // Check if Cloudinary is properly configured
            if (!CloudinaryConfig.isConfigured()) {
                System.err.println("Cloudinary is not properly configured. Check environment variables.");
                return null;
            }
            
            String publicId = "user_profile_" + userId + "_" + System.currentTimeMillis();
            
            // Upload parameters according to official documentation
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "peso-app/users",
                "width", 300,
                "height", 300,
                "crop", "fill",
                "quality", "auto",
                "format", "jpg",
                "overwrite", true,
                "resource_type", "image"
            );
            
            Map uploadResult = cloudinary.uploader().upload(fileContent, uploadParams);
            
            String secureUrl = (String) uploadResult.get("secure_url");
            System.out.println("Successfully uploaded user image: " + secureUrl);
            return secureUrl;
            
        } catch (Exception e) {
            System.err.println("Failed to upload user image for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Upload company logo to Cloudinary
     * @param fileContent InputStream of the image file
     * @param companyId Company ID for unique naming
     * @return Cloudinary image URL if successful, null if failed
     */
    public static String uploadCompanyImage(InputStream fileContent, int companyId) {
        try {
            // Check if Cloudinary is properly configured
            if (!CloudinaryConfig.isConfigured()) {
                System.err.println("Cloudinary is not properly configured. Check environment variables.");
                return null;
            }
            
            String publicId = "company_logo_" + companyId + "_" + System.currentTimeMillis();
            
            // Upload parameters according to official documentation
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "peso-app/companies",
                "width", 400,
                "height", 300,
                "crop", "fit",
                "quality", "auto",
                "format", "jpg",
                "overwrite", true,
                "resource_type", "image"
            );
            
            Map uploadResult = cloudinary.uploader().upload(fileContent, uploadParams);
            
            String secureUrl = (String) uploadResult.get("secure_url");
            System.out.println("Successfully uploaded company image: " + secureUrl);
            return secureUrl;
            
        } catch (Exception e) {
            System.err.println("Failed to upload company image for company " + companyId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Upload resume/document to Cloudinary
     * @param fileContent InputStream of the file
     * @param userId User ID for unique naming
     * @param fileName Original filename
     * @return Cloudinary file URL if successful, null if failed
     */
    public static String uploadResume(InputStream fileContent, int userId, String fileName) {
        try {
            String publicId = "resume_" + userId + "_" + System.currentTimeMillis();
            
            Map uploadResult = cloudinary.uploader().upload(fileContent, ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "peso-app/resumes",
                "resource_type", "auto" // Allows PDF, DOC, etc.
            ));
            
            return (String) uploadResult.get("secure_url");
            
        } catch (IOException e) {
            System.err.println("Failed to upload resume: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Delete image from Cloudinary
     * @param imageUrl Full Cloudinary URL
     * @return true if deletion was successful
     */
    public static boolean deleteImage(String imageUrl) {
        try {
            // Extract public_id from URL
            String publicId = extractPublicIdFromUrl(imageUrl);
            if (publicId != null) {
                Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                return "ok".equals(result.get("result"));
            }
            return false;
        } catch (IOException e) {
            System.err.println("Failed to delete image: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extract public_id from Cloudinary URL
     * @param imageUrl Full Cloudinary URL
     * @return public_id or null if extraction fails
     */
    private static String extractPublicIdFromUrl(String imageUrl) {
        try {
            // Example URL: https://res.cloudinary.com/your-cloud/image/upload/v1234567890/folder/public_id.jpg
            if (imageUrl != null && imageUrl.contains("cloudinary.com")) {
                String[] parts = imageUrl.split("/");
                for (int i = 0; i < parts.length; i++) {
                    if ("upload".equals(parts[i]) && i + 2 < parts.length) {
                        String publicIdWithExtension = parts[i + 2];
                        // Remove file extension
                        int dotIndex = publicIdWithExtension.lastIndexOf('.');
                        return dotIndex > 0 ? publicIdWithExtension.substring(0, dotIndex) : publicIdWithExtension;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to extract public_id from URL: " + imageUrl);
        }
        return null;
    }
    
    /**
     * Get optimized image URL with transformations
     * @param originalUrl Original Cloudinary URL
     * @param width Desired width
     * @param height Desired height
     * @return Optimized image URL
     */
    public static String getOptimizedImageUrl(String originalUrl, int width, int height) {
        if (originalUrl == null || !originalUrl.contains("cloudinary.com")) {
            return originalUrl;
        }
        
        try {
            // Insert transformation parameters into the URL
            String transformation = "w_" + width + ",h_" + height + ",c_fill,q_auto,f_auto";
            return originalUrl.replace("/upload/", "/upload/" + transformation + "/");
        } catch (Exception e) {
            System.err.println("Failed to create optimized URL: " + e.getMessage());
            return originalUrl;
        }
    }
}