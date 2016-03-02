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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="Stylesheet.css" rel="stylesheet"/>
<meta http-equiv="Refresh" content="6;url=login.jsp">
</head>
<body>
	<div id="Register">
    	<h2><%=resource.getString("loginAuthFailMsg") %></h2>
    </div>
</body>
</html>