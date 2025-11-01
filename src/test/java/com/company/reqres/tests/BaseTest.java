package com.company.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification reqSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = "https://reqres.in";

        String apiKey = System.getenv().getOrDefault("REQRES_API_KEY", "reqres-free-v1");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Environment variable 'REQRES_API_KEY' is not set.");
        }

        reqSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", apiKey)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(Matchers.anyOf(Matchers.is(200), Matchers.is(201)))
                .expectResponseTime(Matchers.lessThan(2000L))
                .build();


    }
}