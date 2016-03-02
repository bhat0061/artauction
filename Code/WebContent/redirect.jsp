<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="Data.Account"
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
<link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
<link type="text/css" href="css/Custom.css" rel="stylesheet"/>
<meta http-equiv="Refresh" content="2;url=<%=session.getAttribute("redirectLink")%>">
<title><%=resource.getString("welcome")%> <%=((Account)session.getAttribute("account"))%></title>
</head>
<body>
<div class="container style="w">
     <div class="card" >
    	<h3>
        	<%=resource.getString("hello")%>,
        	<%=session.getAttribute("redirectMsg")%></h3>
        	</div>
    </div><!-- /container -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
</body>
</html>