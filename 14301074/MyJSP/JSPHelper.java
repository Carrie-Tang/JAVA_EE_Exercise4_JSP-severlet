package MyJSP;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import Server.Response;
import Server.ServerCons;

public class JSPHelper {
	/*delimiter */
	public static final String END_IMPORT_DELIMIER = "//<END-JSP-IMPORT>";
	
	//pre uri begins with "/" and ends with ".jsp"
	//return a coresponding jsp class name if exists
	//else return null
	public static String getJsp_ServletName(String uri) {
		String jname = uri.substring(uri.lastIndexOf('/') + 1, uri.lastIndexOf('.')) + "_jsp";
		String fname = jname + ".java";
		File fp = new File(ServerCons.JSP_JAVA_ROOT, fname);
		if(fp.exists()){
			return jname;
		}
		return null;
	}
	
	//refers http://www.2cto.com/kf/201206/136441.html
	//
	public static int  compile(String des, String src) {
		//dynamic complie
		String[] commadline = {"-d",des,src};
		int status;
		//logs
		System.out.println("complie-log:\n" + "des:" + des + "\nsrc: " + src + "\n");
		
		try{
			status = com.sun.tools.javac.Main.compile(commadline);
		}catch (Exception e) {
			// done
			e.printStackTrace();
			return -1;
		}
	     
		return status;
	}
	
	//jsp -> servlet
	//support <% %>, <%-- --%>, <%! %>, <%= %>
	//not support <%@ %> currently!
	//
	//return null jsp format is wrong
	//return a servlet java string if success
	public static String jspToServlet(String jsp, String servName, Response response) {
		//
		StringBuffer sb = new StringBuffer();
		//front
		sb.append("package Servlet;\n");
		sb.append("import javax.servlet.*;\n" +
				"import java.io.IOException;\n" +
				"import java.io.PrintWriter;\n");
		//delimiter field
		sb.append("//begin dynamic import\n");
		sb.append(END_IMPORT_DELIMIER + "\n\n");
		//class field
		sb.append("public class " + servName + " implements Servlet{\n");
		sb.append("\tpublic void init(ServletConfig config) throws ServletException { }\n\n");
		sb.append("\tpublic void service(ServletRequest request, ServletResponse response)\n");
		sb.append("\t\tthrows ServletException, IOException {\n");
		sb.append("\t\tPrintWriter out = response.getWriter();\n\n");
		//jsp convert
		int last_pos = 0, cur_pos = 0;
		sb.append("\t\tout.print(\"");
		//convert the dynamic part
		try{
			while(true){
				int p = jsp.indexOf("<%", last_pos);
				if(p == -1){
					sb.append(jsp.substring(cur_pos).replaceAll("\n", ""));
					//
					/*
					String s = jsp.substring(cur_pos).trim();
					p = cur_pos;
					boolean odd = false;
					while(true){
						int i = s.indexOf("\n", p);
						if(i == -1){
							break;
						}
						//
						if(odd){
							sb.append("\t\tout.print(\"" + s.substring(p, i) + "\");\r\n");
						}else{
							sb.append(s.substring(p, i) + "\");\r\n");
							System.out.println("jjj--" + s.substring(p, i));
							odd = true;
						}
						p = i+1;
					}
					if(p < s.length()){
						sb.append("out.print(\"" +s.substring(p, s.length()) + "\");\r\n");
					}
					sb.append("\t\tout.print(\"");
					*/
					break;
				}
				//
				last_pos = cur_pos;
				cur_pos = p;
				if(jsp.charAt(p+2) == '-' && jsp.charAt(p + 3) == '-'){
					//omit
					p = jsp.indexOf("--%>", p + 4);
					if(p == -1){
						return null;
					}
					last_pos = cur_pos = p + 4;
				}else if(jsp.charAt(p + 2) == '=' || jsp.charAt(p + 2) == '!'){
					int p1 = jsp.indexOf("%>", p+3);
					if(p1 == -1)	return null;
					//
					last_pos = cur_pos = p1 + 2;
					sb.append("\");\n");
					sb.append("\t\tout.print(" + jsp.substring(p+3, p1) + ");\n");
					sb.append("\t\tout.print(\"");
				}else if(jsp.charAt(p + 2) == '@'){
					int p1 = jsp.indexOf("%>", p+3);
					if(p1 == -1)	return null;
					//
					sb.append("\");\n");
					sb.append("\t\tout.print(\"");
					last_pos = cur_pos = p1 + 2;
					//handle jsp ins
					handleJSPIns(jsp.substring(p + 3, p1).trim(), response, sb);
					
				}else {
					int p1 = jsp.indexOf("%>", cur_pos);
					if(p1 == -1) return null;
					/*
					int i = 0;
					String s = jsp.substring(last_pos + 1, p).trim();
					boolean odd = false;
					while(true){
						int x = s.indexOf('\n', i);
						if(x != -1)
							break;
						if(odd){
							sb.append("out.print(\"" + s.substring(i, x) + "\");\r\n");
						}else{
							sb.append(s.substring(i, x) + "\");\r\n");
							odd = true;
						}
						i = x+1;
					}
					if(i < s.length()){
						sb.append("out.print(\""+ s.substring(i, s.length()) + "\");\r\n");
					}
					*/
					
					sb.append(jsp.substring(last_pos + 1, p).trim().
							replaceAll("\n", "") + "\");\n");
					//
					//contents on <% ... %>
					sb.append(jsp.substring(p + 2, p1) + "\n");
					sb.append("\t\tout.print(\"");
					last_pos = cur_pos = p1+2;
				}
			}	
		}catch (Exception e) {
			// done
			e.printStackTrace();
			return null;
		}
		//
		sb.append("\");\n");
		//end of service() method
		sb.append("\t\tout.flush();\n");
		sb.append("}\n\n");
		//tail
		//destroy method
		sb.append("\tpublic void destroy() { }\n\n");
		sb.append("\tpublic String getServletInfo() {\n\treturn null; \n}\n\n");
		sb.append("\tpublic ServletConfig getServletConfig() {\n\treturn null;\n}\n\n}");
		
		//logs
		String ret = JSPHelper.getRemoveSubstr(sb.toString().trim(), "out.print(\"\");");
		System.out.println("log------jsp-------\n" + ret + "----jsp------\n");
		return ret;
	}
	
