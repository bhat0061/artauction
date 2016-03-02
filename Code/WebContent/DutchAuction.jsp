<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
	import="java.util.LinkedList"
	import="java.util.Locale"
	import="java.util.Currency"
	import="java.text.NumberFormat"
	import="Data.Bid"
	import="Data.Account"
	import="Data.DutchAuction"
	import="Handler.AuctionHandler"
	import="java.util.ResourceBundle"
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
	DutchAuction auction = (DutchAuction)session.getAttribute("Auction");
//	Bid bid1  = (Bid)session.getAttribute("Bid");
	String lang;
	if(session==null || session.getAttribute("language") == null )
		lang = "Language.lang_en_ca";
	else
		lang = session.getAttribute("language").toString();
	ResourceBundle resource = ResourceBundle.getBundle(lang);
	AuctionHandler handler = new AuctionHandler();
	
%>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Auction Listing</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/heroic-features.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(document).ready(function(){
			function initBid() {
				$('#updateBid').load('UpdateBidServlet?auctid=<%=auction.getId()%>');
				setTimeout(initBid, 5000);	
			}
			initBid();
		});
	</script>
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
                <a class="navbar-brand" href="auctionlist.jsp">Auction Site</a>
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
		<hr>
		<h3 style="display: inline"><%=resource.getString("price")%>: </h3><h3 style="display: inline" id="updateBid"></h3>
		<div id="clockdiv">
		  <div>
		  	<h3>
			    Days Left: <span class="days"> </span><br/>
			    Time Left: <span class="hours"></span> :
			    <span class="minutes"></span> :
			    <span class="seconds"></span>
			</h3>
		  </div>
		</div>

		<%if((((Account)session.getAttribute("account")).isAdmin() && !auction.isApproved())){
			out.println("<a class=\"btn btn-primary\" role=\"Button\" href=\"/ArtAuction/ApproveDutchAuctionServlet\">"+resource.getString("accept")+"</a>");
			out.println("<a class=\"btn btn-primary\" role=\"Button\" href=\"/ArtAuction/RejectDutchAuctionServlet\">"+resource.getString("reject")+"</a>");
		}else if(((Account)session.getAttribute("account")).getId() != auction.getAccountid()){
		%>
		<form action="BuyOutServlet" method="POST">
			<input type="hidden" name="accountid" value='<%=((Account)session.getAttribute("account")).getId()%>'>
			<input type="hidden" name="auctionid" value='<%=auction.getId()%>'>
			<button type="submit" id="buynow" class="btn btn-info btn-lg"><%=resource.getString("buyNow")%></button>
		</form> 
		<%
		Account acc = ((Account)session.getAttribute("account"));
				if(!CreditCardHandler.verifyUserCard(acc.getId())) { %>	
					<script>  
					document.getElementById("buynow").disabled = true; 
					</script> 
					<h3>Please enter a credit card to bid! </h3>
			<% }
		}%>
		 
	</div>
	<!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</body>
<script>
function getTimeRemaining(endtime){
	  var t = Date.parse(endtime) - Date.parse(new Date());
	  var seconds = Math.floor( (t/1000) % 60 );
	  var minutes = Math.floor( (t/1000/60) % 60 );
	  var hours = Math.floor( (t/(1000*60*60)) % 24 );
	  var days = Math.floor( t/(1000*60*60*24) );
	  return {
	    'total': t,
	    'days': days,
	    'hours': hours,
	    'minutes': minutes,
	    'seconds': seconds
	  };
	}

	function initializeClock(id, endtime){
	  var clock = document.getElementById(id);
	  var daysSpan = clock.querySelector('.days');
	  var hoursSpan = clock.querySelector('.hours');
	  var minutesSpan = clock.querySelector('.minutes');
	  var secondsSpan = clock.querySelector('.seconds');

	  function updateClock(){
	    var t = getTimeRemaining(endtime);

	    daysSpan.innerHTML = t.days;
	    hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
	    minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
	    secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);

	    if(t.total<=0){
	      clearInterval(timeinterval);
	    }
	  }

	  updateClock();
	  var timeinterval = setInterval(updateClock,1000);
	}

	var deadline = '<%= session.getAttribute("EndTime") %>';
	initializeClock('clockdiv', deadline);
</script>
</html>
