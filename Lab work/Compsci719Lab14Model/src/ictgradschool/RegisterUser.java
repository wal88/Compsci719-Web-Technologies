package ictgradschool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */
//@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int MAX_FORM_FIELDS = 4;
	
	private String jsonFolder;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init( ){
        // the location where uploads are to be stored
        //filePath = getServletContext().getInitParameter("upload-location");
        ServletContext servletContext = getServletContext();
    	jsonFolder = servletContext.getRealPath("/user-data");
    }	
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//HttpSession session = request.getSession(false); // don't create one if it doesn't exist		
		// DO create a session if one didn't exist. But if so, session.isNew() will be true to indicate it was just created.
		HttpSession session = request.getSession(); 
		
		PrintWriter out = response.getWriter();
		
		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Register User</title>");
		out.println("<meta charset='UTF-8' />");
		out.println("</head>");
		out.println("<body>");
		
		
		// if there are no request params or no existing session: produce the user registration form
		Enumeration<String> params = request.getParameterNames();
		
		//if(!params.hasMoreElements() && session == null) { // if using getSession(false)		
		if(session.isNew()) {
			
			if(!params.hasMoreElements()) {
				
				System.err.println("New session and no parameters: present empty registration form to new user");
				
				Properties formFields = new Properties();
				createForm(formFields, out, false); // will create an empty form (empty formFields) with editable fields (form onlyForDisplay=false)
			} else {
				System.err.println("New session and parameters. Perhaps these are get parameters");
			}	
		
		} else { // existing session
			//response.getWriter().append("Served at: ").append(request.getContextPath());
			
			String sessionID = session.getId();
			File userFile = new File(jsonFolder, sessionID + ".json");			
			
			if(params.hasMoreElements()) { // existing session with parameters: user filled in form and pressed register
			
				System.err.println("Existing session with parameters: user filled in form and pressed Register");
				
				int numFormFieldsFilledIn = storeFormData(userFile, request);
				
				// if the form is partially filled in
				if(numFormFieldsFilledIn != MAX_FORM_FIELDS) {
					out.println("<p>The data you've entered has been saved.</p>");
					out.println("<p>To continue your registration click <a href='/Compsci719Lab14Model/RegisterUser'>here</a>.</p>"); // ?continue=yes
				}
				else { // form was completely filled in, reload the RegisterUser servlet page and display values 
					response.sendRedirect("/Compsci719Lab14Model/RegisterUser"); // ?continue=no
					
				}
			} else { // existing session with no parameters: user clicked on "Continue with registration" link after incomplete submission, 
					 // OR the user had filled in all fields and pressed Register.
				System.err.println("Existing session with no parameters: user clicked on 'Continue with registration' link.");
				
				// read data from json file
				if(!userFile.exists()) {
					System.err.println("User file " + userFile + " does not exist. Expected to find it.");
				} else {
					out.println("<p>Loading session data from userfile: " + userFile + ".</p>");
					
					Properties formFields = readJSONData(userFile);
					
					if (formFields.size() != MAX_FORM_FIELDS) { // still need to finish filling in form 
																//if(request.getParameter("continue").equals("yes")) { 
						createForm(formFields, out, false); // false for editable fields 
					} else {
						createForm(formFields, out, true); // true that the form's only for display: no editable fields and no register button
					}	
				}
			}
			
		}		
		
		// close off the HTML page
		out.println("</body>");
		out.println("</html>");
	}
	
	
	protected Properties readJSONData(File userFile) {
		Properties userDataProperties = new Properties();
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(userFile));
			String line = "";
			JSONObject jsonRecord = null;
			
			while((line = reader.readLine()) != null) { // while there is another line to read, read it into String line and process
				
				// the JSON object is stored encoded as a String
				// Decode the String into a JSON object by parsing the String
				// Using JSON-Simple https://code.google.com/p/json-simple/wiki/DecodingExamples
				
				jsonRecord = (JSONObject)JSONValue.parse(line);
			}
			
			// iterate over name-value pairs in the JSON record, putting them into the Properties object
			Iterator jsonNames = jsonRecord.keySet().iterator();
			while(jsonNames.hasNext()) {  
				String name = (String)jsonNames.next();
				String value = (String)jsonRecord.get(name);
				//userDataProperties.setProperty("firstname", (String)jsonRecord.get("firstname"));
				userDataProperties.setProperty(name, value);
			}	
			
		} catch(IOException ioe) {
			System.err.println("Could not read file " + userFile + ": " + ioe.getMessage());				
		} finally {
			try {
				reader.close();
			} catch(Exception e) {
				System.err.println("Could not close file " + userFile + ": " + e.getMessage());
				reader = null;
			}
			
		}
		
		return userDataProperties;
	}
	
	protected int storeFormData(File userFile, HttpServletRequest request) {
		FileWriter fileOut = null;
		int numFormFieldsFilledIn = 0;		
		
		try {
			fileOut = new FileWriter(userFile);
		
			// Using JSON-Simple.

			// https://code.google.com/p/json-simple/wiki/DecodingExamples		
			// https://code.google.com/p/json-simple/wiki/EncodingExamples		
			
			// Want to encode the following:
			
			JSONObject jsonRecord =new JSONObject();			
			
			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				//fileOut.write("firstname:"+ );
				String formFieldName = paramNames.nextElement();
				
				if(!formFieldName.equals("submit_button")) {
					String value = request.getParameter(formFieldName);
					
					if(value != null && !value.equals("")) {						
						jsonRecord.put(formFieldName, value);					
						numFormFieldsFilledIn++;
					}
				}	
			}			
			
			String jsonText = jsonRecord.toJSONString(); 
			fileOut.write(jsonText);
			fileOut.flush();
			
		} catch(IOException ioe) {
			System.err.println("Could not write file " + userFile + ": " + ioe.getMessage());				
		} finally {
			try {
				fileOut.close();
			} catch(Exception e) {
				System.err.println("Could not close file " + userFile + ": " + e.getMessage());
				fileOut = null;
			}
			
		}
		
		return numFormFieldsFilledIn;
	}
	
	// creates the User Registration form using any pre-existing values in the formFields Properties object
	// If editableFields is false, the fields are readonly and the register button is disabled
	protected void createForm(Properties formFields, PrintWriter out, boolean onlyDisplay) {
		
		// prefill values
		String firstname = formFields.getProperty("firstname", "");
		String lastname = formFields.getProperty("lastname", "");
		String username = formFields.getProperty("username", "");
		String email = formFields.getProperty("email", "");
		
		String editability = "";
		if(onlyDisplay) {
			editability = "' readonly='readonly"; 
				// the spaces and nonsymmetrical single quotes are to allow chaining editability to an existing HTML string further below			
		}
		
		out.println("<form style='width:500px;margin:auto;' id='userform_id' name='userform' method='post' action='/Compsci719Lab14Model/RegisterUser'>");
		
		if(onlyDisplay) { // only displaying form, 'static' data
			out.println("<fieldset><legend>Your user data:</legend>");
		} else { // filling out the form
			out.println("<fieldset><legend>Register as a user:</legend>");
		}
		
		// http://stackoverflow.com/questions/421046/what-are-all-of-the-allowable-characters-for-peoples-names
		// http://stackoverflow.com/questions/6005459/is-there-a-way-to-match-any-unicode-non-alphabetic-character
		// . will match any unicode character, see http://www.regular-expressions.info/unicode.html
		// (Java: http://stackoverflow.com/questions/4304928/unicode-equivalents-for-w-and-b-in-java-regular-expressions)
		// last name is optional
		// If storing such as in a DB, may have to limit the allowed characters to what chars can be stored
		
		out.println("<p><label for='fname_id'>First name (no digits):</label><br />");
		out.println("<input type='text' size='50' name='firstname' id='fname_id' pattern='\\D+' value='" + firstname + editability + "' /></p>");
				// pattern='.+' to allow all unicode. But \D to disallow digits
		
		out.println("<p><label for='lname_id'>Last name (no digits):</label><br />");
		out.println("<input type='text' size='50' name='lastname' id='lname_id' pattern='\\D*' value='" + lastname + editability + "' /></p>");
		
		// some systems allow basic keyboard chars like @_^& for usernames, but see
		// http://stackoverflow.com/questions/2053335/what-should-be-the-valid-characters-in-usernames
		// With \w, names, numbers and underscores are allowed
		out.println("<p><label for='uname_id'>Chosen username/nickname:</label><br />");
		out.println("<input type='text' size='50' name='username' id='uname_id' pattern='[\\w]+' value='" + username + editability + "' /></p>"); 
			 
		
		// or use email input validation pattern from http://www.w3schools.com/tags/att_input_pattern.asp
		// pattern='^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$' // need to escape \ by writing \\ in Java String
		out.println("<p><label for='email_id'>Your email:</label><br />");
		out.println("<input type='email' size='50' name='email' id='email_id' value='" + email + editability + "' /></p>");			
		
		
		// Submit button
		if(!onlyDisplay) {
			out.println("<input type='submit' name='submit_button' id='submit_id' value='Register' /></p>");
		}
		out.println("</fieldset>");
		out.println("</form>");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
