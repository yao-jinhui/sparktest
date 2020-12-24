package com.wechat.member;

import com.utils.FakerUtils;
import com.wechat.apiobject.MemberObject;
import com.wechat.apiobject.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1、基础脚本，分别执行了，创建、修改、查询、删除接口并进行了校验
 * 2、进行了优化，方法间进行解耦，每个方法可以独立行
 * 3、进行了优化，使用时间戳命名法避免入参重复造成的报错。
 * 4、进行了优化，每次方法执行前后都对历史数据进行清理，确保每次执行脚本数据环境一致。
 * 5、进行了优化，对脚本进行了分层，减少了重复代码，提高了代码复用率，并减少了维护成本。
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_04_layer {
    private static final Logger logger = LoggerFactory.getLogger(Demo_04_layer.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken(){
        accessToken= TokenHelper.getAccessToken();
        logger.info(accessToken);
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
        Response response=MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
    @DisplayName("修改成员")
    @Test
    @Order(2)
    void updateMember() {
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();

        Response response=MemberObject.updateMember(name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }

    @DisplayName("查询成员")
    @Test
    @Order(3)
    void listMember() {
        String createUserid =MemberObject.getMemberId(accessToken);
        Response response=MemberObject.selectMember(createUserid, accessToken);
        assertEquals(createUserid,response.path("userid").toString());

    }
    @DisplayName("删除成员")
    @Test
    @Order(4)
    void deleteMember() {
        String createUserid =MemberObject.getMemberId(accessToken);
        Response response = MemberObject.deletMember(createUserid, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
}
