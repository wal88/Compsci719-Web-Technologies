package ictgradschool;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.servlet.http.HttpSession;

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
	private int maxFileSize = 5 * 1024 * 1024;
	private int maxMemSize = 5 * 1024 * 1024;
	private final String UPLOADED_FOLDER = "/Compsci719Lab14/Uploaded-Photos/";
	private PrintWriter writer;
	File thumbnail = null;
	File dir;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageGallerySubmit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		String id = session.getId();

		filePath = servletContext.getRealPath("/Uploaded-Photos") + "\\";
		filePath = filePath + id + "\\";
		File idDir = new File(filePath);
		idDir.mkdir();

		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// out.println("Uploaded directory: " + filePath + "<br>");
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
		factory.setRepository(new File("c:\\temp"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		File fullFile = null;
		String imgCaption = "";
		
		
		try {
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<FileItem> i = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					// String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String imgExt = fileName.substring(fileName.lastIndexOf('.'));
					// String contentType = fi.getContentType();
					// boolean isInMemory = fi.isInMemory();
					// long sizeInBytes = fi.getSize();

					// Error checking
					if (fi.getSize() > maxFileSize) {
						out.println("The file exceeds the maximum upload file size of " + maxFileSize);
					} else if (!(imgExt.endsWith("jpg") || imgExt.endsWith("png") || imgExt.endsWith("gif"))) {
						out.println("The file type is not valid.");
					} else {
						// fullFile = new File(filePath, "fullsize" + imgExt);
						dir = new File(filePath);
						fullFile = File.createTempFile("fullsize-", imgExt, dir);
						fi.write(fullFile);
						out.println("Original name of Uploaded File: " + fileName + "<br />");
						out.println("Uploaded directory: " + filePath + "<br />");
						out.println("Output file: " + fullFile.getAbsolutePath() + "<br />");
					}
				} else if (fi.getFieldName().equals("caption")) {
					imgCaption = fi.getString();
					File captionFile = new File(filePath + "caption.txt");
					FileOutputStream fos = new FileOutputStream(captionFile, true);
					writer = new PrintWriter(fos);
					writer.append(imgCaption + "\n");
					writer.close();
					fos.close();
				}
			}

			out.println("</body>");
			out.println("</html>");
		} catch (Exception ex) {
			System.out.println(ex);
		}

		if (fullFile != null) {
			generateThumbnail(fullFile, filePath);
			out.println("<a href='" + UPLOADED_FOLDER + id + "/" + fullFile.getName() + "'><img src='"
					+ UPLOADED_FOLDER + id + "/" + thumbnail.getName()+ "' alt='" + imgCaption + "' /></a>");
			out.println("<br/>" + "Caption: " + imgCaption + "<br />");
		}

	}

	private void generateThumbnail(File fullFile, String filePath) {
		BufferedImage fullImage = null;
		try {
			fullImage = ImageIO.read(fullFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int imgW = fullImage.getWidth();
		int imgH = fullImage.getHeight();

		if (imgW > 400 || imgH > 400) {
			double scale = 400.0 / Math.max(imgW, imgH);
			imgW = (int) (imgW * scale);
			imgH = (int) (imgH * scale);
		}

		BufferedImage thumbImg = new BufferedImage(imgW, imgH, fullImage.getType());
		Image rescaledImg = fullImage.getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH);
		thumbImg.createGraphics().drawImage(rescaledImg, 0, 0, null);
		try {
			thumbnail =  File.createTempFile("thumbnail-", ".png", dir);
			ImageIO.write(thumbImg, "png", thumbnail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
