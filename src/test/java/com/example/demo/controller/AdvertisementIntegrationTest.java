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
public class AdvertisementIntegrationTest {

  private final static String BASE_URI = "http://localhost";

  @LocalServerPort
  private int port;

  private ValidatableResponse validatableResponse;

  private ValidatableResponse validatableResponse1;

  @BeforeEach
  public void configureRestAssured() {
    RestAssured.baseURI = BASE_URI;
    RestAssured.port = port;
  }
  public int getUserId() throws JSONException {
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
    return id;
  }
  @Test
  public void getAllAdvertisementsSuccess() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements")
        .then()
        .assertThat().statusCode(200);
  }
  @Test
  public void getAllAdvertisementsFailure() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisement")
        .then()
        .assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");
  }

  @Test
  public void getAdvertisementByIdSuccess() throws JSONException {
    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", getUserId());

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + id)
        .then()
        .assertThat().log().all().statusCode(200);
  }
  @Test
  public void getAdvertisementByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");

  }
  @Test
  public void createAdvertisement() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomTitle = faker.name().title();
    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId",userId);

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("title", equalTo(randomTitle))
        .body("description", equalTo(randomTitle + " description"))
        .body("userId", equalTo(userId));

  }
  @Test
  public void createAdvertisementFailure() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomTitle = faker.name().title();
    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", null);
    newAdvertisement.put("description", null);
    newAdvertisement.put("userId",null);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(400)
        .statusLine("HTTP/1.1 400 ");

  }




  @Test
  public void updateAdvertisementSuccess() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    Faker fakerUpdate = new Faker();
    String randomUpdateTitle = fakerUpdate.name().title();
    JSONObject newUpdateAdvertisement = new JSONObject();

    newUpdateAdvertisement.put("title", randomUpdateTitle);
    newUpdateAdvertisement.put("description", randomUpdateTitle + " description");
    newUpdateAdvertisement.put("userId", userId);

    validatableResponse = given()
        .contentType(ContentType.JSON).body(newUpdateAdvertisement.toString())
        .when()
        .put("/advertisements/" + id)
        .then()
        .log().all().assertThat().statusCode(200);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("title", equalTo(randomUpdateTitle))
        .body("description", equalTo(randomUpdateTitle + " description"))
        .body("userId", equalTo(userId));

  }
  @Test
  public void updateAdvertisementFailure() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    Faker fakerUpdate = new Faker();
    String randomUpdateTitle = fakerUpdate.name().title();
    JSONObject newUpdateAdvertisement = new JSONObject();

    newUpdateAdvertisement.put("title", null);
    newUpdateAdvertisement.put("description", null);
    newUpdateAdvertisement.put("userId", null);

    validatableResponse1 = given()
        .contentType(ContentType.JSON).body(newUpdateAdvertisement.toString())
        .when()
        .post("/users/" + id)
        .then()
        .log().all().assertThat().statusCode(405)
        .statusLine("HTTP/1.1 405 ");
  }

  @Test
  public void deleteAdvertisement() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/advertisements/" + id)
        .then()
        .log().all().assertThat().statusCode(204);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + id)
        .then()
        .log().all().assertThat().statusCode(404);

  }
  @Test
  public void deleteUserByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");
  }

}
