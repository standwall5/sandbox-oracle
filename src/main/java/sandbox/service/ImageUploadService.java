package sandbox.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import sandbox.config.CloudinaryConfig;

import java.io.InputStream;
import java.util.Map;

/**
 * Foolproof Cloudinary Upload Service
 * Works with Heroku environment variables, logs full errors
 */
public class ImageUploadService {

    private static final Cloudinary cloudinary = CloudinaryConfig.getInstance();

    /**
     * Upload a file to Cloudinary
     * @param fileContent InputStream of the file
     * @param folder Folder path in Cloudinary (e.g., "peso-app/users")
     * @param publicId Optional custom public_id, pass null to auto-generate
     * @return secure URL if successful, null otherwise
     */
    public static String uploadFile(InputStream fileContent, String folder, String publicId) {
        if (!CloudinaryConfig.isConfigured()) {
            System.err.println("[Cloudinary] Not configured. Set CLOUDINARY_URL or individual env vars.");
            return null;
        }

        try {
            if (publicId == null || publicId.isEmpty()) {
                publicId = "file_" + System.currentTimeMillis();
            }

            Map<String, Object> params = ObjectUtils.asMap(
                    "folder", folder,
                    "public_id", publicId,
                    "resource_type", "auto",   // supports images, pdfs, docs
                    "overwrite", true
            );

            Map uploadResult = cloudinary.uploader().upload(fileContent, params);

            System.out.println("[Cloudinary] Upload result: " + uploadResult);

            String secureUrl = (String) uploadResult.get("secure_url");
            if (secureUrl != null) {
                System.out.println("[Cloudinary] File uploaded successfully: " + secureUrl);
            } else {
                System.err.println("[Cloudinary] Upload did not return secure_url.");
            }

            return secureUrl;

        } catch (Exception e) {
            System.err.println("[Cloudinary] Failed to upload file:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convenience method to upload user profile images
     */
    public static String uploadUserImage(InputStream fileContent, int userId) {
        String publicId = "user_profile_" + userId + "_" + System.currentTimeMillis();
        return uploadFile(fileContent, "peso-app/users", publicId);
    }

    /**
     * Convenience method to upload company logos
     */
    public static String uploadCompanyImage(InputStream fileContent, int companyId) {
        String publicId = "company_logo_" + companyId + "_" + System.currentTimeMillis();
        return uploadFile(fileContent, "peso-app/companies", publicId);
    }

    /**
     * Convenience method to upload resumes/documents
     */
    public static String uploadResume(InputStream fileContent, int userId, String originalFileName) {
        String publicId = "resume_" + userId + "_" + System.currentTimeMillis();
        return uploadFile(fileContent, "peso-app/resumes", publicId);
    }
}
