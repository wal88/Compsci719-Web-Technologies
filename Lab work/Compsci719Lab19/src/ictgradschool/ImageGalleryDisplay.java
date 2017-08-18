package ictgradschool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageGalleryDisplay
 */

public class ImageGalleryDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String photosPath;
	private final String THUMBNAIL_SUFFIX = "_thumbnail.png";
	String messages = "";

	private final static int COOKIE_EXPIRATION_TIME = 5 * 60; // cookies stored
																// for 5 mins
																// (5*60
																// seconds)
																// which
																// suffices for
																// testing

	// don't make the following into class variables, as they are a single
	// user's settings, whereas this
	// Servlet class should work for all users that may connect to it, each with
	// their own user preferencse
	// private String filenameSortToggle = "asc";
	// private String filesizeSortToggle = "asc";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageGalleryDisplay() {
		super();
		// TODO Auto-generated constructor stub
	}

	// servletContext() only accessible from init(), not from constructor
	public void init() {
		photosPath = getServletContext().getRealPath("Photos");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		HttpSession session = request.getSession(); // this will create a
													// session if one doesn't
													// exist.
		// This step needs to be done before any output to the page
		// we'll be storing sort order constraints in the session in the
		// function doSortDisplay()

		File photosFolder = new File(photosPath);

		if (!photosFolder.exists()) {
			response.sendError(500, "Photos folder " + photosPath + " does not exist.");
			return;
		} else if (!photosFolder.canRead()) {
			response.sendError(500, "Photos folder " + photosPath + " can't be read.");
			return;
		}

		File[] files = photosFolder.listFiles(); // File objects returned can be
													// folders
		List<FileInfo> fileDataList = new LinkedList<FileInfo>();
		messages = "";

		File thumbPath = null;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String filename = files[i].getName();

				if (filename.endsWith(THUMBNAIL_SUFFIX)) {
					// thumbPath = files[i];
					continue;
				} else if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".gif")) {

					long fullfileSize = files[i].length();

					// found full size image
					// try to find matching thumbnail file for the image
					String parentFolder = files[i].getParent();
					String fileNamePrefix = filename.substring(0, filename.lastIndexOf("."));
					thumbPath = new File(parentFolder, fileNamePrefix + THUMBNAIL_SUFFIX);
					if (thumbPath.exists()) {
						fileDataList.add(new FileInfo(thumbPath, fullfileSize, files[i]));
					} else {
						messages += "Could not find thumbnail " + thumbPath.getName() + " for image " + filename + "\n";
						fileDataList.add(new FileInfo(files[i], fullfileSize));
					}
				}
			}
		}

		if (fileDataList.size() == 0) {
			messages += "No images found in Photos folder " + photosPath + ".\n";
		} else {

			// Get filenameSortToggle, filesizeSortToggle sort orders, if
			// stored. Else store their initial values as ascending
			String filenameSortToggle, filesizeSortToggle;
			Object fnToggle = session.getAttribute("filenameSortToggle");
			Object fsToggle = session.getAttribute("filesizeSortToggle");

			if (fnToggle == null) { // initialise and store in the session for
									// future use
				filenameSortToggle = "asc";
				session.setAttribute("filenameSortToggle", filenameSortToggle);
			} else {
				filenameSortToggle = (String) fnToggle;
			}

			if (fsToggle == null) { // initialise and store in the session for
									// future use
				filesizeSortToggle = "asc";
				session.setAttribute("filesizeSortToggle", filesizeSortToggle);
			} else {
				filesizeSortToggle = (String) fsToggle;
			}

			String currFilenameSortToggle = filenameSortToggle;
			String currFilesizeSortToggle = filesizeSortToggle;
			request.setAttribute("currFilenameSortToggle", filenameSortToggle);
			request.setAttribute("currFilesizeSortToggle", filesizeSortToggle);

			// may have been asked to sort the fileDataList. If so, do it now
			doSortDisplay(request, response, fileDataList);

			// doSortDisplay() will also have toggled session's
			// filenameSortToggle and filesizeSortToggle to the future state
			filenameSortToggle = (String) session.getAttribute("filenameSortToggle");
			filesizeSortToggle = (String) session.getAttribute("filesizeSortToggle");
			request.setAttribute("filenameSortToggle", filenameSortToggle);
			request.setAttribute("filesizeSortToggle", filesizeSortToggle);

			// out.println("*** future: " + filenameSortToggle + " - current: "
			// + currFilenameSortToggle);

			// the sort order toggle image displayed reflects the current state
			// of the sort order
			// but the sort order that is used when clicking on a sorting link
			// is the future (toggled) sort order

		/*	for (int i = 0; i < fileDataList.size(); i++) {
				FileInfo fileData = fileDataList.get(i);
				File thumbNailFile = fileData.thumbPath;
				if (thumbNailFile == null) {
					fileDataList.remove(fileData);
					continue;
				}
				File fullFile = fileData.fullFile;
				long filesize = fileData.fullfileSize;
				String displayStr = fileData.thumbDisplay;

			}*/

		}

		request.setAttribute("fileDataList", fileDataList);
		request.setAttribute("messages", messages);
		request.setAttribute("photosPath", photosPath);
		request.getRequestDispatcher("ImageGalleryUI.jsp").forward(request, response);
	}

	private List<FileInfo> doSortDisplay(HttpServletRequest request, HttpServletResponse response,
			List<FileInfo> fileDataList) {
		HttpSession session = request.getSession();

		// Check request parameters for any sorting constraints supplied
		String sortColumn = request.getParameter("sortColumn");
		String sortOrder = request.getParameter("order");

		// Check cookies first for any stored sorting constraints
		if (sortColumn == null && sortOrder == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {

				Cookie cookie;

				for (int i = 0; i < cookies.length; i++) {
					// let's find the cookie
					cookie = cookies[i];
					if (cookie.getName().equals("sortColumn"))
						sortColumn = cookie.getValue();
					if (cookie.getName().equals("sortOrder"))
						sortOrder = cookie.getValue();
				}
			}

			if (sortColumn != null && sortOrder != null) {
				messages += "Found sorting cookies: sort " + sortColumn + " by " + sortOrder + "\n";
			}

		}

		// Next check the session for any stored sorting constraints
		if (sortColumn == null && sortOrder == null) {
			messages += "Couldn't find sorting constraints in cookies, trying session..." + "\n";

			sortColumn = (String) session.getAttribute("sortColumn");
			sortOrder = (String) session.getAttribute("sortOrder");

			if (sortColumn != null && sortOrder != null) {
				messages += "Found sorting constraints in session: sort " + sortColumn + " by " + sortOrder + "\n";
			}
		}

		// now we may have sorting constraints either from the request
		// parameters, or remembered (from cookies or else the session)

		if (sortColumn != null && sortOrder != null) {
			if ((sortColumn.equals("filename") || sortColumn.equals("filesize"))
					&& (sortOrder.equals("ascending") || sortOrder.equals("descending"))) {
				// we've been given meaningful sort constraints, so try sorting
				// by them

				Comparator<FileInfo> comparator = new FileInfoComparator(sortColumn, sortOrder);
				Collections.sort(fileDataList, comparator);
			} else {
				messages += "Sorting constraints " + sortColumn + " by " + sortOrder + " not recognised. Not sorting."
						+ "\n";
			}

			// having sorted, toggle the sortOrder for future use
			if (sortColumn.equals("filename")) {
				if (sortOrder.equals("ascending")) {
					session.setAttribute("filenameSortToggle", "desc");
				} else if (sortOrder.equals("descending")) {
					session.setAttribute("filenameSortToggle", "asc");
				}
			} else if (sortColumn.equals("filesize")) {
				if (sortOrder.equals("ascending")) {
					session.setAttribute("filesizeSortToggle", "desc");
				} else if (sortOrder.equals("descending")) {
					session.setAttribute("filesizeSortToggle", "asc");
				}
			}

			messages += "Found sort column: " + sortColumn + " by " + sortOrder + "\n";

			// Finally, since sortColumn and order were defined,
			// we remember these sorting constraints for next time by setting
			// them as cookies and as attributes of the Session

			// Create cookie, set their expiration time and then add them to the
			// response (they go into the response header)
			// so that the browser can then store them on its end
			Cookie sortColumnCookie = new Cookie("sortColumn", sortColumn);
			sortColumnCookie.setMaxAge(COOKIE_EXPIRATION_TIME);
			response.addCookie(sortColumnCookie);

			Cookie sortOrderCookie = new Cookie("sortOrder", sortOrder);
			sortOrderCookie.setMaxAge(COOKIE_EXPIRATION_TIME);
			response.addCookie(sortOrderCookie);

			session.setAttribute("sortColumn", sortColumn);
			session.setAttribute("sortOrder", sortOrder);
		}

		return fileDataList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public class FileInfo implements Serializable {
		private File thumbPath;
		private long fullfileSize;
		private File fullFile;
		private String thumbDisplay;

		public FileInfo() {
		}

		public FileInfo(File fullfile, long size) {
			this.fullFile = fullfile;
			this.fullfileSize = size;
			this.thumbPath = null;
			this.thumbDisplay = null;
		}

		public FileInfo(File thumbfile, long size, File fullfile) {
			this.thumbPath = thumbfile;
			this.fullfileSize = size;
			this.fullFile = fullfile;
			this.thumbDisplay = thumbDisplayString();
		}

		public File getThumbPath() {
			return thumbPath;
		}

		public void setThumbPath(File thumbPath) {
			this.thumbPath = thumbPath;
		}

		public long getFullfileSize() {
			return fullfileSize;
		}

		public void setFullfileSize(long fullfileSize) {
			this.fullfileSize = fullfileSize;
		}

		public File getFullFile() {
			return fullFile;
		}

		public void setFullFile(File fullFile) {
			this.fullFile = fullFile;
		}

		public String getThumbDisplay() {
			return thumbDisplay;
		}

		public void setThumbDisplay(String thumbDisplay) {
			this.thumbDisplay = thumbDisplay;
		}

		// return a tidy display string for the thumbnail name
		private String thumbDisplayString() {
			String displayStr = thumbPath.getName(); // path removed
			displayStr = displayStr.replace(THUMBNAIL_SUFFIX, ""); // get rid of
																	// "_thumbnail.png"
																	// suffix
			displayStr = displayStr.replace('_', ' '); // _ replaced with space
			return displayStr;
		}

	}

	// http://stackoverflow.com/questions/4108604/java-comparable-vs-comparator
	// http://www.tutorialspoint.com/java/java_using_comparator.htm
	// No natural ordering for FileInfo, since we can sort by filesize OR
	// filename
	// therefore implement Comparator for custom sorting
	public class FileInfoComparator implements Comparator<FileInfo> {
		private String sortField;
		private String sortOrder;

		public FileInfoComparator(String sortField, String sortOrder) {
			this.sortField = sortField; // class member variable called
										// 'sortField' is assigned the value of
										// parameter also called 'sortField'
			this.sortOrder = sortOrder;
		}

		public int compare(FileInfo fi1, FileInfo fi2) {

			if (sortField.equals("filename")) {
				if (sortOrder.equals("ascending")) {
					return fi1.thumbDisplay.compareTo(fi2.thumbDisplay);
				} else { // descending
					return fi2.thumbDisplay.compareTo(fi1.thumbDisplay);
				}
			} else if (sortField.equals("filesize")) {
				// can't do return fi1.fullfileSize - fi2.fullfileSize; for
				// ascending sort
				// or return fi2.fullfileSize - fi1.fullfileSize; for descending
				// sort
				// since the type of fullfileSize is long and the return value
				// of this function is int

				// Using the Java Object Wrapper "Long" to wrap basic data type
				// long
				// and then use its object method compareTo() as we did with
				// String.compareTo above

				Long fileSize1 = new Long(fi1.fullfileSize);
				Long fileSize2 = new Long(fi2.fullfileSize);

				if (sortOrder.equals("ascending")) {
					return fileSize1.compareTo(fileSize2);

				} else { // descending
					return fileSize2.compareTo(fileSize1);
				}
			} else {
				System.err.println("Meaningless comparison of FileInfos on unknown " + sortField + ". Not sorting.");
				return 0;
			}
		}
	}

}
