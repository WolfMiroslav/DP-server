package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

public class CConnector {
	
	Connection conn;
	
	public JSONObject login(String name, String password)
	{
		try 
		{
			//Class.forName("net.sf.json.JSONObject");
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/myEmotionDatabase";
			conn = DriverManager.getConnection(url, "postgres", "0143");
    
			String query = "SELECT pk_id FROM users WHERE name = '"+name+"' AND password = '"+ password +"'";
			
			PreparedStatement statement = conn.prepareStatement(query);
			
			ResultSet result = statement.executeQuery();
					
			JSONObject json = new JSONObject();
			
			while(result.next())
			{				
				if(result.getMetaData().getColumnCount()>=1 &&result.getString(1) != null)
				{
					String id = result.getString(1);	
					//json.put("returnValue", 0);
					json.put("id", id);
					break;
				}		
			}
								
			/*Statement queryStatement = conn.createStatement();
			
			queryStatement.close();*/
			
			conn.close();
			
			if(json.isNull("id"))
			{
				json.put("id", "-1");				
			}	
			
			return json;
		}
		catch( Exception e ) 
		{
            e.printStackTrace();
        }
		
		return null;
	}
	
	public JSONObject registration(String name, String password, String age, boolean gender)
	{
		try 
		{
			//Class.forName("net.sf.json.JSONObject");
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/myEmotionDatabase";
			conn = DriverManager.getConnection(url, "postgres", "0143");
    			
			String selectQuery = "SELECT count(name) FROM users WHERE name = '"+name+"'";			
			PreparedStatement statement = conn.prepareStatement(selectQuery);			
			ResultSet result = statement.executeQuery();					
			JSONObject json = new JSONObject();
			
			int count = 0;
			
			result.next();				
			if(result.getMetaData().getColumnCount()>=1 &&result.getString(1) != null)
			{
				count = result.getInt(1);			
			}
			
			if(count == 0)
			{
				String insertQuery = "INSERT INTO users VALUES('"+name+"'," + age + ","+ gender + ",'"+password+"')";
				statement = conn.prepareStatement(insertQuery);
				statement.executeUpdate();
			}
			
			conn.close();
						
			return json;
		}
		catch( Exception e ) 
		{
            e.printStackTrace();
        }
		
		return null;
	} 
	
}
