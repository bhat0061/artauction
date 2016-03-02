<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" 
	import="java.util.LinkedList"
	import="Data.EnglishAuction"	
	import="Data.Account"
	import="java.io.File"
	import = "java.util.ResourceBundle"
	import="Data.Bid"
	
 	%>
 	<%
		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);
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
	
	LinkedList<EnglishAuction>  auctions = (LinkedList<EnglishAuction>)session.getAttribute("auctionList");
	int count = 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Auction Site</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/heroic-features.css" rel="stylesheet">
</head>
<body>
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
                <a class="navbar-brand" href="auctionlist.jsp"><%=resource.getString("auctionSite")%></a>
				<ul class="nav navbar-nav">
					<jsp:include page="header.jsp"/>
				</ul>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <!-- /.navbar-collapse -->
        </div>
        
        <!-- /.container -->
    </nav>
   <div class="container">
  <form class="form-horizontal" action="CreditCardServlet" method="post">
    <fieldset>
      <legend><%=resource.getString("payment")%></legend>
      <div class="form-group">
       <div style="color: #FF0000;">${errorMessage}</div>
        <label class="col-sm-3 control-label" for="card-holder-name"><%=resource.getString("nameoncard")%></label>
        <div class="col-sm-9">
          <input type="text" class="form-control" name="card-holder-name" id="card-holder-name"  required="required" placeholder=<%=resource.getString("cardholder")%>>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="card-number"><%=resource.getString("cardnumber")%></label>
        <div class="col-sm-9">
          <input type="text" class="form-control" name="card-number" id="card-number"  required="required" placeholder=<%=resource.getString("cardcdnumber")%>>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="expiry-month" ><%=resource.getString("expirationdate")%></label>
        <div class="col-sm-9">
          <div class="row">
            <div class="col-xs-3">
              <select class="form-control col-sm-2" name="expiry-month"  required="required" id="expiry-month">
                <option value="01">01</option>
                <option value="02">02</option>
                <option value="03">03</option>
                <option value="04">04</option>
                <option value="05">05</option>
                <option value="06">06</option>
                <option value="07">07</option>
                <option value="08">08</option>
                <option value="09">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
              </select>
            </div>
            <div class="col-xs-3">
              <select class="form-control" name="expiry-year"  required="required" >
                <option value="2015">2015</option>
                <option value="2016">2016</option>
                <option value="2017">2017</option>
                <option value="2018">2018</option>
                <option value="2019">2019</option>
                <option value="2020">2020</option>
                <option value="2021">2021</option>
                <option value="2022">2022</option>
                <option value="2023">2023</option>
                <option value="2024">2024</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="cvv"><%=resource.getString("cardccv")%></label>
        <div class="col-sm-3">
          <input type="text" class="form-control" name="cvv" id="cvv" placeholder=<%=resource.getString("securitycode")%>  required="required">
        </div>
      </div>
      <div class="form-group">
      	<label class="col-sm-3 control-label" for="address"><%=resource.getString("address") %></label>
      	<div class="col-sm-3">
      		<input type="text" class="form-control" name="address" id="address" placeholder=<%=resource.getString("address") %> required="required">
      	</div>
     </div>
      <div class="form-group">
      	<label class="col-sm-3 control-label" for="city"><%=resource.getString("city") %></label>
      	<div class="col-sm-3">
      		<input type="text" class="form-control" name="city" id="city" placeholder=<%=resource.getString("city") %> required="required">
      	</div>
     </div>
      <div class="form-group">
      	<label class="col-sm-3 control-label" for="province"><%=resource.getString("province") %></label>
      	<div class="col-sm-3">
      		<input type="text" class="form-control" name="province" id="province" placeholder=<%=resource.getString("province") %> required="required">
      	</div>
     </div>
      <div class="form-group">
      	<label class="col-sm-3 control-label" for="postalcode"><%=resource.getString("postalcode") %></label>
      	<div class="col-sm-3">
      		<input type="text" class="form-control" name="postalcode" id="postalcode" placeholder=<%=resource.getString("postalcode") %> required="required">
      	</div>
     </div>
      <div class="form-group">
      	<div class="col-sm-offset-3 col-sm-9">
      	<button class="btn btn-success " type="submit"><%=resource.getString("addcreditcard")%></button>
      	</div>
      </div>
  	
  	   </fieldset>
  	  
  </form><!-- /form -->
        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Red Leaf Technologies 2015</p>
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