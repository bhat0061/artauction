<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import = "java.util.ResourceBundle"
    %>
<% 
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<form action="CreateEnglishAuctionServlet" class="form-signin" method="post" id="form" enctype="multipart/form-data">
		<h1> <%=resource.getString("createEnglishAuction")%></h1>
			<%=resource.getString("title")%>:<br>
			<input type="text" name="title" required="required" placeholder="<%=resource.getString("title")%>" class="form-control" autofocus value="${param.title }"><br>
			<%=resource.getString("auctionImage")%>:<br>
			<input type="file"  accept=".png, .jpg" name="filePath" required="required" class="form-control" placeholder="<%=resource.getString("auctionImage")%>" value="${param.filePath }"><br>
			<%=resource.getString("description")%>:<br>
			<input type="text" name="description" required="required" class="form-control" placeholder="<%=resource.getString("description")%>" value="${param.description }"><br>
			<%=resource.getString("startingPrice")%>:<br>
			<input type="text" name="startingPrice" required="required" class="form-control" placeholder="<%=resource.getString("startingPrice")%>" value="${ param.startingPrice }"><br>
			<%=resource.getString("endingTime")%>:<br>
			<input type="text" value="" id="datetimepicker" name="datetimepicker" required="required" class="form-control" placeholder="<%=resource.getString("endingTime")%>" value="${param.datetimepicker }"/><br>
			<button class="btn btn-lg btn-primary btn-block btn-signin" input type="submit" id="Button"><%=resource.getString("createAuction")%></button>
		</form>
		</div>
	</div>
</body>
<script src="jquery.js"></script>
<script src="jquery.datetimepicker.full.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	$.datetimepicker.setLocale('en');
	
	$("#datetimepicker_format_change").on("click", function(e){
		$("#datetimepicker_format").data('xdsoft_datetimepicker').setOptions({format: $("#datetimepicker_format_value").val()});
	});
	$("#datetimepicker_format_locale").on("change", function(e){
		$.datetimepicker.setLocale($(e.currentTarget).val());
	});
	
	$('#datetimepicker').datetimepicker({
	});

</script>
</html>