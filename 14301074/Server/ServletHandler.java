package Server;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletHandler {
	
	public void handle(Request request,  Response response) {
		String uri = request.getURI();
		String servletName = uri.substring(uri.lastIndexOf('/') + 1);
		//log
		System.out.println("log load servlet name: " + servletName + "\n");
		
		//a class loader
		URLClassLoader urlClassLoader = null;
		try{
			URLStreamHandler urlStreamHandler = null;
			urlClassLoader = new URLClassLoader(new URL[]{
					new URL(null, "file:" + ServerCons.WEB_SERVER_ROOT, urlStreamHandler)
			});
			
			//logs
			//System.out.println("log : " + ServerCons.WEB_SERVER_ROOT + "\n");
		}catch (IOException e) {
			// DONE
			System.out.println(e.toString());
		}
		
		//load the servlet obj
		Class<?> unknownClass = null;
		try{
			unknownClass = urlClassLoader.loadClass("Servlet." + servletName);
		}catch (ClassNotFoundException e) {
			// print exp obj info
			System.out.println("line 36 in servlet handler \r\n" + e.toString());
		}
		
		//run the servlet
		Servlet servlet = null;
		RequestFacade requestFacade = new RequestFacade(request);
		ResponseFacade responseFacade = new ResponseFacade(response);
		try{
			servlet = (Servlet)unknownClass.newInstance();
			servlet.service((ServletRequest)requestFacade, (ServletResponse)responseFacade);
		}catch (Exception e) {
			// print exp obj info
			System.out.println("line 48 in servlet handler \r\n" +e.toString());
		}
	}
}
