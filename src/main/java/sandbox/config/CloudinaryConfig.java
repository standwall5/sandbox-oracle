package sandbox.config;

import com.cloudinary.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Cloudinary Configuration for Image Hosting
 * Handles cloud-based image uploads and management
 */
public class CloudinaryConfig {
    private static Cloudinary cloudinary;
    
    static {
        // Initialize Cloudinary with environment variables following official documentation
        // First, try CLOUDINARY_URL environment variable (preferred by Cloudinary)
        String cloudinaryUrl = System.getenv("CLOUDINARY_URL");
        
        if (cloudinaryUrl != null) {
            // Use CLOUDINARY_URL if available (format: cloudinary://api_key:api_secret@cloud_name)
            cloudinary = new Cloudinary(cloudinaryUrl);
        } else {
            // Fall back to individual environment variables
            String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
            String apiKey = System.getenv("CLOUDINARY_API_KEY");
            String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
            
            // For local development - you can set these directly (not recommended for production)
            if (cloudName == null) cloudName = "your-cloud-name"; // Replace with your cloud name
            if (apiKey == null) apiKey = "your-api-key"; // Replace with your API key  
            if (apiSecret == null) apiSecret = "your-api-secret"; // Replace with your API secret
            
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
            ));
        }
    }
    
    /**
     * Get Cloudinary instance
     * @return configured Cloudinary instance
     */
    public static Cloudinary getInstance() {
        return cloudinary;
    }
    
    /**
     * Check if Cloudinary is properly configured
     * @return true if credentials are available via CLOUDINARY_URL or individual env vars
     */
    public static boolean isConfigured() {
        String cloudinaryUrl = System.getenv("CLOUDINARY_URL");
        if (cloudinaryUrl != null) {
            return true; // CLOUDINARY_URL is set
        }
        
        // Check individual environment variables
        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
        String apiKey = System.getenv("CLOUDINARY_API_KEY");
        String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
        
        return cloudName != null && apiKey != null && apiSecret != null;
    }
    
    /**
     * Get configuration info for debugging
     * @return configuration status
     */
    public static String getConfigInfo() {
        String cloudinaryUrl = System.getenv("CLOUDINARY_URL");
        if (cloudinaryUrl != null) {
            return "Using CLOUDINARY_URL environment variable (recommended)";
        } else if (isConfigured()) {
            return "Using individual environment variables (CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY, CLOUDINARY_API_SECRET)";
        } else {
            return "Using hardcoded values (NOT RECOMMENDED for production)";
        }
    }
}