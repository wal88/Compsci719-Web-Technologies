<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.File,java.io.IOException,java.io.PrintWriter,java.util.List,java.util.LinkedList,
	javax.servlet.ServletException,javax.servlet.annotation.WebServlet,javax.servlet.http.HttpServlet,
	javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse"%>
<%!private static final long serialVersionUID = 1L;
	private String directoryName = "PDF-Slides";
	private String fullDirectoryName;
	private String pdfExtension = ".pdf";

	public void init() {
		fullDirectoryName = getServletContext().getRealPath(directoryName);
	}%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Question 3: PDF Slide Display</title>
</head>
<body>
	<%
		File pdfDirectory = new File(fullDirectoryName);
		if (!pdfDirectory.exists()) {
	%>
	<p>
		The folder <%=fullDirectoryName%> does not exist.
	</p>
</body>
</html>
<%
	return;
	}

	List<File> pdfFiles = this.getFileDataList(pdfDirectory);
%>
<table>
	<tr>
		<th></th>
		<th>Presentation Title</th>
	</tr>
	<%
		for (File pdfFile : pdfFiles) {
			String pdfLink = directoryName + "/" + pdfFile.getName();
			
			String fileName = pdfFile.getName()
					.substring(0, pdfFile.getName().lastIndexOf('.')) //delete the extension and dot
					.replaceAll("[_-]", " "); // replace all _ and - with a space
			
			String tooltip = "Right click to download " + fileName;
	%>
	<tr>
		<td><a href='<%=pdfLink.toString()%>'> <img
				alt='<%=tooltip%>' src='<%=directoryName + "/pdf_icon.png"%>'
				title='<%="Right click to download " + fileName%>' /></a></td>
		<td><%=fileName%></td>
	</tr>
	<%
		}
	%>
	
</table>
</body>
</html>

<%!private List<File> getFileDataList(File folder) {
		List<File> fileDataList = new LinkedList<File>();
		File[] files = folder.listFiles(); // File objects returned can be folders
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String filename = files[i].getName();
				if (filename.endsWith(pdfExtension)) {
					fileDataList.add(files[i]);
				}
			}
		}
		return fileDataList;
	}
%>



