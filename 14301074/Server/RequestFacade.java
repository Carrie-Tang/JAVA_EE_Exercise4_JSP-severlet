package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestFacade  implements ServletRequest{

	private ServletRequest request = null;
	
	public RequestFacade(ServletRequest request) {
		// 
		this.request = request;
	}
	
	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String arg0) {
		// 
		return request.getAttribute(arg0);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// done
		return request.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		// done
		return request.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		// done
		return request.getContentLength();
	}

	@Override
	public String getContentType() {
		// done
		return request.getContentType();
	}

	@Override
	public DispatcherType getDispatcherType() {
		// done
		return request.getDispatcherType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// done
		return request.getInputStream();
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// done
		return request.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// done
		return request.getLocales();
	}

	@Override
	public String getParameter(String arg0) {
		// done
		return request.getParameter(arg0);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// done
		return request.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		// done
		return request.getAttributeNames();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// done
		return request.getParameterValues(arg0);
	}

	@Override
	public String getProtocol() {
		// done
		return request.getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// done
		return request.getReader();
	}

	@SuppressWarnings("deprecation")
	public String getRealPath(String arg0) {
		// done
		return request.getRealPath(arg0);
	}

	@Override
	public String getRemoteAddr() {
		// done
		return request.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		// done
		return request.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return request.getRequestDispatcher(arg0);
	}

	@Override
	public String getScheme() {
		// done
		return request.getScheme();
	}

	@Override
	public String getServerName() {
		// done
		return request.getServerName();
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return request.getServerPort();
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecure() {
		// done
		return request.isSecure();
	}

	@Override
	public void removeAttribute(String arg0) {
		// done
		request.removeAttribute(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// done
		request.setAttribute(arg0, arg1);
	}

	@Override
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		// done
		request.setCharacterEncoding(arg0);
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
