package Server;

import java.io.File;

public class ServerCons {
	//
	public static final String WEB_ROOT = System.getProperty("user.dir") + 
			File.separator + "webroot";
	//
	public static final String WEB_SERVER_ROOT =  System.getProperty("user.dir") + 
			//File.separator + "bin";
			File.separator + "target" + File.separator + "classes";
	
	//
	public static final String JSP_CLASS_ROOT = System.getProperty("user.dir") + 
			File.separator + "bin";
	//
	public static final String JSP_JAVA_ROOT = System.getProperty("user.dir") + 
			  File.separator + "src" + File.separator + "Servlet";
	
}
