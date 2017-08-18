package ictgradschool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormDataList
 */

public class FormDataList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormDataList() {
        super();
    }

	protected void displayBasic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String, String[]> map = request.getParameterMap();

		for (Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            out.print( key + "=[ ");
            for (String value: values) {
                out.print("'" + value + "' ");
            }
            out.print("]\n");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	
	protected void displayFancy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// out.println("To be implemented");
		
		Map<String, String[]> map = request.getParameterMap();
		
		out.format("+----------------------+------------------------------------------+%n");
		out.format("| Form Parameter       | Parameter Values                         |%n");
		out.format("+----------------------+------------------------------------------+%n");
		String leftAlignFormat = "| %-20s | %-40s |%n";

		for (Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.equals("display"))
            	continue;
            String[] values = entry.getValue();
            String valuesString= ""; 
            for (int i=0; i<values.length; i++) {
                if( i!=0 && i!=values.length ) {
                	valuesString += ", ";
                }
                valuesString += values[i];
            }
            out.format(leftAlignFormat, key, valuesString);
		}
		
		out.format("+----------------------+------------------------------------------+%n");
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		
		if (map.containsKey("display") && map.get("display")[0].equals("fancy")) {
			displayFancy(request, response);
		} else {
			displayBasic(request, response);
		}
		 
		/*  
        Iterator<Entry<String, String[]>> i = map.entrySet().iterator();
                
        while (i.hasNext()) {
            Entry<String, String[]> entry = i.next();
            String key = entry.getKey();
            String[] values = entry.getValue();
            out.print( key + "=[ ");
            for (String value: values) {
                out.print("'" + value + "' ");
            }
            out.print("]\n");
        }
        */
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
