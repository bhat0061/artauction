/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.26
 * Generated at: 2015-11-27 14:28:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.LinkedList;
import Data.EnglishAuction;
import Data.Account;
import java.io.File;
import java.util.ResourceBundle;
import Data.Bid;

public final class AddCreditCard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("Data.Bid");
    _jspx_imports_classes.add("java.util.ResourceBundle");
    _jspx_imports_classes.add("Data.Account");
    _jspx_imports_classes.add("java.util.LinkedList");
    _jspx_imports_classes.add("Data.EnglishAuction");
    _jspx_imports_classes.add("java.io.File");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write(" \t");

		String lang;
		if(session==null || session.getAttribute("language") == null )
			lang = "Language.lang_en_ca";
		else
			lang = session.getAttribute("language").toString();
		ResourceBundle resource = ResourceBundle.getBundle(lang);

      out.write('\r');
      out.write('\n');

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

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("\r\n");
      out.write("    <meta charset=\"utf-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("    <meta name=\"description\" content=\"\">\r\n");
      out.write("    <meta name=\"author\" content=\"\">\r\n");
      out.write("\r\n");
      out.write("    <title>Auction Site</title>\r\n");
      out.write("\r\n");
      out.write("    <!-- Bootstrap Core CSS -->\r\n");
      out.write("    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("    <!-- Custom CSS -->\r\n");
      out.write("    <link href=\"css/heroic-features.css\" rel=\"stylesheet\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<body>\r\n");
      out.write("    <!-- Navigation -->\r\n");
      out.write("    <nav class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">\r\n");
      out.write("        <div class=\"container\">\r\n");
      out.write("            <!-- Brand and toggle get grouped for better mobile display -->\r\n");
      out.write("            <div class=\"navbar-header\">\r\n");
      out.write("                <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">\r\n");
      out.write("                    <span class=\"sr-only\">Toggle navigation</span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                    <span class=\"icon-bar\"></span>\r\n");
      out.write("                </button>\r\n");
      out.write("                <a class=\"navbar-brand\" href=\"auctionlist.jsp\">");
      out.print(resource.getString("auctionSite"));
      out.write("</a>\r\n");
      out.write("\t\t\t\t<ul class=\"nav navbar-nav\">\r\n");
      out.write("\t\t\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("            </div>\r\n");
      out.write("            <!-- Collect the nav links, forms, and other content for toggling -->\r\n");
      out.write("            <!-- /.navbar-collapse -->\r\n");
      out.write("        </div>\r\n");
      out.write("        \r\n");
      out.write("        <!-- /.container -->\r\n");
      out.write("    </nav>\r\n");
      out.write("   <div class=\"container\">\r\n");
      out.write("  <form class=\"form-horizontal\" action=\"CreditCardServlet\" method=\"post\">\r\n");
      out.write("    <fieldset>\r\n");
      out.write("      <legend>");
      out.print(resource.getString("payment"));
      out.write("</legend>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("       <div style=\"color: #FF0000;\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${errorMessage}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</div>\r\n");
      out.write("        <label class=\"col-sm-3 control-label\" for=\"card-holder-name\">");
      out.print(resource.getString("nameoncard"));
      out.write("</label>\r\n");
      out.write("        <div class=\"col-sm-9\">\r\n");
      out.write("          <input type=\"text\" class=\"form-control\" name=\"card-holder-name\" id=\"card-holder-name\"  required=\"required\" placeholder=");
      out.print(resource.getString("cardholder"));
      out.write(">\r\n");
      out.write("        </div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("        <label class=\"col-sm-3 control-label\" for=\"card-number\">");
      out.print(resource.getString("cardnumber"));
      out.write("</label>\r\n");
      out.write("        <div class=\"col-sm-9\">\r\n");
      out.write("          <input type=\"text\" class=\"form-control\" name=\"card-number\" id=\"card-number\"  required=\"required\" placeholder=");
      out.print(resource.getString("cardcdnumber"));
      out.write(">\r\n");
      out.write("        </div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("        <label class=\"col-sm-3 control-label\" for=\"expiry-month\" >");
      out.print(resource.getString("expirationdate"));
      out.write("</label>\r\n");
      out.write("        <div class=\"col-sm-9\">\r\n");
      out.write("          <div class=\"row\">\r\n");
      out.write("            <div class=\"col-xs-3\">\r\n");
      out.write("              <select class=\"form-control col-sm-2\" name=\"expiry-month\"  required=\"required\" id=\"expiry-month\">\r\n");
      out.write("                <option value=\"01\">01</option>\r\n");
      out.write("                <option value=\"02\">02</option>\r\n");
      out.write("                <option value=\"03\">03</option>\r\n");
      out.write("                <option value=\"04\">04</option>\r\n");
      out.write("                <option value=\"05\">05</option>\r\n");
      out.write("                <option value=\"06\">06</option>\r\n");
      out.write("                <option value=\"07\">07</option>\r\n");
      out.write("                <option value=\"08\">08</option>\r\n");
      out.write("                <option value=\"09\">09</option>\r\n");
      out.write("                <option value=\"10\">10</option>\r\n");
      out.write("                <option value=\"11\">11</option>\r\n");
      out.write("                <option value=\"12\">12</option>\r\n");
      out.write("              </select>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"col-xs-3\">\r\n");
      out.write("              <select class=\"form-control\" name=\"expiry-year\"  required=\"required\" >\r\n");
      out.write("                <option value=\"2015\">2015</option>\r\n");
      out.write("                <option value=\"2016\">2016</option>\r\n");
      out.write("                <option value=\"2017\">2017</option>\r\n");
      out.write("                <option value=\"2018\">2018</option>\r\n");
      out.write("                <option value=\"2019\">2019</option>\r\n");
      out.write("                <option value=\"2020\">2020</option>\r\n");
      out.write("                <option value=\"2021\">2021</option>\r\n");
      out.write("                <option value=\"2022\">2022</option>\r\n");
      out.write("                <option value=\"2023\">2023</option>\r\n");
      out.write("                <option value=\"2024\">2024</option>\r\n");
      out.write("              </select>\r\n");
      out.write("            </div>\r\n");
      out.write("          </div>\r\n");
      out.write("        </div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("        <label class=\"col-sm-3 control-label\" for=\"cvv\">");
      out.print(resource.getString("cardccv"));
      out.write("</label>\r\n");
      out.write("        <div class=\"col-sm-3\">\r\n");
      out.write("          <input type=\"text\" class=\"form-control\" name=\"cvv\" id=\"cvv\" placeholder=");
      out.print(resource.getString("securitycode"));
      out.write("  required=\"required\">\r\n");
      out.write("        </div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("      \t<label class=\"col-sm-3 control-label\" for=\"address\">");
      out.print(resource.getString("address") );
      out.write("</label>\r\n");
      out.write("      \t<div class=\"col-sm-3\">\r\n");
      out.write("      \t\t<input type=\"text\" class=\"form-control\" name=\"address\" id=\"address\" placeholder=");
      out.print(resource.getString("address") );
      out.write(" required=\"required\">\r\n");
      out.write("      \t</div>\r\n");
      out.write("     </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("      \t<label class=\"col-sm-3 control-label\" for=\"city\">");
      out.print(resource.getString("city") );
      out.write("</label>\r\n");
      out.write("      \t<div class=\"col-sm-3\">\r\n");
      out.write("      \t\t<input type=\"text\" class=\"form-control\" name=\"city\" id=\"city\" placeholder=");
      out.print(resource.getString("city") );
      out.write(" required=\"required\">\r\n");
      out.write("      \t</div>\r\n");
      out.write("     </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("      \t<label class=\"col-sm-3 control-label\" for=\"province\">");
      out.print(resource.getString("province") );
      out.write("</label>\r\n");
      out.write("      \t<div class=\"col-sm-3\">\r\n");
      out.write("      \t\t<input type=\"text\" class=\"form-control\" name=\"province\" id=\"province\" placeholder=");
      out.print(resource.getString("province") );
      out.write(" required=\"required\">\r\n");
      out.write("      \t</div>\r\n");
      out.write("     </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("      \t<label class=\"col-sm-3 control-label\" for=\"postalcode\">");
      out.print(resource.getString("postalcode") );
      out.write("</label>\r\n");
      out.write("      \t<div class=\"col-sm-3\">\r\n");
      out.write("      \t\t<input type=\"text\" class=\"form-control\" name=\"postalcode\" id=\"postalcode\" placeholder=");
      out.print(resource.getString("postalcode") );
      out.write(" required=\"required\">\r\n");
      out.write("      \t</div>\r\n");
      out.write("     </div>\r\n");
      out.write("      <div class=\"form-group\">\r\n");
      out.write("      \t<div class=\"col-sm-offset-3 col-sm-9\">\r\n");
      out.write("      \t<button class=\"btn btn-success \" type=\"submit\">");
      out.print(resource.getString("addcreditcard"));
      out.write("</button>\r\n");
      out.write("      \t</div>\r\n");
      out.write("      </div>\r\n");
      out.write("  \t\r\n");
      out.write("  \t   </fieldset>\r\n");
      out.write("  \t  \r\n");
      out.write("  </form><!-- /form -->\r\n");
      out.write("        <hr>\r\n");
      out.write("\r\n");
      out.write("        <!-- Footer -->\r\n");
      out.write("        <footer>\r\n");
      out.write("            <div class=\"row\">\r\n");
      out.write("                <div class=\"col-lg-12\">\r\n");
      out.write("                    <p>Copyright &copy; Red Leaf Technologies 2015</p>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </footer>\r\n");
      out.write("        </div>\r\n");
      out.write("  \r\n");
      out.write("\t    <!-- jQuery -->\r\n");
      out.write("    <script src=\"js/jquery.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    <!-- Bootstrap Core JavaScript -->\r\n");
      out.write("    <script src=\"js/bootstrap.min.js\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}