package ictgradschool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * Servlet implementation class FormDataList
 */
//@WebServlet("/FormDataList")
public class FormDataList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormDataList() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    protected void displayBasic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Enumeration<String> paramNames = request.getParameterNames();
		request.getp
		PrintWriter out = response.getWriter();
		
		out.println("<html>\n<head><title>FormDataList Server response</title>");		
		out.println("</head>\n<body>");
		
		out.println("<pre>");
		
		while(paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if(paramName.equals("display") || paramName.equals("sort")) continue; // skip the hidden fields
			
			String[] values = request.getParameterValues(paramName);
			
			
			out.append(paramName);
			out.append(": ");
			
			if (values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					out.append(values[i]);
					if(values.length > i+1) out.append(", ");
				}
			}
			
			out.append("\n");
		}
		
		out.println("</pre>");
		out.println("</body></html>");
    }
    
    
    protected void displayFancy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Enumeration<String> paramNamesEnumeration = request.getParameterNames();
    	
    	// To sort the parameter names, convert the Enumeration into a List using Collections.list(Enumeration)
    	// Since Strings have a natural ordering, we can then use Collections.sort(list) for sorting by ascending order
    	// and Collections.sort(list, Collections.reverseOrder()) to sort by descending order
    	ArrayList<String> paramNames = Collections.list(paramNamesEnumeration);    	
    	String sortOrder = request.getParameter("sort");
    	if(sortOrder == null || sortOrder.equals("ascending")) {
    		Collections.sort(paramNames);
			
		} else if (sortOrder.equals("descending")) {
			Collections.sort(paramNames, Collections.reverseOrder());
		}		
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>\n<head><title>FormDataList Server response</title>");		
		out.println("</head>\n<body>");
		
		out.println("<table>");
		out.println("<tr><th>parameter name</th><th>parameter values</th></tr>");
		
		Iterator<String> iterator = paramNames.iterator();
		while(iterator.hasNext()) {
			String paramName = iterator.next();
			if(paramName.equals("display") || paramName.equals("sort")) continue; // skip the hidden fields
			
			String[] values = request.getParameterValues(paramName);
			
			out.append("<tr><td>");
			out.append(paramName);			
			
			out.append("</td><td>");
			
			if (values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					out.append(values[i]);
					if(values.length > i+1) out.append(", ");
				}
			}
			
			out.append("</td></tr>\n");
		}

		out.println("</table>");
		//out.println("<p>To be implemented.</p>");

		out.println("</body></html>");
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		String displayMethod = request.getParameter("display");
		
		if(displayMethod != null && displayMethod.equals("fancy")) {
			displayFancy(request, response);
			
		} else {
			displayBasic(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
