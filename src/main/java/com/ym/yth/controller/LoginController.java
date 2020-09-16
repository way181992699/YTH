package com.ym.yth.controller;

import com.ym.yth.util.JSONResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wkx
 * @description 用户登录
 * @date 2020/4/8
 */
@RestController
@RequestMapping("user/login")
public class LoginController {

    @PostMapping("/in")
    public JSONResult login(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "pwd", required = false) String pwd) {
        return JSONResult.errorMsg("登录失败,账号密码有误");
    }

    @PostMapping("/post")
    public JSONResult login1() {

        final String filePath = "D:";
        final String fileName = "test.docx";
        final String url = "http://10.153.114.136:21117/doc/test";

        RestTemplate restTemplate = new RestTemplate();

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(filePath + "/" + fileName);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        form.add("filename", fileName);

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        String s = restTemplate.postForObject(url, files, String.class);
        System.out.println(s);

        return JSONResult.errorMsg("登录失败,账号密码有误");
    }

}
