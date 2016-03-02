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
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%=resource.getString("auctionIndex")%></title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/heroic-features.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><%=resource.getString("auctionSite")%></a>
				<ul class="nav navbar-nav">
					<jsp:include page="header.jsp"/>
				</ul>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <!-- /.navbar-collapse -->
        </div>
        
        <!-- /.container -->
    </nav>
    <!-- Page Content -->
    <div class="container">

        <!-- Jumbotron Header -->
        <header class="jumbotron hero-spacer">
            <h1><%=resource.getString("plsLoginMessage")%></h1>
            <p> <a class="btn btn-primary btn-lg" href="login.jsp" role="Button"><%=resource.getString("login")%></a>
            	<a class="btn btn-primary btn-lg" href="register.jsp" role=Button"><%=resource.getString("register")%></a>
           </p>
        </header>

        <hr>
           <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p><%=resource.getString("copywrite")%> &copy; Red Leaf Technologies 2015</p>
                </div>
            </div>
        </footer>
  </div>
  
	    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
</body>
</html>