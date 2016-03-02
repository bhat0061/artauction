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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
 
<title><%=resource.getString("placeYourBid")%></title>
</head>
<body>
<form action="BidServlet" method="post" id="form">
		<h1><%=resource.getString("bid")%></h1>
        <fieldset style="width: 300px">
 			<%=resource.getString("biddingPrice")%>
 			<br>
 			<input type="text" name="bidPrice" required="required" value = "${param.bidPrice}"  /><br>
			 
		</fieldset>
		<input type="submit" id="Button" value="Bid"   />
		 
    </form>
</body>
</html>