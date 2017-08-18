package ictgradschool;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormDataList
 */
// @WebServlet("/FormDataList")
public class FormDataList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormDataList() {
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
		PrintWriter out = response.getWriter();
		Map<String, String[]> map = request.getParameterMap();
		if (map.containsKey("display") && map.get("display")[0].equals("fancy")) {
			displayFancy(request, response);
		} else {
			displayBasic(request, response);
		}
		
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

	protected void displayBasic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> i = map.entrySet().iterator();

		while (i.hasNext()) {
			Entry<String, String[]> entry = i.next();
			String key = entry.getKey();
			String[] values = entry.getValue();
			out.print(key + "=[ ");
			for (String value : values) {
				out.print("'" + value + "' ");
			}
			out.print("]\n");
		}
		
	}

	protected void displayFancy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> i = map.entrySet().iterator();
		out.format("%32s%32s\n", "Parameter", "Value");
		while (i.hasNext()) {
			Entry<String, String[]> entry = i.next();
			String key = entry.getKey();
			if(key.equals("display")){
				continue;
			}
			String[] values = entry.getValue();
			//out.print(key + "=[ ");
			for (String value : values) {
				out.format("%32s%32s\n", key, value);
			}
		}

	}

}
