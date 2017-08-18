package ImageGalleryDisplay;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Comparator;

/**
 * Servlet implementation class ImageGalleryDisplay
 */
// @WebServlet("/ImageGalleryDisplay")
public class ImageGalleryDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String filePath;
	String filename;
	String filesize;
	Map<String, String> nameMap;
	Map<Long, String> sizeMap;
	private final String IMG_FOLDER = "/Compsci719Lab14a/Photos/";

	Cookie cookie;
	Cookie cookie2;
	HttpSession session;
	// HttpSession session2;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageGalleryDisplay() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		filePath = servletContext.getRealPath("/Photos");
		String sortColumnPara = request.getParameter("sortColumn");
		String sortOrderPara = request.getParameter("order");
		

		PrintWriter out = response.getWriter();

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		Cookie ck[];
		ck = request.getCookies();
		session = request.getSession(true);



		if (sortOrderPara==null || sortColumnPara==null) {
			
			for (Cookie c : ck) {
				if (c.getName().equals("sortColumnPara")) {
					sortColumnPara = c.getValue();
				} else if (c.getName().equals("sortOrderPara")) {
					sortOrderPara = c.getValue();
				}
			}
			
			if (sortOrderPara==null || sortColumnPara==null) {
				
				if (session.getAttribute("sortColumnPara") != null && session.getAttribute("sortOrderPara") != null) {
					sortColumnPara = session.getAttribute("sortColumnPara").toString();
					sortOrderPara = session.getAttribute("sortOrderPara").toString();
				} else {
					sortColumnPara = "filename";
					sortOrderPara = "ascending";
				}
			}

		}

		if (sortOrderPara.equals("ascending")) {
			nameMap = new TreeMap<String, String>();
			sizeMap = new TreeMap<Long, String>();
		} else if (sortOrderPara.equals("descending")) {
			nameMap = new TreeMap<String, String>(new Comparator<String>() {
				public int compare(String o1, String o2) {
					return o2.compareTo(o1);
				}
			});
			sizeMap = new TreeMap<Long, String>(new Comparator<Long>() {
				public int compare(Long o1, Long o2) {
					return (int) (o2 - o1);
				}
			});
		}

		String html = "";
		String name;
		String jpg = ".jpg";
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				name = listOfFiles[i].getName();
				char a = name.charAt(name.length() - 3);
				if (a == 'p') {


					html = "<a href='" + IMG_FOLDER + name.substring(0, name.indexOf("_thu")) + jpg + "'><img src='"
							+ IMG_FOLDER + name + "' /></a><br>" + name + "<br>" + listOfFiles[i].length() + "<br>";
					nameMap.put(name, html);
					sizeMap.put(listOfFiles[i].length(), html);
				}
			}
		}

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet upload</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Uploaded directory: " + filePath + "<br>");



		cookie = new Cookie("sortColumnPara", sortColumnPara);
		cookie2 = new Cookie("sortOrderPara", sortOrderPara);

		cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
		cookie2.setMaxAge(60 * 60 * 24 * 30);
		response.addCookie(cookie);
		response.addCookie(cookie2);
		

		session.setAttribute("sortColumnPara", sortColumnPara);
		session.setAttribute("sortOrderPara", sortOrderPara);
		

		if (sortColumnPara.equals("filename")) {
			

			if (sortOrderPara.equals("ascending")) {
				out.print(
						"<a href=\"ImageGalleryDisplay?sortColumn=filename&order=descending\">filename &#x25B2</a>\t");
				out.println("<a href=\"ImageGalleryDisplay?sortColumn=filesize&order=ascending\">filesize</a><br><br>");
				for (String s : nameMap.keySet()) {
					out.println(nameMap.get(s));
				}
			} else if (sortOrderPara.equals("descending")) {
				out.print("<a href=\"ImageGalleryDisplay?sortColumn=filename&order=ascending\">filename &#x25BC</a>\t");
				out.println("<a href=\"ImageGalleryDisplay?sortColumn=filesize&order=ascending\">filesize</a><br><br>");
				for (String s : nameMap.keySet()) {
					out.println(nameMap.get(s));
				}
			}

		} else if (sortColumnPara.equals("filesize")) {
			

			if (sortOrderPara.equals("ascending")) {
				out.print("<a href=\"ImageGalleryDisplay?sortColumn=filename&order=ascending\">filename</a>\t");
				out.println(
						"<a href=\"ImageGalleryDisplay?sortColumn=filesize&order=descending\">filesize &#x25B2</a><br><br>");
				for (Long l : sizeMap.keySet()) {
					out.println(sizeMap.get(l));
				}
			} else if (sortOrderPara.equals("descending")) {
				out.print("<a href=\"ImageGalleryDisplay?sortColumn=filename&order=ascending\">filename</a>\t");
				out.println(
						"<a href=\"ImageGalleryDisplay?sortColumn=filesize&order=ascending\">filesize &#x25BC</a><br><br>");
				for (Long l : sizeMap.keySet()) {
					out.println(sizeMap.get(l));
				}
			}

		}

		out.println("</body>");
		out.println("</html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
