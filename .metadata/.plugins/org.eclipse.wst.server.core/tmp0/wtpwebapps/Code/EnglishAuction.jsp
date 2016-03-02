<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
	import="java.util.LinkedList"
	import="java.util.Locale"
	import="java.util.Currency"
	import="java.text.NumberFormat"
	import="Data.Bid"
	import="Data.Account"
	import="Data.EnglishAuction"
	import = "java.util.ResourceBundle"
	import="Handler.CreditCardHandler"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	//Check client session
	request.getSession(false);
	if (session == null || session.getAttribute("account") == null){
		session.setAttribute("redirectLink", "index.jsp");
		session.setAttribute("redirectMsg", "You are currently not signed in, please sign in before viewing this page. Redirecting you home shortly.");
		response.sendRedirect("redirect.jsp");		
	}
	 
	@SuppressWarnings("unchecked")
	LinkedList<Bid> allBids = (LinkedList<Bid>)session.getAttribute("AllBids");
	EnglishAuction auction = (EnglishAuction)session.getAttribute("Auction");
//	Bid bid1  = (Bid)session.getAttribute("Bid");
	String lang;
	if(session==null || session.getAttribute("language") == null )
		lang = "Language.lang_en_ca";
	else
		lang = session.getAttribute("language").toString();
	ResourceBundle resource = ResourceBundle.getBundle(lang);
	Locale curLocale;
	if(lang == "Language.lang_en_ca") curLocale = Locale.CANADA;
	else curLocale = Locale.CANADA_FRENCH;
	NumberFormat format = NumberFormat.getCurrencyInstance(curLocale);
%>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%=resource.getString("auctionListing")%></title>

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
	    <div class="row">
	        <div class="col-lg-6 col-md-offset-4">
	        	<h2><%=auction.getTitle()%></h2>
	        </div>
	   </div>
	   <hr>
		<div id="ImageAuction" style="width:400px;height:300px;content-align:center;margin-left:auto;margin-right:auto;">
	   		<img src="/ArtAuction/Image?id=<%=auction.getImageid()%>" width="400px" height="300px">
		</div>
		<hr>
		<h3><span class="text-center"><%=auction.getDescription()%></span></h3>
		<br/>
		<div style="color: #FF0000;">${errorMessage}</div>
		<%if(((Account)session.getAttribute("account")).isAdmin() && !auction.isApproved()){
			out.println("<a class=\"btn btn-primary\" role=\"Button\" href=\"/ArtAuction/ApproveEnglishAuctionServlet\">"+resource.getString("accept")+"</a>");
			out.println("<a class=\"btn btn-primary\" role=\"Button\" href=\"/ArtAuction/RejectEnglishAuctionServlet\">"+resource.getString("reject")+"</a>");
		}else{
		
		%>
		<table class="table table-striped">
			<tr>
				<th> <%=resource.getString("bidder")%> </th>
				<th> <%=resource.getString("bidPrice")%> </th>
				<th> <%=resource.getString("bidDateTime")%> </th>
			<tr>
			<%
				if(allBids != null){
					for(Bid bid : allBids){			
						out.println("<tr>");
						out.print("<td>" + bid.encryptBidUsername(bid.getUserName()) + "</td>" +
							"<td>"+ format.format(bid.getValue()) +"</td><td>" + bid.getCreatedTimeStamp() + "</td></tr>");	
					}
				}
			%>
			
		</table>
		<%
        if(((Account)session.getAttribute("account")).getId() != auction.getAccountid()){%>
            <button type="button" id="bidButton" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"><%=resource.getString("placeYourBid")%></button>
        <%} %>
			<% 
					Account acc = ((Account)session.getAttribute("account"));
				if(!CreditCardHandler.verifyUserCard(acc.getId())) { %>	
					<script>  
					document.getElementById("bidButton").disabled = true; 
					</script> 
					<h3><%=resource.getString("entercard") %></h3>
				 <% }
				}%>
			<div class="modal fade" id="myModal" role="dialog">
    			<div class="modal-dialog">
      				<div class="modal-content">
        				<div class="modal-header">
          					<button type="button" class="close" data-dismiss="modal">&times;</button>
          					<h4 class="modal-title"><%=resource.getString("placeYourBid")%></h4>
        				</div>
        				<div class="modal-body" style="text-align:center;">
        					<form class="form-inline" action="BidServlet" method="post" id="form">
        						<div class="form-group">
        							<label class="sr-only" for="exampleInputAmount"><%=resource.getString("amountDollars")%></label>
    								<div class="input-group">
      									<div class="input-group-addon">$</div>
      									<input type="text" class="form-control" name="bidPrice" required="required"
      										value="${ param.bidPrice }" placeholder="Amount"/>					
        							</div>
        						</div>
        						<button type="submit" class="btn btn-primary"><%=resource.getString("placeYourBid")%></button>
        					</form>		
        				</div>
        				<div class="modal-footer">
          					<button type="button" class="btn btn-default" data-dismiss="modal"><%=resource.getString("close")%></button>
        				</div>
      				</div>
    			</div>
  		</div>  
	</div>
	<!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>

