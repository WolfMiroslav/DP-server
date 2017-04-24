package main;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;



@WebServlet("/DatabaseConnectionServlet")
public class DatabaseConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DatabaseConnectionServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    	System.out.println("ahoj get");
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);		
		try 
		{	
			int requestLength = request.getContentLength();
			byte[] input = new byte[requestLength];
			ServletInputStream servletInput = request.getInputStream();
			
			int c, count = 0, range = 1000;
			while((c = servletInput.read(input, count, input.length-count) )!= -1)
			{
				count +=c;
			}
						
			String receivedString = new String(input);
			
			String json = "{\"received\": \"ok\"}";
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	        response.getWriter().flush();
	        response.getWriter().close();
			
			/*response.setStatus(HttpServletResponse.SC_OK);
			response.setCharacterEncoding("UTF-8");
			response.addHeader("content-type", "application/json");
			//setContentType("application/json");

			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
			
			writer.write("{\"return\":\"ok\"}");
			writer.flush();
			writer.close();*/
			
/*			String subStr = receivedString.substring(8, receivedString.length());			
			String[] latLongStrings = subStr.split(",");
			
			Double longitude = Double.valueOf(latLongStrings[0].substring(10));
			Double latitude = Double.valueOf(latLongStrings[1].substring(10));
			
			if(latLongStrings.length<=3)
			{
				range = 1000;
			}
			else 
			{
				range = Integer.valueOf(latLongStrings[3]);
			}*/
			
			//System.out.println("long: "+longitude + "   lat: "+latitude );
			
				
		}
		catch (IOException e) {
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
