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
public class ImageGallerySubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 5000 * 1024;
	private int maxMemSize = 4000 * 1024;
	private File fullFile;
	private String caption;
	private final String UPLOADED_FOLDER = "/Compsci719Lab13/Uploaded-Photos/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageGallerySubmit() {
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
		filePath = servletContext.getRealPath("/Uploaded-Photos/");

		java.io.PrintWriter out = response.getWriter();

		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);

		// Location to save data that is larger than maxMemSize.
		File fileDir = new File(filePath);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		factory.setRepository(fileDir);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator i = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();

				if (fi.getFieldName().equals("caption")) {
					caption = fi.getString();
					out.println("<br/> + Caption: " + caption + "<br/><br/>");
				}
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
//					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String fileNameOriginal = fileName.toString();
					fileName = "fullsize" + fileNameOriginal.substring(fileNameOriginal.indexOf("."));
//					String contentType = fi.getContentType();
//					boolean isInMemory = fi.isInMemory();
//					long sizeInBytes = fi.getSize();
					// Write the file
					if (fileName.lastIndexOf("\\") >= 0) {
						fullFile = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));

					} else {
						fullFile = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}

					// Save the image as "fullsize" + extension
					fi.write(fullFile);

					// Save a thumbnail version
					if (fullFile != null) {
						generateThumbnail(fullFile);
						out.println("<a href='" + UPLOADED_FOLDER + fullFile.getName() + "'><img src='"
								+ UPLOADED_FOLDER + "thumbnail.png'/></a>");
						
					}

					out.println("<br>" + "Original Filename: " + fileNameOriginal + "<br><br>");
				}
			}
			out.println("Uploaded directory: " + filePath + "<br>");
			out.println("Uploaded file: " + fullFile.getAbsolutePath() + "<br>");

			out.println("</body>");
			out.println("</html>");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private void generateThumbnail(File fullFile) {
		BufferedImage fullImage = null;
		try {
			fullImage = ImageIO.read(fullFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int width = fullImage.getWidth();
		int height = fullImage.getHeight();

		if (width > 400 || height > 400) {
			double scale = 400.0 / Math.max(width, height);
			width = (int) (width * scale);
			height = (int) (height * scale);
		}
		BufferedImage thumbImage = new BufferedImage(width, height, fullImage.getType());

		Image rescaledImage = fullImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		thumbImage.createGraphics().drawImage(rescaledImage, 0, 0, null);

		try {
			ImageIO.write(thumbImage, "png", new File(filePath, "thumbnail.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return thumbImage;
	}

}
