package com.wechat.member;

import com.utils.FakerUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1、基础脚本，分别执行了，创建、修改、查询、删除接口并进行了校验
 * 2、进行了优化，方法间进行解耦，每个方法可以独立行
 * 3、使用工具类命名法避免入参重复造成的报错。
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_03_01_repeat_timestamp {
    private static final Logger logger = LoggerFactory.getLogger(Demo_03_01_repeat_timestamp.class);
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
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        String body = "{\n" +
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \""+address+"\",\n" +
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
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        String crateBody = "{\n" +
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \""+address+"\",\n" +
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
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
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
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        String crateBody = "{\n" +
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \""+address+"\",\n" +
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
                .param("userid",userid)
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
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        String crateBody = "{\n" +
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \""+address+"\",\n" +
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
                .param("userid",userid)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+accessToken)
                .then()
                .log().body()
                .extract().response()
                ;
        assertEquals("0",response.path("errcode").toString());

    }
}
