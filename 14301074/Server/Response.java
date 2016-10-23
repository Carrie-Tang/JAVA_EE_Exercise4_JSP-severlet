package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class Response implements ServletResponse{
	
	OutputStream outputStream =null;
	Request request = null;
	PrintWriter pw = null;
	private static final int BUFFER_SIZE = 1024;
	
	
	public Response(OutputStream outputStream) {
		// 
		this.outputStream = outputStream;
		//
		
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticRes() throws IOException{
		byte buffer[] = new byte[BUFFER_SIZE];
		FileInputStream fileInputStream = null;
		//
		try{
			File file = new File(ServerCons.WEB_ROOT, request.getURI());
			//logs
			//System.out.println("log static file path: " + file.getAbsolutePath() + "\n");
			//
			fileInputStream = new FileInputStream(file);
			
			//read the file and write out
			int i = fileInputStream.read(buffer, 0, BUFFER_SIZE);
			while(i != -1){
				outputStream.write(buffer, 0, i);
				i = fileInputStream.read(buffer, 0, BUFFER_SIZE);
			}
		}catch (IOException e) {
			// return a 404 info
			String retMsg = "HTTP/1.1 404 File Not Found\r\n" +
					"Content-Type: text/html\r\n" +
					"Content-Length: 50\r\n\r\n" +
					"<h1>File Not Found</h1>";
			//
			outputStream.write(retMsg.getBytes());
		}
		finally{
			if(fileInputStream != null)
				fileInputStream.close();
		}
		
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// done
		pw = new PrintWriter(outputStream, true);
		return pw;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub
		//setCharacterEncoding(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub
		//setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}

}
