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
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */

public class question2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int COMPLETED_FORM_COUNT = 3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public question2() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Register for ICT GradSchool Newsletter</title>");
		out.println("<meta charset='UTF-8' />");
		out.println("</head>");
		out.println("<body>");

		HttpSession session = request.getSession();

		Enumeration<String> params = request.getParameterNames();
		if (params.hasMoreElements()) {
			// This is the result of either a submit form, or clear form

			if (request.getParameter("cleardata") != null) {

				// out.println("Need to clear the session information and
				// produce a fresh, empty, form");
				session.invalidate();
				session = request.getSession();
				createForm(session, out);
			} else {
				int numFieldsFilledIn = storeFormDataSession(request);

				if (numFieldsFilledIn == 0) {
					out.println("<p>No information entered.</p>\n"
							+ "<p>To return to the registration page, click <a href='question2'>here</a>.</p>");
				}

				else if (numFieldsFilledIn != COMPLETED_FORM_COUNT) {
					out.println("<p>The data you've entered so far has been saved.</p>\n"
							+ "<p>To continue your registration click <a href='question2'>here</a>.</p>");
				} else {
					out.println("<p>You have been registered!</p>");
				}
			}
		} else {

			createForm(session, out);
		}

		// close off the HTML page
		out.println("</body>");
		out.println("</html>");
	}

	protected int storeFormDataSession(HttpServletRequest request) {

		// Needs to be completed!
		int count = 0;
		HttpSession session = request.getSession();
		// Store the three form fields as attributes in the session
		// (for the ones that exist)
		// Return a count of how may of the fields were stored
		String[] parameters = new String[]{"firstName", "lastName", "email"};
		for (String parameter : parameters) {
			if (!request.getParameter(parameter).isEmpty()) {
				session.setAttribute(parameter, request.getParameter(parameter));
				count++;
			}
		}
		
		return count;
	}

	protected Properties getFormDataSession(HttpSession session) {

		// Needs to be completed!

		Properties userDataProperties = new Properties();

		// Retrieves the three form fields from the session
		Enumeration<String> attributeNames = session.getAttributeNames();
		
		// and stores each one that exists in the Properties object
		while(attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			userDataProperties.setProperty(attributeName, (String) session.getAttribute(attributeName));
		}
		
		return userDataProperties;
	}

	protected void createForm(HttpSession session, PrintWriter out) {

		Properties formFields = this.getFormDataSession(session);

		out.println(
				"<form style='width:500px;margin:auto;' id='userform_id' name='userform' method='get' action='question2'>");

		out.println("<fieldset><legend>Register for the ICT GradSchool Newsletter:</legend>");

		// out.println("<p>Form elements go here!</p>");
		// Generate appropriate input form fields for:

		/*
		 * 'Pre-fetch' fields in the form if they exist in the variable
		 * 'formFields', otherwise set empty text
		 */
		String firstNameText = formFields.containsKey("firstName") ? formFields.getProperty("firstName") : "";
		String lastNameText = formFields.containsKey("lastName") ? formFields.getProperty("lastName") : "";
		String emailText = formFields.containsKey("email") ? formFields.getProperty("email") : "";

		// firstname
		out.println("<div><label for='firstName' >First Name:</label><br/>"
				+ "<input id='firstName' name='firstName' type='text' value='" 
				+ firstNameText 
				+ "' /></div>");

		// lastName
		out.println("<div>" + "<label for='lastName' >Last Name (no digits):</label><br/>"
				+ "<input id='lastName' name='lastName' type='text' pattern='[a-zA-Z\\-]*' value='" 
				+ lastNameText
				+ "' /></div>");

		// email
		out.println("<div>" + "<label for='email' >Your email:</label><br/>"
				+ "<input id='email' name='email' type='email'' value='" 
				+ emailText 
				+ "' /></div>");

		
		
		out.println("<input type='submit' name='submit_button' id='submit_id' value='Register' /></p>");

		out.println("</fieldset>");
		out.println("</form>");

		out.println(
				"<form  style='width:500px;margin:auto;' id='clearform_id' name='clearform' method='get' action='question2'>");
		out.println("<input type='hidden' name='cleardata' id='cleardata_id' value='clear' /></p>");
		out.println("<input type='submit' name='clear_button' id='clear_id' value='Clear Fields' /></p>");

		out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
