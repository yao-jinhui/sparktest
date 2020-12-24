package com.wechat.member;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基础脚本，完成对企业微信成员的创建、修改、查询、删除功能
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_01_base {
    private static final Logger logger = LoggerFactory.getLogger(Demo_01_base.class);
    static String accessToken;
    static String userId;

    @BeforeAll
    public static void getAccessToken(){
        accessToken = given().log().all()
                .when()
                .param("corpid","ww24b26af57fb47c80")
                .param("corpsecret","Ovh18KeVBR0Yu-wz9nMoLQopijZ0khsRI6Tm6vzOpSU")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response().path("access_token");
    }

    @DisplayName("创建成员")
    @Order(1)
    @Test
    void createMember(){
        String body = "{\n" +
                "    \"userid\": \"zhangsan\",\n" +
                "    \"name\": \"张三\",\n" +
                "    \"mobile\": \"13800000000\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \"2\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response response = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
//        userId = response.path("errmsg").toString();

    }
    @DisplayName("修改成员")
    @Test
    @Order(2)
    void updateMember() {

        String body = "{\n" +
                "    \"userid\": \"zhangsan\",\n" +
                "    \"name\": \"张美美\",\n" +
                "    \"mobile\": \"13800000000\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \"2\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response response=given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+accessToken+"")
                .then()
                .log().body()
                .extract().response();
        assertEquals("0",response.path("errcode").toString());

    }

    @DisplayName("查询成员")
    @Test
    @Order(3)
    void listMember() {

        Response response =given().log().all()
                .when()
                .param("userid","zhangsan")
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        assertEquals("0",response.path("errcode").toString());

    }
    @DisplayName("删除成员")
    @Test
    @Order(4)
    void deleteMember() {
        Response response = given().log().all()
                .contentType("application/json")
                .param("access_token",accessToken)
                .param("userid","zhangsan")
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+accessToken)
                .then()
                .log().body()
                .extract().response()
                ;
        assertEquals("0",response.path("errcode").toString());

    }
}
