package com.resrassuredtest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class demoTest {
    @Test
    public void fun(){
        given()
                .get("https://www.baidu.com")
                .then()
                .statusCode(200)
                .log().all();
    }
}
