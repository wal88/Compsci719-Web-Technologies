<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<li><span><b>Name:</b>
   <%= request.getParameter("name")%><br/>
</span></li>
<li><span><b>E-mail</b>
   <%= request.getParameter("E-Mail")%><br/>
</span></li>
<li><span><b>city</b>
   <%= request.getParameter("city")%><br/>
</span></li>
<li><span><b>describe</b>
   <%= request.getParameter("describe")%><br/>
</span></li>
<li><span><b>rating</b>
   <%= request.getParameter("rate")%><br/>
</span></li>
<li><span><b>favourite animal</b>
   <%= request.getParameter("sel")%><br/>
</span></li>
<li><span><b>comments</b>
   <%= request.getParameter("comm")%><br/>
</span></li>
</body>

</html>