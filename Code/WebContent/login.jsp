<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import = "java.util.ResourceBundle" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
<link type="text/css" href="css/Custom.css" rel="stylesheet"/>
<%
 		if(session != null && session.getAttribute("account") != null){
		response.sendRedirect("auctionlist.jsp");		
	}
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
%>


<title>Login Application</title>
</head>
<body>
   <!-- Jumbotron Header -->
     
			<img alt="auction" src="auction.png" style="display: block; margin-left:auto; margin-right:auto;" />
      
    <div class="container">
        <div class="card card-container">
            <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
            <form class="form-signin" action="LoginServlet" method="post">
                <span id="reauth-email" class="reauth-email"></span>
                <input type="text" id="inputEmail" name="username" class="form-control" placeholder="<%=resource.getString("username")%>" required="required" autofocus>
                <input type="password" id="inputPassword" name="userpass" class="form-control" placeholder="<%=resource.getString("password")%>" required="required">
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"><%=resource.getString("signin")%></button>
            </form><!-- /form -->
        </div><!-- /card-container -->
    </div><!-- /container -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>