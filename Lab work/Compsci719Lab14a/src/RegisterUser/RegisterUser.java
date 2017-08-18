package RegisterUser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class RegisterUser
 */
//@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filePath;
	String firstnameText = "";
	String lastnameText = "";
	String usernameText = "";
	String useremailText = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		if(!(firstnameText.equals("")||lastnameText.equals("")||usernameText.equals("")||useremailText.equals(""))){
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Register</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form method=\"POST\" action=\"RegisterUser\">");
			out.println("<fieldset>");
			out.println("<legend>Register as a user</legend>");
			out.println("<label for=\"firstname\">First name (no digits):</label><br>");
			out.println("<input id=\"firstname\" name=\"firstname\" value=\"" + firstnameText + "\" type=\"text\" readonly><br><br>");
			out.println("<label for=\"lastname\">Last name (no digits):</label><br>");
			out.println("<input id=\"lastname\" name=\"lastname\" value=\"" + lastnameText + "\" type=\"text\" readonly><br><br>");
			out.println("<label for=\"username\">Chosen username/nickname:</label><br>");
			out.println("<input id=\"username\" name=\"username\" value=\"" + usernameText + "\" type=\"text\" readonly><br><br>");
			out.println("<label for=\"useremail\">Your email:</label><br>");
			out.println("<input id=\"useremail\" name=\"useremail\" value=\"" + useremailText + "\" type=\"email\" readonly><br><br>");
			out.println("</fieldset>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		} else {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Register</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form method=\"POST\" action=\"RegisterUser\">");
			out.println("<fieldset>");
			out.println("<legend>Register as a user</legend>");
			out.println("<label for=\"firstname\">First name (no digits):</label><br>");
			out.println("<input id=\"firstname\" name=\"firstname\" value=\"" + firstnameText + "\" type=\"text\"><br><br>");
			out.println("<label for=\"lastname\">Last name (no digits):</label><br>");
			out.println("<input id=\"lastname\" name=\"lastname\" value=\"" + lastnameText + "\" type=\"text\"><br><br>");
			out.println("<label for=\"username\">Chosen username/nickname:</label><br>");
			out.println("<input id=\"username\" name=\"username\" value=\"" + usernameText + "\" type=\"text\"><br><br>");
			out.println("<label for=\"useremail\">Your email:</label><br>");
			out.println("<input id=\"useremail\" name=\"useremail\" value=\"" + useremailText + "\" type=\"email\"><br><br>");
			out.println("<input id=\"register\" type=\"submit\" value=\"Register\"><br>");
			out.println("</fieldset>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		ServletContext servletContext = getServletContext();
		filePath = servletContext.getRealPath("/json") + "\\";
		HttpSession session = request.getSession();
		String id = session.getId();
		JSONObject obj1 = new JSONObject();
		if (request.getParameter("firstname") != null)
			obj1.put("firstname", request.getParameter("firstname"));
		if (request.getParameter("lastname") != null)
			obj1.put("lastname", request.getParameter("lastname"));
		if (request.getParameter("username") != null)
			obj1.put("username", request.getParameter("username"));
		if (request.getParameter("useremail") != null)
			obj1.put("useremail", request.getParameter("useremail"));
		File json = new File(filePath + id + ".json");
		FileWriter writer = new FileWriter(json);
		writer.write(obj1.toJSONString());
		// writer.flush();
		writer.close();

		JSONParser parser = new JSONParser();
		try {
			Object obj2 = parser.parse(new FileReader(json));
			JSONObject jsonObject = (JSONObject) obj2;
			firstnameText = (String) jsonObject.get("firstname");
			lastnameText = (String) jsonObject.get("lastname");
			usernameText = (String) jsonObject.get("username");
			useremailText = (String) jsonObject.get("useremail");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Register</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>Information has been saved.");
		out.println("<p>To continue your registration, <a href=\"RegisterUser\">click</a> here.<p>");
		out.println("</body>");
		out.println("</html>");
	}

}
