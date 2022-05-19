package com.example.demo.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
  private final static String BASE_URI = "http://localhost";

  @LocalServerPort
  private int port;

  private ValidatableResponse validatableResponse;

  private ValidatableResponse validatableResponse1;

  private ValidatableResponse validatableResponse2;

  @BeforeEach
  public void configureRestAssured() {
    RestAssured.baseURI = BASE_URI;
    RestAssured.port = port;
  }
  @Test
  public void getAllUsersSuccess() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users")
        .then()
        .assertThat().statusCode(200)
        .statusLine("HTTP/1.1 200 ");

  }
  @Test
  public void getAllUsersFailure() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user")
        .then()
        .assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");

  }

  @Test
  public void getUserByIdSuccess() throws JSONException {
    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();

    JSONObject newUser = new JSONObject();

    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + id)
        .then()
        .assertThat().log().all().statusCode(200).statusLine("HTTP/1.1 200 ");
  }
  @Test
  public void getUserByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");

  }

  @Test
  public void createUserSuccess() throws JSONException {

    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();
    JSONObject newUser = new JSONObject();
    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("displayName", equalTo(randomName))
        .body("displaySurname", equalTo(randomSurname))
        .body("phoneNumber", equalTo(randomPhone))
        .body("email", equalTo(randomEmail));

  }
 @Test
  public void createUserFailure() throws JSONException {
   Faker faker = new Faker();
   String randomName = faker.name().firstName();
   String randomSurname = faker.name().lastName();
   String randomPhone = faker.phoneNumber().phoneNumber();
   String randomEmail = faker.internet().emailAddress();
   JSONObject newUser = new JSONObject();
   newUser.put("displayName", randomName);
   newUser.put("displaySurname", randomSurname);
   newUser.put("phoneNumber", null);
   newUser.put("email", null);
   validatableResponse1 = given()
       .contentType(ContentType.JSON)
       .when()
       .post("/users")
       .then()
       .log().all().assertThat().statusCode(400)
       .statusLine("HTTP/1.1 400 ");

  }
  @Test
  public void createUserFailure2() throws JSONException {
    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();
    JSONObject newUser = new JSONObject();
    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("displayName", equalTo(randomName))
        .body("displaySurname", equalTo(randomSurname))
        .body("phoneNumber", equalTo(randomPhone))
        .body("email", equalTo(randomEmail));

    JSONObject newUser2 = new JSONObject();
    newUser2.put("displayName", randomName);
    newUser2.put("displaySurname", randomSurname);
    newUser2.put("phoneNumber", randomPhone);
    newUser2.put("email", randomEmail);

    validatableResponse2 = given()
        .contentType(ContentType.JSON)
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(400)
        .statusLine("HTTP/1.1 400 ");

  }

  @Test
  public void updateUser() throws JSONException {
    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();
    JSONObject newUser = new JSONObject();
    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    JSONObject newUserUpdated = new JSONObject();

    newUserUpdated.put("displayName", randomName + "update");
    newUserUpdated.put("displaySurname", randomSurname);
    newUserUpdated.put("phoneNumber", randomPhone);
    newUserUpdated.put("email", randomEmail);

    validatableResponse = given()
        .contentType(ContentType.JSON).body(newUserUpdated.toString())
        .when()
        .put("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(200);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("displayName", equalTo(randomName + "update"))
        .body("displaySurname", equalTo(randomSurname))
        .body("phoneNumber", equalTo(randomPhone))
        .body("email", equalTo(randomEmail));

  }

  @Test
  public void updateUserFailure() throws JSONException {
    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();
    JSONObject newUser = new JSONObject();
    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    JSONObject newUserUpdated = new JSONObject();

    newUserUpdated.put("displayName", randomName + "update");
    newUserUpdated.put("displaySurname", randomSurname);
    newUserUpdated.put("phoneNumber", null);
    newUserUpdated.put("email", null);

    validatableResponse1 = given()
        .contentType(ContentType.JSON).body(newUserUpdated.toString())
        .when()
        .post("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(405)
        .statusLine("HTTP/1.1 405 ");

  }


  @Test
  public void deleteUserSuccess() throws JSONException {

    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomSurname = faker.name().lastName();
    String randomPhone = faker.phoneNumber().phoneNumber();
    String randomEmail = faker.internet().emailAddress();
    JSONObject newUser = new JSONObject();
    newUser.put("displayName", randomName);
    newUser.put("displaySurname", randomSurname);
    newUser.put("phoneNumber", randomPhone);
    newUser.put("email", randomEmail);

    int id = given()
        .contentType(ContentType.JSON).body(newUser.toString())
        .when()
        .post("/users")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(204);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(404);

  }

  @Test
  public void deleteUserByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");
  }
}