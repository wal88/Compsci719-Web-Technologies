package ictgradschool;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");

	      PrintWriter out = response.getWriter();
	      String title = "Register User";
			
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " +
	         "transitional//en\">\n";
				
	      out.println("<form action=\"RegisterUser\" autocomplete=\"on\" method=\"post\" name=\"questionnaire\"><br>    <fieldset>        <legend>Register as a user:</legend>        <label>First Name:</label><br>        <input name=\"firstName\" placeholder=\"Enter your first name\" type=\"text\" required><br>        <br>        <label>Last Name:</label><br>        <input name=\"lastName\" placeholder=\"Enter your last name\" type=\"text\" required><br>        <br>        <label>Nick name:</label><br>        <input name=\"nickName\" placeholder=\"Enter your nickname\" type=\"text\" required><br>        <br>        <label>Email:</label><br>        <input name=\"email\" placeholder=\"Enter valid email\" type=\"email\" required><br><br>         <input name=\"register\" type=\"submit\" required>  <input name=\"Clear\" type=\"reset\" ><br>   </fieldset><br></form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter  finishPage =  response.getWriter();
		finishPage.println("<html><head><meta charset='UTF-8' /><title>Results</title></head><body>");
		finishPage.println("<p>The data you have entered has been submitted</p>");
		finishPage.println("<p>Click" +  "<a href='RegisterUser'>here</a> to continue registration</p>" );
		finishPage.println("</body></html>");
		ServletContext servletContext = getServletContext();
		String basePath = servletContext.getRealPath("/userBobs");
		String sessionName = request.getSession().getId();
		JSONObject obj=new JSONObject();
		
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> i = map.entrySet().iterator();
		
		User bob;
		UserDaoImpl bobby = new UserDaoImpl();
		String first,last,nick,ema;
		first=last=nick=ema="null";
		
		while(i.hasNext()) {
			Entry<String, String[]> entry = i.next();
			String key = entry.getKey();
			String valueString = "";
			String[] values = entry.getValue();
			for(String value: values) {
				valueString += value;
			}			
			obj.put(key, valueString);
			switch (key) {
			case "firstName":
				first = valueString;
				break;
			case "lastName":
				last = valueString;
				break;
			case "nickName":
				nick = valueString;
				break;
			case "email":
				ema = valueString;
				break;
			}
			
			
		}
		bob = new User(first, last, nick, ema);
		bobby.addUser(bob);
		
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString(); 
		System.out.print(jsonText);
		
		try (FileWriter file = new FileWriter(basePath + "/"+ sessionName + ".json")) {
			
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
			System.out.println(basePath + "/" +sessionName + ".json");
		}
		
 
		
	}
	
	
		 
		     
		      
		   
		

}
