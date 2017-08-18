<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ page import="java.io.*,java.util.*, javax.servlet.*,java.text.*" %>
	<% out.print("Current time is: "); %>
	
<% Date dNow = new Date( );
		SimpleDateFormat ft = 
		new SimpleDateFormat ("hh:mm:ss a zzz");
		out.print( ft.format(dNow) ); %>
</body>
</html>