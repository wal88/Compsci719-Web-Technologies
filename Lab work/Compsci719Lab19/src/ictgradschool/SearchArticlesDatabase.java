package ictgradschool;

import java.sql.*;
import org.apache.derby.jdbc.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * Servlet implementation class SearchArticlesDB */
public class SearchArticlesDatabase extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		try {
			Class cd = Class.forName("org.apache.derby.jdbc.ClientDriver");
			try (Connection conn = DriverManager
					.getConnection("jdbc:derby://localhost:1527/Lab18Ex02_Articles;create=true", "APP", "APP")) {
				// you use the connection here
				try (Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM articles WHERE title LIKE '%Kate%'")) {
					while (rs.next()) {
						int numColumns = rs.getMetaData().getColumnCount();
						
						String[] columnNames = new String[]{"Article Number: ", "Article's name: ", "Article text: "};
						
						for (int i = 1; i <= numColumns; i++) {
							System.out.println(columnNames[i-1] + "\n" + rs.getObject(i));
						}
						System.out.println();
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	/* @see HttpServlet#HttpServlet() */
	public SearchArticlesDatabase() {
		super();
	}

	/***
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String query = request.getParameter("query");

		try {
			Class cd = Class.forName("org.apache.derby.jdbc.ClientDriver");
			try (Connection conn = DriverManager
					.getConnection("jdbc:derby://localhost:1527/Lab18Ex02_Articles;create=true", "APP", "APP")) {
				// you use the connection here
				try (Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM articles WHERE UPPER(title) LIKE '%" + query.toUpperCase() + "%'")) {
					while (rs.next()) {
						int numColumns = rs.getMetaData().getColumnCount();

						String[] columnNames = new String[]{"Article Number: ", "Article's name: ", "Article text: "};
						
						for (int i = 1; i <= numColumns; i++) {
							out.println(columnNames[i-1]);
							out.println(rs.getObject(i));
						}
						out.println();
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception except) {
			except.printStackTrace();
		}

		// with 'try-with-resources' the VM will take care of closing
		// connections

		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/***
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
