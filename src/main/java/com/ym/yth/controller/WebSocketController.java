package com.ym.yth.controller;

import com.ym.yth.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/socket")
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @RequestMapping("/push/{cid}")
    public void pushToWeb(@PathVariable String cid) {
        try {
            WebSocketService.sendInfo("微信授权成功", cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/one")
    public void pushToWebOne( String msg) {
        try {
            webSocketService.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
