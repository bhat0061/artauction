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
        
        if(!((Account)session.getAttribute("account")).isAdmin()){
        	out.println(
        	"<div class=\"col-md-3 col-sm-6 hero-feature\">" +
        		"<div class=\"thumbnail\">"+
	        		"<a class=\"btn btn-default\" aria-label=\"Left Align\" style=\"width:250px;height:247px;background-color:#5cb85c;\" href=\"createDutchAuction.jsp\"><br><br><br><br><br>"+resource.getString("createAuction")+"</a>" + 
				"</div>" +
			"</div>");
        }
        %>