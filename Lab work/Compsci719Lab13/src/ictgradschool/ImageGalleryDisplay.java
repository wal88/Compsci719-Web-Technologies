package ictgradschool;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class ImageGallerySubmit
 */
public class ImageGalleryDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private final String PHOTOS_FOLDER = "/Compsci719Lab13/Photos/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageGalleryDisplay() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		response.getWriter().append("Cannot use GET, must use POST. \nServed at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext servletContext = getServletContext();
		filePath = servletContext.getRealPath("/Photos/");

		java.io.PrintWriter out = response.getWriter();

		out.println("<html>" + "<head>" + "<title>Image Gallery Display</title>" + "<style>"
		 + "a, #secondheading { position: absolute; left: 300;}"
		 + ".size { position: absolute; left: 500;}"
		 + ".heading {font-weight: bold; font-size: 16; }"
				+ "</style>" + "</head>" + "<body>");

		// Location of photos
		File photosDir = new File(filePath);
//		out.print("<p>" +photosDir.exists() + filePath + "</p>");
		File[] photos = photosDir.listFiles();
		
		out.print("<div><span class='heading'>Thumbnail</span> <span id='secondheading' class='heading'>Filename</span> <span class='size heading'>File-size</span></div><br/>");
		
		for (int i = 0; i < photos.length; i++) {
			File photo = photos[i];
			
			if (photo.getName().contains("_thumbnail.png")) {
				String photoName = photo.getName().substring(0, photo.getName().lastIndexOf("_"));

				out.print("<img src='" + PHOTOS_FOLDER + photo.getName() + "' />" + "<a href='" + PHOTOS_FOLDER
						+ photoName + ".jpg' >" + photoName + "</a>");
				out.print("<span class='size'>" + photo.length() + " byes"
						+ "</span><br/>");
			}

		}

		out.println("</body>");
		out.println("</html>");

		// try {
		// // Parse the request to get file items.
		// List fileItems = upload.parseRequest(request);
		//
		// // Process the uploaded file items
		// Iterator i = fileItems.iterator();
		//
		// while (i.hasNext()) {
		// FileItem fi = (FileItem) i.next();
		//
		// if (fi.getFieldName().equals("caption")) {
		// caption = fi.getString();
		// }
		// if (!fi.isFormField()) {
		// // Get the uploaded file parameters
		//// String fieldName = fi.getFieldName();
		// String fileName = fi.getName();
		// String fileNameOriginal = fileName.toString();
		// fileName = "fullsize" +
		// fileNameOriginal.substring(fileNameOriginal.indexOf("."));
		//// String contentType = fi.getContentType();
		//// boolean isInMemory = fi.isInMemory();
		//// long sizeInBytes = fi.getSize();
		// // Write the file
		// if (fileName.lastIndexOf("\\") >= 0) {
		// fullFile = new File(filePath +
		// fileName.substring(fileName.lastIndexOf("\\")));
		//
		// } else {
		// fullFile = new File(filePath +
		// fileName.substring(fileName.lastIndexOf("\\") + 1));
		// }
		//
		// // Save the image as "fullsize" + extension
		// fi.write(fullFile);
		//
		// // Save a thumbnail version
		// if (fullFile != null) {
		// generateThumbnail(fullFile);
		// out.println("<a href='" + UPLOADED_FOLDER + fullFile.getName() +
		// "'><img src='"
		// + UPLOADED_FOLDER + "thumbnail.png' alt='" + caption + "' /></a>");
		// out.println("<br/> + Caption: " + caption + "<br/><br/>");
		// }
		//
		// out.println("<br>" + "Original Filename: " + fileNameOriginal +
		// "<br><br>");
		// }
		// }
		// out.println("Uploaded directory: " + filePath + "<br>");
		// out.println("Uploaded file: " + fullFile.getAbsolutePath() + "<br>");
		//
		// out.println("</body>");
		// out.println("</html>");
		// } catch (Exception ex) {
		// System.out.println(ex);
		// }
	}

	// private void generateThumbnail(File fullFile) {
	// BufferedImage fullImage = null;
	// try {
	// fullImage = ImageIO.read(fullFile);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// int width = fullImage.getWidth();
	// int height = fullImage.getHeight();
	//
	// if (width > 400 || height > 400) {
	// double scale = 400.0 / Math.max(width, height);
	// width = (int) (width * scale);
	// height = (int) (height * scale);
	// }
	// BufferedImage thumbImage = new BufferedImage(width, height,
	// fullImage.getType());
	//
	// Image rescaledImage = fullImage.getScaledInstance(width, height,
	// Image.SCALE_SMOOTH);
	//
	// thumbImage.createGraphics().drawImage(rescaledImage, 0, 0, null);
	//
	// try {
	// ImageIO.write(thumbImage, "png", new File(filePath, "thumbnail.png"));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// //return thumbImage;
	// }

}
