package com.wechat.apiobject;

import com.utils.FakerUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MemberObject {
    public static Response creatMember(String userid, String name, String mobile, int gender, String address, String accessToken){
        String creatBody = "{\n" +
                "    \"userid\": \""+userid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \""+address+"\",\n" +
                "    \"main_department\": 1\n" +
                "}";
        Response creatResponse = given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then().log().all()
                .extract()
                .response();
        return creatResponse;
    }

    public static String getMemberId(String accessToken){
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        return userid;
    }

    public static Response updateMember(String name, String mobile,int gender,String address,String accessToken){
        String createUserid =getMemberId(accessToken);
        String updateBody = "{\n" +
                "    \"userid\": \""+createUserid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response updateResponse=given().log().all()
                .contentType("application/json")
                .body(updateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+accessToken+"")
                .then()
                .log().body()
                .extract().response();
        return updateResponse;
    }

    public static Response updateMember(String createUserid, String name, String mobile,int gender,String address,String accessToken){
        String updateBody = "{\n" +
                "    \"userid\": \""+createUserid+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"mobile\": \""+mobile+"\",\n" +
                "    \"department\": [1, 15],\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"address\": \"上海市\",\n" +
                "    \"main_department\": 1,\n" +
                "}";
        Response updateResponse=given().log().all()
                .contentType("application/json")
                .body(updateBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+accessToken+"")
                .then()
                .log().body()
                .extract().response();
        return updateResponse;
    }

    public static Response selectMember(String createUserid,String accessToken){
        Response selectResponse =given().log().all()
                .when()
                .param("userid",createUserid)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        return selectResponse;
    }

    public static Response deletMember(String userid,String accessToken){
        Response deletResponse = given().log().all()
                .contentType("application/json")
                .param("access_token",accessToken)
                .param("userid",userid)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+accessToken)
                .then()
                .log().body()
                .extract().response()
                ;
        return deletResponse;
    }
}
