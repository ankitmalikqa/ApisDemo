package dummyTestApis;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
public class SnakeAndLadders {

	int id;
	@BeforeTest
	public void init()
	{
	id=0;	
	}
	@Test(priority=1)
	public void add_new_player_check()
	{
		Response  r = when().
				       get("http://10.0.1.86/snl/rest/v1/board/new.json");
				      		 id=r.path("response.board.id");
	System.out.println(id);
	//System.out.println("response.board.id");
	}
	 @Test(priority=1)
	 public void register_player_check() throws JSONException
	{
		JSONObject json = new JSONObject();
        json.put("board", id);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "ankit6");
		json.put("player", jsonObj);
		JSONObject json1 = new JSONObject();
        json1.put("board", id);
        JSONObject jsonObj1 = new JSONObject();
        jsonObj1.put("name", "ankit5");
		json1.put("player", jsonObj1);
		JSONObject json2 = new JSONObject();
        json2.put("board", id);
        JSONObject jsonObj2 = new JSONObject();
        jsonObj2.put("name", "ankit4");
		json2.put("player", jsonObj2);
		JSONObject json3 = new JSONObject();
        json3.put("board", id);
        JSONObject jsonObj3 = new JSONObject();
        jsonObj3.put("name", "ankit3");
		json3.put("player", jsonObj3);
		System.out.println(json.toString());
		given().contentType("application/json").body(json.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
		given().contentType("application/json").body(json1.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
		given().contentType("application/json").body(json2.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
		given().contentType("application/json").body(json3.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
		//given().contentType(ContentType.JSON).body(json).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
		when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id).then().assertThat().body("response.board.players.id",hasSize(4));
	}
	 @Test(priority=2)
	public void check_if_there_are_4_players()
	{
		System.out.println("ddsds"+id);
	    when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id).then().assertThat().body("response.board.players.id",hasSize(4));
	}
	@Test(priority=3)
	public void check_status_code()
	{
		try{
			  URL url = new URL("http://10.0.1.86/snl/rest/v1/board/4164.json");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        assertThat(conn.getResponseCode()).isEqualTo(200);
		}
	catch(Exception e)
		{
		e.printStackTrace();
		}		
	}
	@Test(priority=4)
	public void player_already_exists_test() throws JSONException
	{
		JSONObject json = new JSONObject();

		json.put("board", id);

		JSONObject jsonObj = new JSONObject();

	
		jsonObj.put("name", "ankit6");
		json.put("player", jsonObj);
		System.out.println(json.toString());
		given().contentType("application/json").body(json.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().assertThat().statusCode(200);	
		
	}
	
	@Test(priority=5)
	public void check_for_delete_player() throws JSONException
	{
	    Response  r1=when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id);
		int id1=r1.path("response.board.players[0].id");
		when().delete("http://10.0.1.86/snl/rest/v1/player/{id1}.json",id1).then().assertThat().statusCode(200);
		//because id=1272 is in 5074.json
		when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id).then().assertThat().body("response.board.players.id",hasSize(3));
		JSONObject json = new JSONObject();
        json.put("board", id);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "ankit6");
		json.put("player", jsonObj);
		given().contentType("application/json").body(json.toString()).when().post("http://10.0.1.86/snl/rest/v1/player.json").then().statusCode(200);
	}
	@Test(priority=6)
	public void check_update_players() throws JSONException
	{
		JSONObject json = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "ankit9");
		json.put("player", jsonObj);
		Response  r1=when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id);
		int id2=r1.path("response.board.players[3].id");	
		given().contentType("application/json").body(json.toString()).when().put("http://10.0.1.86/snl/rest/v1/player/{id2}.json",id2).then().statusCode(200);
	}
	@Test(priority=7)
	public void rolldice_check()
	{
		Response  r1=when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id);
		int id2=r1.path("response.board.players[0].id");
		when().get("http://10.0.1.86/snl/rest/v1/move/{id}.json?player_id={id2}",id,id2).then().assertThat().statusCode(200);	
		}
	@Test(priority=8)
	public void test_version_v2_access_check()  throws JSONException
	{
		System.out.println("idbisnxosnx");
		JSONObject json = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "ankit9");
		json.put("player", jsonObj);
	        
	    RestAssured.given().
	        auth().
	        preemptive().
	        basic("su", "root_pass").contentType("application/json").body(json.toString()).
	    when().
	        post("http://10.0.1.86/snl/rest/v2/player.json").
	    then().
	        assertThat().
	        statusCode(200);
	    
	    RestAssured.given().
        auth().
        preemptive().
        basic("su", "root_pass").
    when().
        get("http://10.0.1.86/snl/rest/v2/board/{id}.json",id).
    then().
        assertThat().
        statusCode(200);
	}
@Test(priority=9)
public void invalid_turn_exception() 
{
	Response  r2=when().get("http://10.0.1.86/snl/rest/v1/board/{id}.json",id);
	int id3=r2.path("response.board.players[0].id");
	Response  r3=when().get("http://10.0.1.86/snl/rest/v1/move/{id}.json?player_id={id2}",id,id3);
//	Assert.assertEquals(-1, Integer.parseInt(r3.path("response.status")));
}

//public static void main(String... s)
//{
//	  try {
//		  
//		  
//
//            System.out.println("http://10.0.1.86/snl/oauth/token");
//            OAuthClient client = new OAuthClient(new URLConnectionClient());
//            System.out.println("17dc04ca1a60818994c0258d573aa522753f205c9dbc3032b50d4abc48b9025b");
//            OAuthClientRequest request = OAuthClientRequest.tokenLocation("http://10.0.1.86/snl/oauth/token")
//            		.setGrantType(GrantType.CLIENT_CREDENTIALS)
//                    .setClientId("7e8fd9f0d9e60402961786da919e19637aff10817c1eef6d30c7c1fd69cc730d")
//                    .setClientSecret("aef6f6cc2a602f284241d1140f8ed03d7ce232bfff3418a0e3f497912c178224")
//                    // .setScope() here if you want to set the token scope
//                    .buildQueryMessage();
//
//            String token =
//                    client.accessToken(request, OAuthJSONAccessTokenResponse.class).getAccessToken();
//            
//
//            System.out.println(token);
//            
//            
//            
//            
//            
//}
//	  catch(Exception ex)
//	  {
//		  ex.printStackTrace();
//	  }
//	  
//}
}