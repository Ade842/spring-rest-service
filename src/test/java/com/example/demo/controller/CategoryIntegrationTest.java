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
public class CategoryIntegrationTest {

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
  public void getAllCategoriesSuccess() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories")
        .then()
        .assertThat().statusCode(200);
  }
  @Test
  public void getAllCategoriesFailure() {
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categorie")
        .then()
        .assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");
  }

  @Test
  public void getCategoryByIdSuccess() throws JSONException {
    Faker faker = new Faker();
    String randomName = faker.name().title();

    JSONObject newCategory = new JSONObject();

    newCategory.put("categoryName", randomName);
    newCategory.put("userId", getUserId());

    int id = given()
        .contentType(ContentType.JSON).body(newCategory.toString())
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + id)
        .then()
        .assertThat().log().all().statusCode(200);
  }
  @Test
  public void getCategoryByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");

  }

  @Test
  public void createCategory() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomName = faker.name().title();
    JSONObject newCategory = new JSONObject();

    newCategory.put("categoryName", randomName);
    newCategory.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newCategory.toString())
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");
    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + id)
        .then()
        .assertThat().log().all().statusCode(200);
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("categoryName", equalTo(randomName))
        .body("userId", equalTo(userId));

  }
  @Test
  public void createCategoryFailure() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomName = faker.name().title();
    JSONObject newCategory = new JSONObject();

    newCategory.put("title", null);
    newCategory.put("userId",null);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(400)
        .statusLine("HTTP/1.1 400 ");

  }
  @Test
  public void createCategoryFailure2() throws JSONException {
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

    String randomNameCategory = faker.name().title();
    JSONObject newCategory = new JSONObject();

    newCategory.put("categoryName", randomNameCategory);
    newCategory.put("userId", id);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(400)
        .statusLine("HTTP/1.1 400 ");

  }
  @Test
  public void updateCategorySuccess() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomName = faker.name().title();

    JSONObject newCategory = new JSONObject();

    newCategory.put("categoryName", randomName);
    newCategory.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newCategory.toString())
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    Faker fakerUpdate = new Faker();
    String randomUpdateName = fakerUpdate.name().title();
    JSONObject newUpdateCategory = new JSONObject();

    newUpdateCategory.put("categoryName", randomUpdateName);
    newUpdateCategory.put("userId", userId);

    validatableResponse = given()
        .contentType(ContentType.JSON).body(newUpdateCategory.toString())
        .when()
        .put("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(200);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(200)
        .body("categoryName", equalTo(randomUpdateName))
        .body("userId", equalTo(userId));

  }
  @Test
  public void updateCategoryFailure() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomName = faker.name().title();

    JSONObject newCategory= new JSONObject();

    newCategory.put("categoryName", randomName);
    newCategory.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newCategory.toString())
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    Faker fakerUpdate = new Faker();
    String randomUpdateName = fakerUpdate.name().title();
    JSONObject newUpdateCategory = new JSONObject();

    newUpdateCategory.put("categoryName", null);
    newUpdateCategory.put("userId", null);

    validatableResponse1 = given()
        .contentType(ContentType.JSON).body(newUpdateCategory.toString())
        .when()
        .post("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(405)
        .statusLine("HTTP/1.1 405 ");
  }

  @Test
  public void deleteCategory() throws JSONException {
    int userId = getUserId();
    Faker faker = new Faker();
    String randomName = faker.name().title();

    JSONObject newCategory = new JSONObject();

    newCategory.put("categoryName", randomName);
    newCategory.put("userId", userId);

    int id = given()
        .contentType(ContentType.JSON).body(newCategory.toString())
        .when()
        .post("/categories")
        .then()
        .log().all().assertThat().statusCode(200).extract().
        path("id");

    validatableResponse = given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(204);

    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + id)
        .then()
        .log().all().assertThat().statusCode(404);

  }
  @Test
  public void deleteCategoryByIdFailure() throws JSONException {
    validatableResponse1 = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/categories/" + 999999999)
        .then()
        .log().all().assertThat().statusCode(404)
        .statusLine("HTTP/1.1 404 ");
  }


}
