package Server;

import java.io.IOException;

public class StaticResHandler {

	public void handle(Request request, Response response) {
		try{
			response.sendStaticRes();
		}catch (IOException e) {
			// done
			e.printStackTrace();
		}
	}

}
