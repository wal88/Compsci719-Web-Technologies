<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image gallery display</title>
</head>
<body>
	<p>Photos path: " + ${photosPath} + "</p>
	<p>${messages}</p>

	<table>

		<tr>
			<th>Thumbnail</th>
			<th><a
				href='/Compsci719Lab19/ImageGalleryDisplay?sortColumn=filename&order=${filenameSortToggle}ending'>
					Filename<img
					src='/Compsci719Lab19/images/sort-${currFilenameSortToggle}.png'
					alt='icon' />
			</a></th>

			<th><a
				href='/Compsci719Lab19/ImageGalleryDisplay?sortColumn=filesize&order=${filesizeSortToggle}ending'>
					File-size <img
					src='/Compsci719Lab19/images/sort-${currFilesizeSortToggle}.png'
					alt='icon' />
			</a></th>
		</tr>

		<c:forEach items="${fileDataList}" var="fileData">
			<tr>
				<td><a href='Photos/${fileData.fullFile.name}'> <img
						src='Photos/${fileData.thumbPath.name}'
						alt='${fileData.thumbDisplay}'>
				</a></td>
				<td>${fileData.thumbDisplay}</td>
				<td>${fileData.fullfileSize}</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>