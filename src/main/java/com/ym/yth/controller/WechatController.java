package com.ym.yth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/wechat")
public class WechatController {
    /**
     * 微信开放平台二维码连接
     */
    private final static String OPEN_QRCODE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    /**
     * 开放平台回调url
     * 注意：test16web.tunnel.qydev.com 域名地址要和在微信端 回调域名配置 地址一直，否则会报回调地址参数错误
     */
    private final static String OPEN_REDIRECT_URL = "http://test16web.tunnel.qydev.com/pub/api/v1/wechat/user/callback1";

    /**
     * 微信审核通过后的appid
     */
    private final static String OPEN_APPID = "wx0255752c69a2d5b";


    /**
     * 拼装微信扫一扫登录url
     */
    @GetMapping("login_url")
    @ResponseBody
    public String loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) throws UnsupportedEncodingException {

        //官方文档说明需要进行编码
        String callbackUrl = URLEncoder.encode(OPEN_REDIRECT_URL, "GBK"); //进行编码

        //格式化，返回拼接后的url，去调微信的二维码
        String qrcodeUrl = String.format(OPEN_QRCODE_URL, OPEN_APPID, callbackUrl, accessPage);

        return qrcodeUrl;
    }


}
