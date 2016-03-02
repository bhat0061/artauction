<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" 
	import="java.util.LinkedList"
	import="Data.EnglishAuction"
	import="Data.DutchAuction"	
	import="Data.Account"
	import="java.io.File"
	import="Data.Bid"
	import = "java.util.ResourceBundle"
 	%>
<%
	//Check client session
	session = request.getSession();
	session.setAttribute("adminView", false);
	if (session == null || session.getAttribute("account") == null){
		session.setAttribute("redirectLink", "index.jsp");
		session.setAttribute("redirectMsg", "You are currently not signed in, please sign in before viewing this page. Redirecting you home shortly.");
		response.sendRedirect("redirect.jsp");		
	}
	
	LinkedList<EnglishAuction> englishAuctions = (LinkedList<EnglishAuction>)session.getAttribute("unapprovedEnglishAuctionList");
	LinkedList<DutchAuction> dutchAuctions = (LinkedList<DutchAuction>)session.getAttribute("unapprovedDutchAuctionList");
	int count = 0;
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

    <title><%=resource.getString("auctionSite")%></title>

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
    <div id="userWelcome">
    	<h2><%=resource.getString("hello") %>, <small><%= ((Account)session.getAttribute("account")).getUsername()%></small></h2><br/>
    	<%
			if(((Account)session.getAttribute("account")).isAdmin()){
				out.println("<a class=\"btn btn-primary\" href=\"/ArtAuction/AuctionListServlet\" role=\"Button\">"+resource.getString("regularView")+"</a>");

    		}
    	%>
    </div>
    
    <!-- Page Content -->
    <div class="container">

        <!-- Jumbotron Header -->
        <header class="jumbotron hero-spacer">
            <h1><%=resource.getString("auctionMessage")%></h1>
			<img alt="auction" src="auction.png" style="display: block; margin-left:auto; margin-right:auto;" />
        </header>

        <hr>
         <div class="row">
            <div class="col-lg-12">
                <h3><%=resource.getString("listedAuctions")%></h3>
            </div>
        </div>
        <!-- /.row -->

        <!-- Page Features -->
        <!-- Nav tabs -->
        <div>
	  		<ul class="nav nav-tabs" role="tablist">
	    		<li role="presentation" class="active"><a href="#english" aria-controls="englsih" role="tab" data-toggle="tab"><%=resource.getString("englishAuction")%></a></li>
	    		<li role="presentation"><a href="#dutch" aria-controls="dutch" role="tab" data-toggle="tab"><%=resource.getString("dutchAuction")%></a></li>
	  		</ul>
	  		<!-- Tab panes -->
	  		<div class="tab-content">
	    		<div role="tabpanel" class="tab-pane active" id="english">
	    			<div class="row text-center">
        	<jsp:include page="AuctionInit.jsp"></jsp:include>
        	<%
        		if (englishAuctions != null) {
        	        		for(EnglishAuction auction : englishAuctions) {
        	        			out.println("<div class=\"col-md-3 col-sm-6 hero-feature\"><div class=\"thumbnail\"> <img src=\"/ArtAuction/Image?id=" + auction.getImageid() + "\" style=\"width:250px;height:250px;\"><div class=\"caption\"><h3>" + auction.getTitle() + "</h3><p>" + auction.getDescription() + "<br>Starting Price: " + String.format(" %.2f",auction.getStartingPrice()) +"</p><p><a href=\"/ArtAuction/EnglishAuctionServlet?auctid=" + auction.getId()+ "\" class=\"btn btn-primary\">View Auction</a></p> </div> </div></div>");
        	        		}
        	        	}
        	%>
        </div>

	   	</div>
	    		<div role="tabpanel" class="tab-pane fade" id="dutch">
	    			<div class="row text-center">
        			<jsp:include page="AuctionInitDutch.jsp"></jsp:include>
        			<% 
        				if (dutchAuctions != null) {
        					for(DutchAuction auction : dutchAuctions) {
        						out.println("<div class=\"col-md-3 col-sm-6 hero-feature\"><div class=\"thumbnail\" style=\"height:550px;\"> <img src=\"/ArtAuction/Image?id=" + auction.getImageid() + "\" style=\"width:250px;height:250px;\"><div class=\"caption\"><h3>" + auction.getTitle() + "</h3><p>" + auction.getDescription() + "<br>" + resource.getString("startingPrice") + String.format(" %.2f",auction.getInitialCost()) + "</p><p><a href=\"/ArtAuction/DutchAuctionServlet?auctid=" + auction.getId()+ "\" class=\"btn btn-primary\">"+resource.getString("viewAuction")+"</a></p> </div> </div></div>");
        					}
        				}
        			%>
        		</div>
	    	</div>
	  	</div>
  		</div>
          
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