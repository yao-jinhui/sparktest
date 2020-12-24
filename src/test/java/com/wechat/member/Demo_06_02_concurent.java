package com.wechat.member;

import com.utils.FakerUtils;
import com.wechat.apiobject.MemberObject;
import com.wechat.apiobject.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 对创建成员进行并发测试
 */
public class Demo_06_02_concurent {
    private static final Logger logger = LoggerFactory.getLogger(Demo_06_02_concurent.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken(){
        accessToken= TokenHelper.getAccessToken();
        logger.info(accessToken);
    }

    @DisplayName("创建成员")
    @Test
    @RepeatedTest(10)
    void createMember(){
        String mobile = FakerUtils.getTel();
        String userid = "userid"+ FakerUtils.getTimeStamp()+mobile.substring(8,11);
        String name =  FakerUtils.getChineseName();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        Response response=MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }

    @DisplayName("修改成员")
    @Test
    @RepeatedTest(10)
    void updateMember() {
        String backendStr = Thread.currentThread().getId()+FakerUtils.getTimeStamp()+"";
        String createUserid = "userid"+ FakerUtils.getTimeStamp()+backendStr;
        String createMobile = FakerUtils.getTel();
        String createName =  FakerUtils.getChineseName();
        int createGender = FakerUtils.getNum(1,2);
        String createAddress = FakerUtils.getRoad();
        Response createResponse=MemberObject.creatMember(createUserid, createName, createMobile, createGender, createAddress, accessToken);

        String updateName =  FakerUtils.getChineseName();
        String updateMobile = FakerUtils.getTel();
        int updateGender = FakerUtils.getNum(1,2);
        String updateAddress = FakerUtils.getRoad();

        Response updateResponse=MemberObject.updateMember(createUserid,updateName, updateMobile, updateGender, updateAddress, accessToken);
        assertEquals("0",updateResponse.path("errcode").toString());

    }
}
