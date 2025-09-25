package test;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

public class TestImageUpload {

    private static void log(String msg) {
        System.out.println("[TestImageUpload] " + msg);
    }

    private static void fail(String msg) {
        System.err.println("[TestImageUpload][ERROR] " + msg);
    }

    public static void main(String[] args) throws Exception {
        // 1. Resolve credentials from environment or CLOUDINARY_URL
        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
        String apiKey = System.getenv("CLOUDINARY_API_KEY");
        String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
        String cloudinaryUrl = System.getenv("CLOUDINARY_URL");

        if ((cloudName == null || apiKey == null || apiSecret == null) && cloudinaryUrl == null) {
            fail("Missing credentials. Set CLOUDINARY_CLOUD_NAME / API key / secret or CLOUDINARY_URL.");
            return;
        }

        Cloudinary cloudinary;
        if (cloudinaryUrl != null) {
            log("Using CLOUDINARY_URL for configuration.");
            cloudinary = new Cloudinary(cloudinaryUrl);
        } else {
            log("Using individual environment variables for configuration.");
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
            ));
        }

        // 2. Determine image path: arg[0] > TEST_IMAGE_PATH env > default placeholder
        String imagePath = (args.length > 0) ? args[0] : System.getenv("TEST_IMAGE_PATH");
        if (imagePath == null || imagePath.isBlank()) {
            fail("Provide an image path as first argument or set TEST_IMAGE_PATH env var.");
            return;
        }

        File file = new File(imagePath);
        if (!file.exists() || !file.isFile()) {
            fail("File does not exist: " + file.getAbsolutePath());
            return;
        }
        log("Using file: " + file.getAbsolutePath() + " (" + Files.size(file.toPath()) + " bytes)");

        // 3. Optional folder / public id customization
        String folder = System.getenv().getOrDefault("CLOUDINARY_TEST_FOLDER", "peso_test_uploads");
        String publicId = "test_" + System.currentTimeMillis();

        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("folder", folder);
        uploadOptions.put("public_id", publicId);
        uploadOptions.put("overwrite", Boolean.TRUE);
        uploadOptions.put("resource_type", "image");

        // 4. Upload via InputStream
        long start = System.currentTimeMillis();
        try (java.io.InputStream fileContent = new java.io.FileInputStream(file)) {
            log("Uploading via InputStream to folder='" + folder + "' public_id='" + publicId + "'...");
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(fileContent, uploadOptions);
            long took = System.currentTimeMillis() - start;
            log("Upload complete in " + took + " ms");

            // 5. Display key fields
            log("secure_url: " + uploadResult.get("secure_url"));
            log("public_id:  " + uploadResult.get("public_id"));
            log("version:    " + uploadResult.get("version"));
            log("format:     " + uploadResult.get("format"));
            log("bytes:      " + uploadResult.get("bytes"));

            // 6. Build a transformed URL (example: thumbnail 150x150, fill crop, format auto)
            String generatedUrl = cloudinary.url()
                .secure(true)
                .transformation(new com.cloudinary.Transformation()
                    .width(150).height(150).crop("fill").gravity("auto").fetchFormat("auto"))
                .generate(uploadResult.get("public_id").toString());
            log("Sample transformed URL (150x150): " + generatedUrl);
        } catch (Exception ex) {
            fail("Upload failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}