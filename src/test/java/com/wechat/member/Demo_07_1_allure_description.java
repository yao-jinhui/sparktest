package com.wechat.member;

import com.utils.FakerUtils;
import com.wechat.apiobject.DepartMentObject;
import com.wechat.apiobject.MemberObject;
import com.wechat.apiobject.TokenHelper;
import com.wechat.task.EvnHelperTask;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * allure2注解举例
 */
@Epic("Epic企业微信接口测试用例")
@Feature("Feature成员相关功能测试")
public class Demo_07_1_allure_description {
    private static final Logger logger = LoggerFactory.getLogger(Demo_07_1_allure_description.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken() throws Exception {
        accessToken = TokenHelper.getAccessToken();
        logger.info(accessToken);

    }

    @Description("Description这个测试方法会测试创建成员的功能-入参数据驱动")
    @Story("Story创建成员测试")
    @DisplayName("DisplayName创建成员")
    @ParameterizedTest
    @CsvFileSource(resources = "/data/createMember.csv", numLinesToSkip = 1)
    void createMember(String userid, String name, String mobile,int gender,String address) {
        Response response=MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @Description("Description这个测试方法会测试修改成员的功能")
    @Story("Story修改成员测试")
    @DisplayName("DisplayName修改成员")
    @Test
    void updateMember() {
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();

        Response response=MemberObject.updateMember(name, mobile, gender, address, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }
    @DisplayName("DisplayName查询成员")
    @Description("Description这个测试方法会测试查询成员的功能")
    @Story("Story查询成员测试")
    @Test
    void listMember() {
        String userid = "userid"+ FakerUtils.getTimeStamp();
        String name =  FakerUtils.getChineseName();
        String mobile = FakerUtils.getTel();
        int gender = FakerUtils.getNum(1,2);
        String address = FakerUtils.getRoad();
        Response creatResponse=MemberObject.creatMember(userid, name, mobile, gender, address, accessToken);
        Response selectResponse=MemberObject.selectMember(userid, accessToken);

        assertAll("查询返回值校验！",
                ()->assertEquals("1",selectResponse.path("errcode").toString()),
                ()->assertEquals(userid+"x",selectResponse.path("userid").toString()),
                ()->assertEquals(mobile+"x",selectResponse.path("mobile").toString()),
                ()->assertEquals(String.valueOf(gender)+"x",selectResponse.path("gender").toString()),
                ()->assertEquals(address+"x",selectResponse.path("address").toString())
        );
    }
    @DisplayName("DisplayName删除成员")
    @Description("Description这个测试方法会测试删除成员的功能")
    @Story("Story删除成员测试")
    @Test
    void deleteMember() {
        String createUserid =MemberObject.getMemberId(accessToken);
        Response response = MemberObject.deletMember(createUserid, accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
}
