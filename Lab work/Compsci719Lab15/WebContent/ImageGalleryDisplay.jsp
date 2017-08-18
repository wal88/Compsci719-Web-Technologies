<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.io.File, java.io.IOException, java.io.PrintWriter, java.util.Comparator, java.util.Map, java.util.TreeMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DisplayGalleryDisplay</title>
</head>
<body>
	<table>
		<%!private final String IMAGE_FOLDER = "/Compsci719Lab15/Photos/";
	Map<String, File> nameMap;
	Map<Long, File> sizeMap;
	Cookie cookie1;
	Cookie cookie2;
	HttpSession session;%>

		<%
			ServletContext servletContext = getServletContext();
			String filePath = servletContext.getRealPath("/Photos");
			String sortColumnPara = request.getParameter("sortColumn");
			String sortOrderPara = request.getParameter("order");

			Cookie ck[];
			ck = request.getCookies();
			session = request.getSession(true);

			if (sortOrderPara == null || sortColumnPara == null) {
				if (ck != null) {
					for (Cookie c : ck) {
						if (c.getName().equals("sortColumnPara")) {
							sortColumnPara = c.getValue();
						} else if (c.getName().equals("sortOrderPara")) {
							sortOrderPara = c.getValue();
						}
					}
				}
				if (sortOrderPara == null || sortColumnPara == null) {
					if (session.getAttribute("sortColumnPara") != null
							&& session.getAttribute("sortOrderPara") != null) {
						sortColumnPara = session.getAttribute("sortColumnPara").toString();
						sortOrderPara = session.getAttribute("sortOrderPara").toString();
					}
				}
			}
			if (sortOrderPara == null || sortColumnPara == null) {
				sortColumnPara = "filename";
				sortOrderPara = "ascending";
			}
			if (sortOrderPara.equals("ascending")) {
				nameMap = new TreeMap<String, File>();
				sizeMap = new TreeMap<Long, File>();
			} else if (sortOrderPara.equals("descending")) {
				nameMap = new TreeMap<String, File>(new Comparator<String>() {
					public int compare(String o1, String o2) {
						return o2.compareTo(o1);
					}
				});
				sizeMap = new TreeMap<Long, File>(new Comparator<Long>() {
					public int compare(Long o1, Long o2) {
						return (int) (o2 - o1);
					}
				});
			}

			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].toString().contains("jpg")) {
					nameMap.put(listOfFiles[i].getName(), listOfFiles[i]);
					sizeMap.put(listOfFiles[i].length(), listOfFiles[i]);
				}
			}

			cookie1 = new Cookie("sortColumnPara", sortColumnPara);
			cookie2 = new Cookie("sortOrderPara", sortOrderPara);
			cookie1.setMaxAge(60 * 60 * 24 * 30);
			cookie2.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			session.setAttribute("sortColumnPara", sortColumnPara);
			session.setAttribute("sortOrderPara", sortOrderPara);

			if (sortColumnPara.equals("filename")) {
				if (sortOrderPara.equals("ascending")) {
					out.println("<tr>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filename&order=descending\">File Name &#x25B2</a></th>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filesize&order=ascending\">File Size</a></th>");
					out.println("<th>Thumbnail</th>");
					out.print("</tr>");
					for (String s : nameMap.keySet()) {
						out.println("<tr>");
						out.println(
								"<td>" + nameMap.get(s).getName().substring(0, nameMap.get(s).getName().indexOf('.'))
										+ "</td>");
						out.println("<td>" + nameMap.get(s).length() / 1024 + "KB</td>");
						out.println("<td><a href='" + IMAGE_FOLDER + nameMap.get(s).getName() + "'><img src='"
								+ IMAGE_FOLDER
								+ nameMap.get(s).getName().substring(0, nameMap.get(s).getName().indexOf('.'))
								+ "_thumbnail.png'/></a></td>");
						out.print("</tr>");
					}
				} else if (sortOrderPara.equals("descending")) {
					out.println("<tr>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filename&order=ascending\">File Name &#x25BC</a></th>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filesize&order=ascending\">File Size</a></th>");
					out.println("<th>Thumbnail</th>");
					out.print("</tr>");
					for (String s : nameMap.keySet()) {
						out.println("<tr>");
						out.println(
								"<td>" + nameMap.get(s).getName().substring(0, nameMap.get(s).getName().indexOf('.'))
										+ "</td>");
						out.println("<td>" + nameMap.get(s).length() / 1024 + "KB</td>");
						out.println("<td><a href='" + IMAGE_FOLDER + nameMap.get(s).getName() + "'><img src='"
								+ IMAGE_FOLDER
								+ nameMap.get(s).getName().substring(0, nameMap.get(s).getName().indexOf('.'))
								+ "_thumbnail.png'/></a></td>");
						out.print("</tr>");
					}
				}

			} else if (sortColumnPara.equals("filesize")) {
				if (sortOrderPara.equals("ascending")) {
					out.println("<tr>");
					out.print("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filename&order=ascending\">File Name</a></th>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filesize&order=descending\">File Size &#x25B2</a></th>");
					out.println("<th>Thumbnail</th>");
					out.print("</tr>");
					for (Long l : sizeMap.keySet()) {
						out.println("<tr>");
						out.println(
								"<td>" + sizeMap.get(l).getName().substring(0, sizeMap.get(l).getName().indexOf('.'))
										+ "</td>");
						out.println("<td>" + sizeMap.get(l).length() / 1024 + "KB</td>");
						out.println("<td><a href='" + IMAGE_FOLDER + sizeMap.get(l).getName() + "'><img src='"
								+ IMAGE_FOLDER
								+ sizeMap.get(l).getName().substring(0, sizeMap.get(l).getName().indexOf('.'))
								+ "_thumbnail.png'/></a></td>");
						out.print("</tr>");
					}
				} else if (sortOrderPara.equals("descending")) {
					out.print("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filename&order=ascending\">File Name</a></th>");
					out.println("<th><a href=\"ImageGalleryDisplay.jsp;jsessionid=" + session.getId()
							+ "?sortColumn=filesize&order=ascending\">File Size &#x25BC</a></th>");
					out.println("<th>Thumbnail</th>");
					out.print("</tr>");
					for (Long l : sizeMap.keySet()) {
						out.println("<tr>");
						out.println(
								"<td>" + sizeMap.get(l).getName().substring(0, sizeMap.get(l).getName().indexOf('.'))
										+ "</td>");
						out.println("<td>" + sizeMap.get(l).length() / 1024 + "KB</td>");
						out.println("<td><a href='" + IMAGE_FOLDER + sizeMap.get(l).getName() + "'><img src='"
								+ IMAGE_FOLDER
								+ sizeMap.get(l).getName().substring(0, sizeMap.get(l).getName().indexOf('.'))
								+ "_thumbnail.png'/></a></td>");
						out.print("</tr>");
					}
				}
			}
		%>
	</table>
</body>
</html>