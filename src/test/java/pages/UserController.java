package pages;

import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public  UserController(Properties prop){
        this.prop = prop;
    }
    public Response ExtractGmailMessage(UserModel userModel){

        RestAssured.baseURI = prop.getProperty("GmailAPI");
        Response res = (Response) given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("gmailToken"))
                .when().get("/gmail/v1/users/me/messages");
        String MessageId = res.jsonPath().getString("messages[0].id");


        return   (Response) given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("gmailToken"))
                .when().get("/gmail/v1/users/me/messages/" + MessageId)
                .then()
                .extract().response();

    }
}
