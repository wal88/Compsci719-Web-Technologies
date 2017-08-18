package ictgradschool;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.PrintWriter;

//http://www.codejava.net/ides/eclipse/how-to-create-deploy-and-run-java-servlet-in-eclipse
//Additional:
//http://stackoverflow.com/questions/4076601/how-do-i-import-the-javax-servlet-api-in-my-eclipse-project
//- How do I import the javax.servlet API in my Eclipse project?
//(http://stackoverflow.com/questions/860022/wheres-javax-servlet)
//http://stackoverflow.com/questions/24995773/jetty-servlet-cannot-be-found


/**
 * Servlet implementation class CGIParams
 */
public class CGIParams extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CGIParams() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//displayBasic(request, response);
		
		displayHTML(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void displayBasic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// http://stackoverflow.com/questions/1928675/servletrequest-getparametermap-returns-mapstring-string-and-servletreques
		
		// https://docs.oracle.com/javaee/6/api/javax/servlet/ServletRequest.html
		
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> i = map.entrySet().iterator();
		response.getWriter().println("\n\nkey: values");
		
		while(i.hasNext()) {
			Entry<String, String[]> entry = i.next();
			String key = entry.getKey();
			String[] values = entry.getValue();
			response.getWriter().print("\n" + key.toUpperCase() + ": ");
			for(String value: values) {
				response.getWriter().print(value + ",");
			}			
		}
		response.getWriter().print("\n");
	} 
	
	// http://stackoverflow.com/questions/17036130/how-to-return-an-html-document-from-java-servlet
	// eclipse "jsp support not configured"
	// https://wiki.eclipse.org/Jetty/Troubleshooting#JSP_support_not_configured.3F
	// https://wiki.eclipse.org/Jetty/Howto/Run_Jetty
	// https://wiki.eclipse.org/Jetty/Troubleshooting#Jetty_has_locked_up.3F
	private void displayHTML(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// http://stackoverflow.com/questions/1928675/servletrequest-getparametermap-returns-mapstring-string-and-servletreques
		
			// https://docs.oracle.com/javaee/6/api/javax/servlet/ServletRequest.html
			
		// "/" is relative to the context root (your web-app name)
		// don't add your web-app name to the path
		
		//RequestDispatcher view = request.getRequestDispatcher("/response.html"); // works, but /response.jsp causes 500 "jsp support not configured"
        //view.forward(request, response);  
        
		
		PrintWriter out = response.getWriter();
		out.println("<html>\n<head><title>Server response</title>");
		//out.println("<link rel='stylesheet' href='/response.css' />"); //not working
		out.println("<style>");
		out.println("th, td { padding: 7px 10px; }");
		out.println("td:first-child { background-color: DeepSkyBlue; color: white; }");
		out.println("th { border-bottom: 1px solid DeepSkyBlue; }");
		out.println("td:first-child { border-right: 1px solid DeepSkyBlue; }");
		//out.println("th, tr:last-child { border-bottom: 1px solid black; }");
		//out.println("td:last-child { border-right: none; }");
		out.println("table { border-collapse: collapse; cell-spacing: 0px; }");
		out.println("</style>");
		out.println("</head>\n<body>");
		out.println("<h1>Server side response</h1>");
		out.println("<p>Thanks for your submission. The values sent to the server are as follows:</p>");
		
		
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> i = map.entrySet().iterator();
		
		out.println("<table>");
		out.println("<tr><th>param</th><th colspan='7'>values</th></tr>");
		
		while(i.hasNext()) {
			Entry<String, String[]> entry = i.next();
			String key = entry.getKey(); //.toUpperCase();
			String[] values = entry.getValue();
			
			if(key.contains("submit") || key.contains("button")) {
				continue; 
			}			
			
			int index = key.indexOf("[]");
			if(index != -1) {
				key = key.substring(0, index);
			}
			
			out.println("<tr><td>"+key+"</td>");
			
			for(String value: values) {
				//response.getWriter().print(value + ",");
				out.println("<td>"+value+"</td>");
			}
			out.println("</tr>");
		}
		
		
		out.println("</table>");
		out.println("</body></html>");
        
	}

}
