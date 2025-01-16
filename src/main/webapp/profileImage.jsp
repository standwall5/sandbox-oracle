 <%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>
<%
    // Retrieve the InputStream from the session (you already set it in the session)
    HttpSession session = request.getSession();
    InputStream inputStream = (InputStream) session.getAttribute("userImage");

    if (inputStream != null) {
        // Set the content type based on the image type (it could be dynamic depending on the image type)
        String imageType = "image/jpeg";  // You can detect this dynamically based on the image's content
        response.setContentType(imageType);

        // Create a buffer to read the image and write it to the output stream
        byte[] buffer = new byte[4096];  // Adjust the buffer size as needed
        int bytesRead;
        OutputStream outStream = response.getOutputStream();

        // Read the InputStream and write the data to the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        // Close the OutputStream
        outStream.close();
    } else {
        // Handle the case where no image is found in the session
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().println("Image not found.");
    }
%>