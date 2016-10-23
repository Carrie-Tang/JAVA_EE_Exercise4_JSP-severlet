package MyJSP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import Server.Request;
import Server.Response;
import Server.ServerCons;
import Server.ServletHandler;

public class JSPHandler {
	
	public void handle(Request request, Response response){
		String jname = JSPHelper.getJsp_ServletName(request.getURI());
		ServletHandler servletHandler = new ServletHandler();
		//
		if(jname == null){
			File fp = new File(ServerCons.WEB_ROOT, request.getURI());
			//logs
			System.out.println("logs jsp file path:" + fp.getAbsolutePath() + "\n");
			//
			if(fp.exists()){
				BufferedReader bufferedReader = null;
				StringBuffer sb = new StringBuffer();
				String jsp_content = null;
				jname = request.getURI().substring(
						request.getURI().lastIndexOf('/') + 1, 
						request.getURI().lastIndexOf('.')) + "_jsp";
				try{			
					bufferedReader = new BufferedReader(new FileReader(fp));
					String str;
					while((str = bufferedReader.readLine()) != null){
						sb.append(str + "\n");
					}
					jsp_content = sb.toString();
					//
					bufferedReader.close();
				}catch (Exception e) {
					// DONE
					e.printStackTrace();
				}
				//
				fp = new File(ServerCons.JSP_JAVA_ROOT, jname + ".java");
				//create servlet java file for the jsp
				try{
					fp.createNewFile();
					FileOutputStream fos = new FileOutputStream(fp);
					fos.write(JSPHelper.jspToServlet(jsp_content, jname, response).getBytes());
					fos.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return;
				}
				
				//compile
				int status = JSPHelper.compile(ServerCons.JSP_CLASS_ROOT, 
						ServerCons.JSP_JAVA_ROOT + File.separator + jname + ".java");
				if(status != 0){
					//logs
					System.out.println("log : " + jname + ".java compiled faile!\n");
				}else{
					//handle servlet
					request.setURI("/" + jname);
					//LOGS
					System.out.println("log reset uri: " + request.getURI() + "\n");
					
					servletHandler.handle(request, response);
				}
			}
			else{
				//jsp file not exits
				try{
					response.sendStaticRes();
				}catch (Exception e) {
					//logs
					e.printStackTrace();
				}
			}
			
		}else{
			//logs
			System.out.println("logs: \njsp name: " + jname + "\n");
			
			request.setURI("/" + jname);
			servletHandler.handle(request, response);
		}
	}
	
}
