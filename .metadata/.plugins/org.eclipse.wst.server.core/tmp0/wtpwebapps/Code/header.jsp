<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="Data.Account"
    import="Handler.CreditCardHandler"
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


<%if (session != null && session.getAttribute("account") != null){ 
	out.println("<li><a href=\"/ArtAuction/LogoutServlet\">"+resource.getString("logout")+"</a></li>");
	
}
else {
	out.println("<li><a href=\"login.jsp\">"+resource.getString("login")+"</a></li><li><a href=\"register.jsp\">"+resource.getString("register")+"</a></li>");
}%>

<li><select id="languageSelect" onchange="changeLang()" class="form-control">
  <option value="dummy">Select Language</option>
  <option value="english">English</option>
  <option value="french">French</option>
</select></li>


<%
	if(session != null && ((Account)session.getAttribute("account") != null)) {
	Account acc = (Account)session.getAttribute("account");
	if(acc.isAdmin()){
		return;
	}
	boolean status = CreditCardHandler.verifyUserCard(acc.getId());
	if(!status){
		out.println("<li><a href=\"AddCreditCard.jsp\">" + resource.getString("addCreditCard") + "</a></li>");
	}
}
%>

<script>
	function changeLang() {
	    var langSelected = document.getElementById("languageSelect").value;
	    var currentPage = window.location.href;
	    window.location = "/ArtAuction/LanguageServlet?language=" + langSelected + "&url=" + currentPage;
	}
</script>
