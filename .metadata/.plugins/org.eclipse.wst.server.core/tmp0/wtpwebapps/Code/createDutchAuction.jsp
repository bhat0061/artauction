<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import = "java.util.ResourceBundle"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="jquery.datetimepicker.css"/>
	<link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" href="css/Custom.css" rel="stylesheet"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><%=resource.getString("createAuction")%></title>
</head>
<body>
	<div class="container">
		<div class="card card-container">
			<div style="color: #FF0000;">${errorMessage}</div>
			<form action="CreateDutchAuctionServlet" class="form-signin" method="post" id="form" enctype="multipart/form-data">
				<h1><%=resource.getString("createDutchAuction")%></h1>
					<%=resource.getString("title")%>:<br>
					<input type="text" name="title" required="required" placeholder="<%=resource.getString("title")%>" class="form-control" autofocus value="${param.title }"><br>
					<%=resource.getString("auctionImage")%>: <br>
					<input type="file"  accept=".png, .jpg" name="filePath" required="required" class="form-control" placeholder="<%=resource.getString("auctionImage")%>" value="${param.filePath }"><br>
					<%=resource.getString("description")%>:<br>
					<input type="text" name="description" required="required" class="form-control" placeholder="<%=resource.getString("description")%>" value="${param.description }"><br>
					<%=resource.getString("initialValue")%>:<br>
					<input type="text" name="initialValue" required="required" class="form-control" placeholder="<%=resource.getString("initialValue")%>" value="${param.initialValue }"><br>
					<%=resource.getString("minimumCost")%>:<br>
					<input type="text" name="minimumCost" required="required" class="form-control" placeholder="<%=resource.getString("minimumCost")%>" value="${param.minimumCost }"><br>
					<%=resource.getString("interval")%>: <br>
					<input type="text" name="interval" required="required" class="form-control" placeholder="<%=resource.getString("interval")%>" value="${param.interval }"><br>
					<%=resource.getString("decrement")%>:<br>
					<input type="text" name="decrement" required="required" class="form-control" placeholder="<%=resource.getString("decrement")%>" value="${param.decrement }"><br>
					
					<button class="btn btn-lg btn-primary btn-block btn-signin" input type="submit" id="Button"><%=resource.getString("createAuction")%></button>
			</form>
		</div>
	</div>
</body>
</html>