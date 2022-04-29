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

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
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
  public void listUsers() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/users")
        .then()
        .assertThat().statusCode(200);

  }

  @Test
  public void listUser() throws JSONException {
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
        .assertThat().log().all().statusCode(200);
  }

  @Test
  public void createUser_success() throws JSONException {

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
  public void deleteUser() throws JSONException {

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
        .get("/user/" + id)
        .then()
        .log().all().assertThat().statusCode(404);

  }

}
