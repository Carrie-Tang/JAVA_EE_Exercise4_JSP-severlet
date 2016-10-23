package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
//import javax.xml.ws.Response;

public class ResponseFacade implements ServletResponse{

	private ServletResponse response = null;
	
	public ResponseFacade(ServletResponse response) {
		// 
		this.response = response;
	}
	@Override
	public void flushBuffer() throws IOException {
		// done
		response.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		// done
		return response.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		// done
		return response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		// done
		return response.getContentType();
	}

	@Override
	public Locale getLocale() {
		// done
		return response.getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// done
		return response.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// done
		return response.getWriter();
	}

	@Override
	public boolean isCommitted() {
		// done
		return response.isCommitted();
	}

	@Override
	public void reset() {
		// done
		response.reset();
	}

	@Override
	public void resetBuffer() {
		// done
		response.resetBuffer();
	}

	@Override
	public void setBufferSize(int arg0) {
		// done
		response.setBufferSize(arg0);
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// done
		response.setCharacterEncoding(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		// done
		response.setContentLength(arg0);
	}

	@Override
	public void setContentType(String arg0) {
		// done
		response.setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		// done
		response.setLocale(arg0);
	}

}
