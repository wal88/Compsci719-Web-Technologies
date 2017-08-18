package ictgradschool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class question1
 */

public class question1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 public enum Status { OK, FileExists, IOWriteError, IOCloseError };

    /**
     * @see HttpServlet#HttpServlet()
     */
    public question1() {
        super();
    }

    /** We set the directory that the transactions will be written to in the
     *  during servlet configuration in web.xml
     */
    protected String transactionDir = null;

    /**
     * 
     * @param servletConfig
     *            a ServletConfig object containing information gathered when
     *            the servlet was created, including information from web.xml
     */
    public void init(ServletConfig servletConfig) throws ServletException{
    	super.init(servletConfig);
    	this.transactionDir = servletConfig.getInitParameter("transaction-directory");
    }
    /** init(ServletConfig) => void **/
 
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// You can uncomment the following line to check the Web server
		// if necessary
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();
		
		String refNum = request.getParameter("refNum");
		
		// If a transaction directory hasn't been set in the web.xml, then
		// default to one within our web context (which might be in some
		// strange directory deep within .metadata in the workspace)
	    if (this.transactionDir == null) {
	  	  ServletContext servletContext = getServletContext();
	      this.transactionDir = servletContext.getRealPath("/Transactions");
	    }	
	    
	    JSONObject jsonObject = new JSONObject();
		if (request.getParameter("refNum") != null)
			jsonObject.put("refNum", request.getParameter("refNum"));
		if (request.getParameter("address") != null)
			jsonObject.put("address", request.getParameter("address"));
		if (request.getParameter("postCode") != null)
			jsonObject.put("postCode", request.getParameter("postCode"));
		if (request.getParameter("cardType") != null)
			jsonObject.put("cardType", request.getParameter("cardType"));
		if (request.getParameter("cardName") != null)
			jsonObject.put("cardName", request.getParameter("cardName"));
		if (request.getParameter("cardNo1") != null)
			jsonObject.put("cardNo1", request.getParameter("cardNo1"));
		if (request.getParameter("cardNo2") != null)
			jsonObject.put("cardNo2", request.getParameter("cardNo2"));
		if (request.getParameter("cardNo3") != null)
			jsonObject.put("cardNo3", request.getParameter("cardNo3"));
		if (request.getParameter("cardNo4") != null)
			jsonObject.put("cardNo4", request.getParameter("cardNo4"));

		File jsonFile = new File(this.transactionDir, refNum+".json");
		
		Status status = saveJSONObject(jsonFile, jsonObject);
		/*alternative implementation of the above line:
		 * FileWriter writer = new FileWriter(jsonFile);
		 * writer.write(jsonObject.toJSONString());
		 * // writer.flush();
		 * writer.close();
		 */
		
		// Remove the following line, and then start coding to solve
		// the task described in Question 1 of the test
		//out.println("<p>Location to save json file = " + jsonFile + "</p>");
		
		// Print the status of the data being saved or the error encountered
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Form Submission</title>");
		out.println("</head>");
		out.println("<body>");
		
		switch (status) {
		case OK: 
			out.println("<p>Data saved: OK</p>");
			break;
		case FileExists: 
			out.println("<p>Form data with this reference number already exists, please try again.</p>");
			break;
		case IOCloseError: 
			out.println("<p>Form data was written but the file could not be closed, please check with the website admin if the save was successful.</p>");
			break;
		case IOWriteError: 
			out.println("<p>Form data could not be written, please try again or contact the administrator.</p>");
			break;
		}
		out.println("</body>");
		out.println("</html>");
		
	}
	/** doGet(HttpServletRequest, HttpServletResponse) => void **/
	

	/**
	 * No special actions for POST so chains throught to doGet()
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/** doPost(HttpServletRequest, HttpServletResponse) => void **/
    
    
    /**
     * Writes the given JSONObject (in JSON format) to the specified file path
     * 
     * @param file
     * @param jsonRecord
     * @return true if file written successfully, false otherwise
     */
    private Status saveJSONObject(File file, JSONObject jsonRecord)
    {
    	Status status = Status.OK;
    	
    	if (file.exists()) {
    		return Status.FileExists;
    	}
    	
    	String json_string = JSONObject.toJSONString(jsonRecord);
    	
    	BufferedWriter writer = null;
    	try
    	{
    	    writer = new BufferedWriter( new FileWriter( file));
    	    writer.write( json_string);

    	}
    	catch ( IOException e)
    	{
    		status = Status.IOWriteError;
    	}
    	finally
    	{
    	    try
    	    {
    	        if ( writer != null)
    	        writer.close( );
    	    }
    	    catch ( IOException e)
    	    {
    	    	status = Status.IOCloseError;
    	    }
    	}
    	
    	return status;
    }
    /** saveJSONObject(File, JSONObject) => boolean **/
}
