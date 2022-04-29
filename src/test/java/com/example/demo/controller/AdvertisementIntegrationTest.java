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

  @Test
  public void getAllAdvertisements() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/advertisements")
        .then()
        .assertThat().statusCode(200);
  }

  @Test
  public void getAdvertisementById() throws JSONException {
    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", 1);

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
        .get("/advertisements/"+id)
        .then()
        .assertThat().log().all().statusCode(200);
  }

  @Test
  public void createAdvertisement() throws JSONException {
    Faker faker = new Faker();
    String randomTitle = faker.name().title();
    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", 1);

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
        .body("userId", equalTo(1));

  }

  @Test
  public void updateAdvertisement() throws JSONException {

    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", 1);

    int id = given()
        .contentType(ContentType.JSON).body(newAdvertisement.toString())
        .when()
        .post("/advertisements")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    Faker fakerUpdate = new Faker();
    String randomUpdateTitle = faker.name().title();
    JSONObject newUpdateAdvertisement = new JSONObject();

    newUpdateAdvertisement.put("title", randomUpdateTitle);
    newUpdateAdvertisement.put("description", randomUpdateTitle + " description");
    newUpdateAdvertisement.put("userId", 1);

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
        .body("userId", equalTo(1));

  }

  @Test
  public void deleteAdvertisement() throws JSONException {

    Faker faker = new Faker();
    String randomTitle = faker.name().title();

    JSONObject newAdvertisement = new JSONObject();

    newAdvertisement.put("title", randomTitle);
    newAdvertisement.put("description", randomTitle + " description");
    newAdvertisement.put("userId", 1);

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
        .log().all().assertThat().statusCode(500);

  }

}
