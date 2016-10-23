package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.net.URI;
import java.util.Date;

import MyJSP.JSPHandler;
import MyJSP.JSPHelper;

public class HttpServer {
	
	int port = 8080;
	private static final String serverName = "127.0.0.1";
	private static final String SERVER_CLOSE_CMD = "/CLOSE";
	
	public static void main(String[] args) {
		// new a server and run it
		HttpServer httpServer = new HttpServer();
		//print time
		System.out.println(httpServer.getClass().getName()+ 
						" is running begin at " +  new Date());
		httpServer.await();
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(serverName));
		}
		catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.exit(-1);
		}
		
		//wait for requests
		while(true){
			Socket socket = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			//
			try{
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				
				//new request obj and parse the request string
				Request request = new Request(inputStream);
				request.parse();
				//check whether if it's a shutdown cmd
				if(request.getURI().equals(SERVER_CLOSE_CMD)){
					System.out.println("Receive a close command! I'm Closing, bye!");
					break;
				}
					
				//new a response obj and the request obj for it
				Response response = new Response(outputStream);
				response.setRequest(request);
				
				//handle servlet request or static res request
				if(request.getURI().startsWith("/Servlet/")){
					//handle servlet request
					ServletHandler servletHandler = new ServletHandler();
					servletHandler.handle(request, response);
				}else if(request.getURI().endsWith(".jsp")){
					//logs
					System.out.println("log : enter jsp handling part\n");
					//handle with jsp
					JSPHandler jspHandler = new JSPHandler();
					jspHandler.handle(request, response);
				}else{
					//handle static res request
					StaticResHandler staticResHandler = new StaticResHandler();
					staticResHandler.handle(request, response);
				}
				//close the socket
				socket.close();
			}catch (Exception e) {
				// done
				e.printStackTrace();
				System.exit(-2);
			}
			
		}
	}
}
