package com.company.reqres.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTests extends BaseTest{

    @Test
    @DisplayName("POST /api/users - Create user named 'Morpheus' and validate response")
    void createUser_Morpheus_ShouldReturn201() {
        /*
        Test steps:
        1) Send POST /api/users with body { name: "Morpheus", job: "leader" }.
        2) Expect HTTP 201.
        3) Validate that 'name' equals "Morpheus".
        4) Validate that 'id' is not empty and 'createdAt' exists.
        */
        given()
                .spec(reqSpec)
                .body("{\"name\":\"Morpheus\",\"job\":\"leader\"}")
                .when()
                .post("/api/users")
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .body("name", equalTo("Morpheus"))
                .body("id", not(isEmptyOrNullString()))
                .body("createdAt", notNullValue());

    }

    @Test
    @DisplayName("GET /api/users/{id} - Retrieve existing user by a known id (fixture)")
    void getUser_ByKnownId_ShouldReturn200() {
        /*
         Test steps (English):
         1) Send GET /api/users/2 (known existing user in ReqRes fixtures).
         2) Expect HTTP 200.
         3) Validate id=2 and basic fields are present and consistent.
         Note: ReqRes does not persist created users, so GET by the id returned in POST is not possible.
        */
        int knownId = 2;

        given()
                .spec(reqSpec)
                .when()
                .get("/api/users/{id}", knownId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data.id", equalTo(knownId))
                .body("data.first_name", equalTo("Janet"))
                .body("data.email", containsString("@reqres.in"));
    }


}
