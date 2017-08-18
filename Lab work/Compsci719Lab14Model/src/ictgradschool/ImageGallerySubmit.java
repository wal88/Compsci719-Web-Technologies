package ictgradschool;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.io.output.*;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;



/**
 * Servlet implementation class ImageGallerySubmit
 */
//@WebServlet("/ImageGallerySubmit")
public class ImageGallerySubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private boolean isMultipart;
	   private String filePath, tmpFilePath;
	   private int maxFileSize = 5 * 1024 * 1024; // 5Mb
	   private int maxMemSize = 4 * 1024;
	   private File fullFile = null;
	   private String imgCaption = ""; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageGallerySubmit() {
        super();
        // TODO Auto-generated constructor stub      
    }
    
    public void init( ){
        // the location where uploads are to be stored
        //filePath = getServletContext().getInitParameter("upload-location");
        ServletContext servletContext = getServletContext();
    	filePath = servletContext.getRealPath("/Uploaded-Photos");

        
        // the temp location where large uploads are to be stored
        tmpFilePath = getServletContext().getInitParameter("tmp-location");
     }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		throw new ServletException("Servlet " + getClass( ).getName( ) + " does not accept the GET method. Use POST method.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession currSession = request.getSession(); // will create a new one if it didn't exist
		String sessionId = currSession.getId();
		File sessionFolder = new File(filePath, sessionId);
		request.getParameter(arg0);
		String tmpFileName = "";
		String imgExt = "";
		//String caption = request.getParameter("caption"); // not available in multipart request
		
		// Code is largely from the tutorial, http://www.tutorialspoint.com/servlets/servlets-file-uploading.htm
		
		// Check that we have a file upload request
	      isMultipart = ServletFileUpload.isMultipartContent(request);
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter( );
	      
	      if( !isMultipart ){
	    	 out.println("<!DOCTYPE html>"); 
	         out.println("<html>");
	         out.println("<head>");
	         out.println("<title>File upload</title>");  
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
	      factory.setRepository(new File(tmpFilePath));

	      // Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      // maximum file size to be uploaded.
	      upload.setSizeMax( maxFileSize );

	      try{ 
	      // Parse the request to get file items.
	      java.util.List fileItems = upload.parseRequest(request);
		
	      // Process the uploaded file items
	      Iterator i = fileItems.iterator();

	      out.println("<!DOCTYPE html>");
	      out.println("<html>");
	      out.println("<head>");
	      out.println("<title>File upload</title>");  
	      out.println("</head>");
	      out.println("<body>");   
	      
	     
		if(!sessionFolder.exists()) { // && currSession.isNew() 
			if(!sessionFolder.mkdirs()) {					
				out.println("Unable to create user directory " + sessionFolder);
				out.println("</body>");
				out.println("</html>");
				return;
			}
		} else {
			out.println("Found user directory " + sessionFolder);
		}
	      
	      while ( i.hasNext () ) 
	      {
	         FileItem fi = (FileItem)i.next();
	         if ( !fi.isFormField () )	
	         {
	            // Get the necessary file parameters of the uploaded file
	           
	        	String fileName = fi.getName();
		        imgExt = fileName.substring(fileName.lastIndexOf(".")); // period included in imgExt!!	        	
	        	
	        			            
		        // Error checking
	        	if(fi.getSize() > maxFileSize) {
	        		out.println("The file exceeds the maximum upload file size of " + maxFileSize);
	        	} else if (!(imgExt.endsWith("jpg") || imgExt.endsWith("png") || imgExt.endsWith("gif"))) {
	        		out.println("The file extension (" + imgExt + ") is not one of .jpg, .png or .gif. Not uploading.");
	        	} else { // file is of correct size and extension
	            
		            // Write the img file
	        		tmpFileName = makeTmpFileName(imgExt, sessionFolder);		           
		            fullFile = new File(sessionFolder, tmpFileName + imgExt);		            
		            
		            fi.write( fullFile ) ;
		            out.println("Original name of Uploaded File: " + fileName + "<br>");
		            out.println("Uploaded directory: " + sessionFolder + "<br>");
		            out.println("Output file: " + fullFile.getAbsolutePath() + "<br>");
		            
		            //break; // found the first file selected for upload, quit the loop since we're only processing a single file upload
	        	} 
	         } else if(fi.getFieldName().equals("caption")){
	        	 imgCaption = fi.getString();
	        	 out.println("Found " + fi.getFieldName() + " field: " + imgCaption);
	        	 // http://stackoverflow.com/questions/5730532/values-of-input-text-fields-in-a-html-multipart-form				 
	         }
	      }
	     
	   }catch(Exception ex) {
	       System.out.println(ex);
	   }	   
	     
	      
	   // fullFile path is null unless the file uploaded was acceptable
	   if(fullFile != null) {
		   // remove the prefix to the servlet's path from the path to the images   
		   // to get the path to the images relative to the servlet. E.g. /lab14/Upload-Photos/userSessionID
		   String servletRootParent = new File(getServletContext().getRealPath("/")).getParent();
		   				// everything in the filepath before servlet name (/lab14)
		   String imageFolderPath = sessionFolder.getAbsolutePath(); // user session folder
		   imageFolderPath = imageFolderPath.substring(servletRootParent.length()); // remove everything before /servlet-name
		   imageFolderPath = imageFolderPath.replace(File.separator, "/"); // make URL slashes
		   
		   // Need to refresh Eclipse's images folder		   
		   // http://stackoverflow.com/questions/5467902/how-to-refresh-eclipse-workspace-programatically
		   // I can't get Eclipse to refresh the images folder programmatically, nor does the Preferences setting
		   // in the following work for me: http://stackoverflow.com/questions/985976/refresh-an-eclipse-project-with-ant
		   
		   // So the thumbnail/fullsize images displayed are always the previously uploaded ones
		   // until the server is restarted and the next image is uploaded...
		   
		   File captionFile = new File(sessionFolder, tmpFileName + "_caption.txt");
		   boolean success = makeCaptionFile(imgCaption, captionFile, out); // creates the caption file
		   
		   File thumbFile = new File(sessionFolder, tmpFileName + "_thumbnail.png");
		   success = success && generateThumbnail(fullFile, thumbFile);
		   if(success) {
			   out.println("<figure>");
			   //out.append("<a href='" + imageFolderPath + "/" + tmpFileName + imgExt + "'>");
			   //out.append("<img src='" + imageFolderPath + "/" + tmpFileName + "_thumbnail.png' alt='" + imgCaption + "' /></a>");
			   out.append("<a href='" + imageFolderPath + "/" + fullFile.getName() + "'>");
			   out.append("<img src='" + imageFolderPath + "/" + thumbFile.getName() + "' alt='" + imgCaption + "' /></a>");
			   out.println("<figcaption><i>Caption:" + imgCaption + "</i></figcaption>");
			   out.println("</figure>");
		   }
		   
	   }
		
	   // finish off the html page
	   
	   out.println("</body>");
	   out.println("</html>");
	   
	}
	
	private String makeTmpFileName(String ext, File folder) throws IOException {
		File tmpFile = File.createTempFile("full-", ext, folder);
        
        String tmpFileName = tmpFile.getName().substring("full-".length());
        tmpFile.delete();
        tmpFile = null;
        
        tmpFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf(ext));
        return tmpFileName;
	}
	
	private boolean makeCaptionFile(String caption, File captionFile, PrintWriter out) {
		boolean success = true;		
		FileWriter output = null;
		try {
			output = new FileWriter(captionFile);
			output.write(caption);
			output.flush();
			
		} catch(IOException ioe) {
			out.println("Unable to write caption to file " + captionFile.getAbsolutePath());
		} finally {
			try {
				output.close();
			} catch(Exception ex) {
				ex.printStackTrace(out);
				output = null;
			}
			
		}
		
		return success;
		
	}
	
	// http://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java
	private boolean generateThumbnail(File fullFile, File thumbFile) {
	      
		// Constraints on thumbnail: 
		// max size of any dimension is 400. The other dimension should be adjusted accordingly.
		// Image dimensions under 400x400 should be preserved in original size.
		// But thumbnail should always be png format.
		
	   final int MAX_W = 400;
	   final int MAX_H = 400;	   
	   boolean success = true;
	   
	   Image rescaledImg = null;
	   BufferedImage rescaledBi = null;
	   BufferedImage bi = null;
	   // read orig image
	   try {
		   bi = ImageIO.read(fullFile);
	   } catch(IOException ioe) {
		   System.out.println("Could not read image " + fullFile);
		   return false;
	   }
	   // get its dimensions
	   int w = bi.getWidth(null);
	   int h = bi.getHeight(null);	
	   
	   // check if we can leave the original dimensions intact
	   if(w <= MAX_W & h <= MAX_H) {
		   
		   // No access to Path objects, only have File objects
		   //if(imgExt.endsWith("png")) {
			   // With this fileext and filesize, no need to create a thumbnail, just a filecopy
			   //Files.copy(fullFile, thumbFile, StandardCopyOption.REPLACE_EXISTING);
		   //}
		   
		   // no resizing
		   rescaledBi = new BufferedImage(w, h, bi.getType());			 
		   rescaledImg = bi;
		   
	   } else { 
	   
		   // one or both dimensions exceed the max allowed size
		   float thumbwidth, thumbheight;
		   float scaleFactor = 1.0f; // without f, it's seen as a double: http://stackoverflow.com/questions/5076710/what-is-float-in-java
		   
		   if (w > h) {
			   thumbwidth = 400.0f; 
			   scaleFactor = thumbwidth/(float)w;
			   thumbheight = (float)h * scaleFactor;
		   } else {
			   thumbheight = 400.0f;
			   scaleFactor = thumbheight/(float) h;
			   thumbwidth = (float)w * scaleFactor;
		   }
		   
		   int thumb_width = Math.round(thumbwidth);
		   int thumb_height = Math.round(thumbheight);
	   		   
		   // http://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java		 
		   rescaledBi = new BufferedImage(thumb_width, thumb_height, bi.getType()); 
		   				// type arg: allow for BufferedImage.TYPE_INT_ARGB for alpha in pngs 
		   rescaledImg = bi.getScaledInstance(thumb_width, thumb_height, Image.SCALE_SMOOTH);
		   
		  	
	   }
	   // finally, write out the thumbnail
	   try {			   
		   rescaledBi.createGraphics().drawImage(rescaledImg,0,0,null);		   
		   ImageIO.write(rescaledBi, "png", thumbFile); //new File(folderPath,"thumbnail.png")
	   } catch (IOException ex) {
		   System.out.println("Could not write thumbnail");
		   success = false;
	   }
	   
	   return success;	   
	}
	
}
