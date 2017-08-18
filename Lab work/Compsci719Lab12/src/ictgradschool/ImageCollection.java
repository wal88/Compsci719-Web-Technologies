package ictgradschool;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class ImageCollection
 */
public class ImageCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageCollection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Using JSON-Simple.
		// Want to encode the following:
		/*
		{ "name":"arctic_fox", "description":"An Arctic fox." },
		{ "name":"grazing_wombat", "description":"A wombat grazing." },
		{ "name":"Himalayan_Pika", "description":"The Himalayan pika is a mammal too." },
		{ "name":"lynx", "description":"A lynx has pointy ears." },
		{ "name":"Pallas_Cat", "description":"A Pallas cat is also called a manul." },
		{ "name":"Pallas_Cat2", "description":"Pallas cats live at high altitudes and are wild animals." },
		{ "name":"pika", "description":"Pikas occur in certain mountainous regions of the world." },
		{ "name":"quokka", "description":"The quokka is a marsupial that looks like it's always smiling." },
		{ "name":"RedPanda", "description":"The nearest Red pandas are probably in your local zoo." },
		{ "name":"wombat2", "description":"Wombats are found in Australia." },
		{ "name":"wombats", "description":"A wombat family at lunch time." }
		 */
		

		String[] names = { "arctic_fox", "grazing_wombat", "Himalayan_Pika", "lynx", "Pallas_Cat", "Pallas_Cat2", "pika", "quokka", 
				"RedPanda", "wombat2", "wombats" }; 
		String[] descriptions = {
				"An Arctic fox.",
				"A wombat grazing.",
				"The Himalayan pika is a mammal too.",
				"A lynx has pointy ears.",
				"A Pallas cat is also called a manul.",
				"Pallas cats live at high altitudes and are wild animals.",
				"Pikas occur in certain mountainous regions of the world.",
				"The quokka is a marsupial that looks like it's always smiling.",
				"The nearest Red pandas are probably in your local zoo.",
				"Wombats are found in Australia.",
				"A wombat family at lunch time." 
		};
		
		// https://code.google.com/p/json-simple/wiki/DecodingExamples		
		// https://code.google.com/p/json-simple/wiki/EncodingExamples		
		
		JSONArray list = new JSONArray();
		
		for(int i = 0; i < names.length; i++) {		
			
			JSONObject imageData=new JSONObject();
			
			imageData.put("name", names[i]);
			imageData.put("description", descriptions[i]);
			
			list.add(imageData);
		}
		
		//System.out.print(list);
		response.getWriter().append(JSONArray.toJSONString(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
