package com.blog.controller.user.restController;

import com.blog.utils.HttpUtils;
import com.blog.utils.QrUtils;
import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class wxlogin {
    //获取授权码的url地址
    private static final String CODEURL=" https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
    //APPID
    private static final String APPID="wx3452a83ad6c9daf0";
    //AppSecret
    private static  final String AppSecret= "5d33c72b7001b59cefa60c2c5b1f7fc1";
    //REDIRECT_URI  扫码点击确认成功后回调的url地址
    private static final String REDIRECT_URI="http://ccx.my_blog/callback";

    //生成二维码
    @RequestMapping("/wxlogin")
    public void getWxlogin(HttpServletResponse response) throws IOException, WriterException {
        String codeUrl =CODEURL.replace("APPID",APPID).replace("REDIRECT_URI", REDIRECT_URI);
        //通过 getURL方法获取地址
        BufferedImage ur = QrUtils.getBufferedImage(codeUrl);
        QrUtils.output(ur, response);
}

    /**
     * 扫码成功之后自动调用这个接口，通过code获取access_token
     * @param code
     * @return
     */
    @RequestMapping("/callback")
    public String wxCallBack(String code){
        String Access_tokenURL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String replace = Access_tokenURL.replace("APPID", APPID).replace("SECRET", AppSecret).replace("CODE", code);
         //返回的json字符串里包含了 access_token和openid
        String httpResource = HttpUtils.getHttpResource(replace);
         //todo 取出 httpResource中的access_token和openid
        //返回的对象里包含了用户信息 和唯一标识unionid
         Object unionID = getUnionID("1", "2");
         //todo 取出用户信息 和唯一标识unionid并保存
        return "redirect:http://localhost:8082/callback.html?"+code;
    }

    /**
     *   通过以获得的token和openid获取用户信息和唯一授权码
     * @param accessToken
     * @param openid
     * @return
     */
    private Object getUnionID(String accessToken, String openid){
         String url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
         String replace = url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
        //获取用户信息json字符串
        return HttpUtils.getHttpResource(replace);
    }

}
