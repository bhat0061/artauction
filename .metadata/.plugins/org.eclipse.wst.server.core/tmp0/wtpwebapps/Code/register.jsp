<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import = "java.util.ResourceBundle"%>
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
<title><%=resource.getString("register")%></title>
</head>
<body>
 <!-- Jumbotron Header -->
			<img alt="auction" src="auction.png" style="display: block; margin-left:auto;margin-right:auto;" />
      
    <div class="container">
        <div class="card card-container">
            <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
            <form class="form-signin" action="RegisterServlet" method="post" id="form">
                <span id="reauth-email" class="reauth-email"></span>
                <%= resource.getString("username")%>: <br>
                <input type="text" id="inputEmail" name="username" class="form-control" placeholder="<%=resource.getString("username")%>" required="required" autofocus value="${param.username}"/> 
                <%= resource.getString("password") %>: <br>    
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="<%=resource.getString("password")%>" required="required">
                <%=resource.getString("confirmPassword")%>: <br>
                <input type="password" name="confirmPassword" class="form-control" required="required" placeholder="<%=resource.getString("confirmPassword")%>" required="required"/>
                <%=resource.getString("fullName")%>: <br>
                <input type="text" name="name" required="required" class="form-control" value="${param.name}" placeholder="<%=resource.getString("fullName")%>" required="required"/>
                <%=resource.getString("email")%>: <br>
                <input type="text" name="email" required="required" placeholder="<%=resource.getString("email")%>" class="form-control" value="${param.email}" required="required"/>
                <%=resource.getString("dateOfBirth")%>: </br>
                <input type="text" name="dateofbirth" id="datetimepicker" value="" class="form-control" required="required" value="${param.dateofbirth }" />
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"><%=resource.getString("register")%></button>
            </form><!-- /form -->
        </div><!-- /card-container -->
    </div><!-- /container -->
</body>
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="jquery.datetimepicker.full.js"></script>

<script>	
	$('#datetimepicker').datetimepicker({
		timepicker:false,
		lang:'en',
		format:'Y/m/d',
		formatDate:'Y/m/d'
	});

</script>
</html>