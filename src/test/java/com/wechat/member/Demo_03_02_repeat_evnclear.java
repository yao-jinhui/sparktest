package com.wechat.member;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1、基础脚本，分别执行了，创建、修改、查询、删除接口并进行了校验
 * 2、进行了优化，方法间进行解耦，每个方法可以独立行
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_03_02_repeat_evnclear {
    private static final Logger logger = LoggerFactory.getLogger(Demo_03_02_repeat_evnclear.class);
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

//    @AfterEach
    @BeforeEach
    void clearMember(){
        Response listResponse =given().log().all()
                .when()
                .param("department_id",1)
                .param("fetch_child",1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token="+accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        ArrayList<String> memberIdList = listResponse.path("userlist.userid");
        for(String memberId : memberIdList){
            if(memberId.equals("MeJinHui") || memberId.equals("nnew")){
                continue;
            }
            Response response = given().log().all()
                    .contentType("application/json")
                    .param("access_token",accessToken)
                    .param("userid",memberId)
                    .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+accessToken)
                    .then()
                    .log().body()
                    .extract().response()
                    ;
        }
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
        String crateBody = "{\n" +
                "    \"userid\": \"zhangsan\",\n" +
                "    \"name\": \"张三\",\n" +
                "    \"mobile\": \"13800000000\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \"2\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response createResponse = given().log().all()
                .contentType("application/json")
                .body(crateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
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
        String crateBody = "{\n" +
                "    \"userid\": \"zhangsan\",\n" +
                "    \"name\": \"张三\",\n" +
                "    \"mobile\": \"13800000000\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \"2\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response createResponse = given().log().all()
                .contentType("application/json")
                .body(crateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
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
        String crateBody = "{\n" +
                "    \"userid\": \"zhangsan\",\n" +
                "    \"name\": \"张三\",\n" +
                "    \"mobile\": \"13800000000\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \"2\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response createResponse = given().log().all()
                .contentType("application/json")
                .body(crateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
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
