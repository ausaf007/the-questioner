/* 
 * The MIT License
 *
 * Copyright 2021 Md Ausaf Rashid.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package thequestioner;
/**
 *
 * @author Md Ausaf Rashid
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credential_Storage {

    //MySQL Database Credentials
    static String getUsername(){
    
        String username=null;
        
        Properties prop = new Properties();
	InputStream input = null;
	try {
		input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);
                username=prop.getProperty("dbuser");
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        return username;
    }    
    static String getPassword(){
        
        String password=null;
        
        Properties prop = new Properties();
	InputStream input = null;
	try {
		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		
                
                password=prop.getProperty("dbpassword");
                
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        return password;
    }
    static String getDatabase(){
        String database=null;
        
        Properties prop = new Properties();
	InputStream input = null;
	try {
		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		
                
                database=prop.getProperty("database");
                
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        return database;
        
        
    }
    
    final static String DB_USERNAME=getUsername();
    final static String DB_PASSWORD=getPassword();
    final static String DB_NAME=getDatabase();
    
    

    
    static boolean db_initialised=Boolean.parseBoolean(getProperty("db_initialised","db_initialised.properties"));
    
    
    
    //getting SMTP credendials for sending Emails
    
    
    static String FROM_EMAIL = getProperty("FROM_EMAIL","config.properties");
    static String FROMNAME = getProperty("FROMNAME","config.properties");
    static String SMTP_USERNAME = getProperty("SMTP_USERNAME","config.properties");
    static String SMTP_PASSWORD = getProperty("SMTP_PASSWORD","config.properties");
    
    static String CONFIGSET = getProperty("CONFIGSET","config.properties");
    static String HOST = getProperty("HOST","config.properties");
    
    static int PORT = Integer.parseInt(getProperty("PORT","config.properties"));
    
    
    static String getProperty(String Propt_Name, String File_Name){
        
        String Propt_Value=null;
        
        Properties prop = new Properties();
	InputStream input = null;
	try {
		input = new FileInputStream(File_Name);
		// load a properties file
		prop.load(input);

                Propt_Value=prop.getProperty(Propt_Name);
                
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        return Propt_Value;
    }
    
    
    
   
}
