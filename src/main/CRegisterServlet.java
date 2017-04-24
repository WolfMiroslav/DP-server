package main;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database.CConnector;



@WebServlet("/CRegisterServlet")
public class CRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CRegisterServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    	System.out.println("ahoj get");
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try 
		{	
			System.out.println("register");
			
			CConnector connector = new CConnector();
			
			int requestLength = request.getContentLength();
			byte[] input = new byte[requestLength];
			ServletInputStream servletInput = request.getInputStream();
			
			int c, count = 0, range = 1000;
			while((c = servletInput.read(input, count, input.length-count) )!= -1)
			{
				count +=c;
			}
						
			String receivedString = new String(input);
			JsonObject obj = new JsonParser().parse(receivedString).getAsJsonObject();			
			
			
			
			JSONObject json = connector.registration(obj.get("username").getAsString(), obj.get("userpassword").getAsString(), obj.get("userage").getAsString(), obj.get("usergender").getAsBoolean());
			
			
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        
	        json.write(response.getWriter());
	        	        
	        response.getWriter().flush();
	        response.getWriter().close();		
				
		}
		catch (IOException | JSONException e) {
			try
			{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } 
			catch (IOException ioe) {
            	
            }
		} 
	}

}
