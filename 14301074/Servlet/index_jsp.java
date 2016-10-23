package Servlet;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
//begin dynamic import
import java.text.*;
import java.util.*;
//<END-JSP-IMPORT>

public class index_jsp implements Servlet{
	public void init(ServletConfig config) throws ServletException { }

	public void service(ServletRequest request, ServletResponse response)
		throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		
		out.print("<html>           <head>           <title>Show time</title>           </head>           <body>                 Hello :");
       
             SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");       
             String str = format.format(new Date());       
          
		
		out.print(str );
		out.print("           </body>           </html>   		<!--  localhost:8080/Servlet/PrimitiveServlet-->");
		out.flush();
}

	public void destroy() { }

	public String getServletInfo() {
	return null; 
}

	public ServletConfig getServletConfig() {
	return null;
}

}