package dummyTestApis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import junit.framework.Assert; 

//Dummy APis

public class DummyApis {
	int id;
	public static void main(String... s) throws UnirestException, JSONException, IOException
	{
		
		DummyApis dummy = new DummyApis();
		String jsonStringPost=dummy.readFile(System.getProperty("user.dir")+"\\src\\test\\resources\\Files\\requestPost.json");
		//Hit post API using RestAssured  
		//Post: http://dummy.restapiexample.com/api/v1/create
		System.out.println("postRequest"+jsonStringPost);
		Response requestPost= RestAssured.given().contentType("application/json").body(jsonStringPost).when().
				post("http://dummy.restapiexample.com/api/v1/create");
		System.out.println(requestPost.asString());
		dummy.id=requestPost.path("data.id");
		Assert.assertEquals(requestPost.getStatusCode(),200);
		System.out.println("Identifire generated:-> "+dummy.id);
		System.out.println("Post status code "+requestPost.getStatusCode());
		
		
		//Hit post API using Unirest  
		//Get: https://jsonplaceholder.typicode.com/todos/1
		HttpResponse<String> responseGet =  Unirest.get("https://jsonplaceholder.typicode.com/todos/1")
		  .header("Cookie", "__cfduid=d5c977a1ba316ec13accac0ab043e5ced1587119873")
		  .asString();
	   Assert.assertEquals(responseGet.getStatus(),200);	
	   Assert.assertEquals(new JSONObject(responseGet.getBody()).getInt("id"), 1);
       System.out.println("GET status code "+responseGet.getStatus());
       System.out.println("GET Response "+responseGet.getBody());
		 
		//Hit Get api using Unirest
       
       
       //PUT Service using Response class
       //https://jsonplaceholder.typicode.com/posts/1
		
        String jsonStringPut=dummy.readFile(System.getProperty("user.dir")+"\\src\\test\\resources\\Files\\requestPut.json");
        Response requestPut= RestAssured.given().contentType("application/json").body(jsonStringPut).when().put("https://jsonplaceholder.typicode.com/posts/1");
        System.out.println("putRequest"+jsonStringPut);
        System.out.println("PUT Response "+requestPut.asString());
        dummy.id=requestPut.path("id");
		System.out.println("Identifire generated:-> "+dummy.id);
		System.out.println("PUT Status code "+requestPut.getStatusCode());
		Assert.assertEquals(requestPut.getStatusCode(),200);
		Assert.assertEquals(new JSONObject(requestPut.asString()).getString("title"), new JSONObject(jsonStringPut).getString("title"));
		Assert.assertEquals(new JSONObject(requestPut.asString()).getString("id"), new JSONObject(jsonStringPut).getString("id"));
		Assert.assertEquals(new JSONObject(requestPut.asString()).getString("body"), new JSONObject(jsonStringPut).getString("body"));
		Assert.assertEquals(new JSONObject(requestPut.asString()).getString("userId"), new JSONObject(jsonStringPut).getString("userId"));
		
		
		//Delete Request
		//https://jsonplaceholder.typicode.com/posts/1
		 Response requestDelete= RestAssured.given().contentType("application/json").body(jsonStringPut).when().delete("https://jsonplaceholder.typicode.com/posts/1");
		 System.out.println("Delete Status code: "+requestDelete.getStatusCode());	
		 Assert.assertEquals(requestDelete.getStatusCode(),200);
		
		 //Patch request
		 //https://jsonplaceholder.typicode.com/posts/1 
		 String jsonStringPatch="{\r\n" + 
		 		"	\"title\": \"Computers\"\r\n" + 
		 		"}";
		 Response requestPatch= RestAssured.given().contentType("application/json").body(jsonStringPatch).when().patch("https://jsonplaceholder.typicode.com/posts/1");
		 Assert.assertEquals(requestPatch.getStatusCode(),200);
		 System.out.println("Patch Status code "+requestPatch.getStatusCode());	
		 System.out.println("Patch Response "+requestPatch.asString());	
		 Assert.assertEquals(new JSONObject(requestPatch.asString()).getString("title"), new JSONObject(jsonStringPatch).getString("title"));
		
		 String xml="<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:SnakesAndLadders:v1\">\r\n" + 
		 		"   <soapenv:Header/>\r\n" + 
		 		"   <soapenv:Body>\r\n" + 
		 		"      <urn:getBoard soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n" + 
		 		"         <id xsi:type=\"xsd:int\">952</id>\r\n" + 
		 		"      </urn:getBoard>\r\n" + 
		 		"   </soapenv:Body>\r\n" + 
		 		"</soapenv:Envelope>";
		 Response requestSoap= RestAssured.given().contentType("text/xml").body(xml).when().post("http://10.0.1.86/snl/soap/v1/action");
		 	System.out.println(requestSoap.asString());
		 	
		 	String xmlCal="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">\r\n" + 
		 			"   <soapenv:Header/>\r\n" + 
		 			"   <soapenv:Body>\r\n" + 
		 			"      <tem:AddInteger>\r\n" + 
		 			"         <tem:Arg1>3</tem:Arg1>\r\n" + 
		 			"         <tem:Arg2>7</tem:Arg2>\r\n" + 
		 			"      </tem:AddInteger>\r\n" + 
		 			"   </soapenv:Body>\r\n" + 
		 			"</soapenv:Envelope>";
			 Response requestSoapCal= RestAssured.given().header("SOAPAction", "http://tempuri.org/SOAP.Demo.AddInteger").relaxedHTTPSValidation().contentType(ContentType.XML).body(xmlCal).when().relaxedHTTPSValidation().
					 post("https://www.crcind.com:443/csp/samples/SOAP.Demo.cls");
			 	System.out.println(requestSoapCal.asString());
	
	}
public String readFile(String file) throws IOException
{
	BufferedReader reader = new BufferedReader(new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    try {
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
    } finally {
        reader.close();
    }
	return stringBuilder.toString();
}
}