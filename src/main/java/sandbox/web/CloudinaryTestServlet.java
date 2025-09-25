package sandbox.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cloudinary.utils.ObjectUtils;
import sandbox.config.CloudinaryConfig;

@WebServlet("/cloudinary-test")
public class CloudinaryTestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Cloudinary Test</title></head><body>");
        out.println("<h1>🔧 Cloudinary Configuration Test</h1>");
        
        try {
            // Check environment variables
            String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
            String apiKey = System.getenv("CLOUDINARY_API_KEY");
            String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
            
            out.println("<h2>📋 Environment Variables:</h2>");
            out.println("<ul>");
            out.println("<li><strong>CLOUDINARY_CLOUD_NAME:</strong> " + (cloudName != null ? "✅ Set (" + cloudName + ")" : "❌ Not set") + "</li>");
            out.println("<li><strong>CLOUDINARY_API_KEY:</strong> " + (apiKey != null ? "✅ Set (" + apiKey.substring(0, 4) + "...)" : "❌ Not set") + "</li>");
            out.println("<li><strong>CLOUDINARY_API_SECRET:</strong> " + (apiSecret != null ? "✅ Set (" + apiSecret.substring(0, 4) + "...)" : "❌ Not set") + "</li>");
            out.println("</ul>");
            
            // Check if Cloudinary is configured
            boolean isConfigured = CloudinaryConfig.isConfigured();
            out.println("<p><strong>Configuration Status:</strong> " + 
                       (isConfigured ? "✅ Using environment variables" : "⚠️ Using hardcoded values") + "</p>");
            
            out.println("<p><strong>Config Info:</strong> " + CloudinaryConfig.getConfigInfo() + "</p>");
            
            // Test Cloudinary connection
            var cloudinary = CloudinaryConfig.getInstance();
            out.println("<h2>🔗 Testing Cloudinary Connection...</h2>");
            
            try {
                // Simple test - try to create cloudinary instance
                if (cloudinary != null) {
                    out.println("<p>✅ <strong>Cloudinary instance created successfully!</strong></p>");
                    out.println("<p><a href='https://cloudinary.com/console/media_library' target='_blank'>🔗 Open Cloudinary Media Library</a></p>");
                } else {
                    out.println("<p>❌ <strong>Failed to create Cloudinary instance</strong></p>");
                }
                
            } catch (Exception connEx) {
                out.println("<p>❌ <strong>Cloudinary connection failed:</strong></p>");
                out.println("<pre style='background: #ffe6e6; padding: 10px; font-size: 12px;'>");
                out.println("Connection Error: " + connEx.getMessage());
                out.println("</pre>");
            }
            
        } catch (Exception e) {
            out.println("<p>❌ <strong>Cloudinary setup error:</strong></p>");
            out.println("<pre style='background: #ffe6e6; padding: 10px; font-size: 12px;'>");
            out.println("Setup Error: " + e.getMessage());
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("<h3>🛠️ Setup Instructions:</h3>");
        out.println("<ol>");
        out.println("<li>Create a Cloudinary account at <a href='https://cloudinary.com' target='_blank'>cloudinary.com</a></li>");
        out.println("<li>Get your credentials from the dashboard</li>");
        out.println("<li>Set environment variables:<br>");
        out.println("<code>heroku config:set CLOUDINARY_CLOUD_NAME=your-cloud-name<br>");
        out.println("heroku config:set CLOUDINARY_API_KEY=your-api-key<br>");
        out.println("heroku config:set CLOUDINARY_API_SECRET=your-api-secret</code></li>");
        out.println("</ol>");
        
        out.println("</body></html>");
    }
}