	//
	//
	//
	//
	public static void handleJSPIns(String ins, Response response, StringBuffer sb) {
		//
		String[] es = ins.split(" ");
		//logs
		System.out.println("log jsp ins: " + ins + "\n");
		for(String string : es){
			System.out.print(" (" + string + "),");
		}
		System.out.println("\n");
		//logs end
		//
		if(es[0].equals("page")){
			for(int i = 1; i < es.length; i++){
				String[] entry = es[i].split("=");
				if(entry[0].equals("contentType")){
					//
					String ct = es[i].substring(entry[0].length() + 2);
					//logs
					System.out.println("log contentType ct: " + ct + "\n");
					//
					String[] tex = ct.split(";");
					response.setContentType(ct.replaceAll("\"", ""));
				}else if(entry[0].equals("import")){
					//
					int p = sb.indexOf(END_IMPORT_DELIMIER);
					String[] pks = entry[1].replaceAll("\"", "").split(",");
					String imports = "";
					for(String str : pks){
						if(str.charAt(str.length() - 1) == ';'){
							imports += "import " + str + "\n";
						}else{
							imports += "import " + str + ";\n";
						}
					}
					sb.insert(p, imports);
				}		
			}
		}
	}
	
	//
	//
	public static String getRemoveSubstr(String src, String sub) {
		int last = 0, cur = 0;
		String str = "";
		while(true){
			cur = src.indexOf(sub, last);
			if(cur == -1)
				break;
			//
			str += src.substring(last, cur);
			last = cur + sub.length();
		}
		if(last < src.length())
			str += src.substring(last, src.length());
		
		return str;
	}
}
