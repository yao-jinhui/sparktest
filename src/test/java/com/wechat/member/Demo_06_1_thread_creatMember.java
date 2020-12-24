package com.wechat.member;

import com.utils.FakerUtils;
import com.wechat.apiobject.MemberObject;
import com.wechat.apiobject.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 对创建成员进行并发测试
 */
public class Demo_06_1_thread_creatMember {
    private static final Logger logger = LoggerFactory.getLogger(Demo_06_1_thread_creatMember.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken(){
        accessToken= TokenHelper.getAccessToken();
        logger.info(accessToken);
    }

    @DisplayName("创建成员")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void createMember(){
        String mobile = FakerUtils.getTel();
        String userid = "userid"+ FakerUtils.getTimeStamp()+mobile.substring(8,11);
        String name =  FakerUtils.getChineseName();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        Response response=MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
}
