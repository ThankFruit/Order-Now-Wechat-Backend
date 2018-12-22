package com.tkft.sell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther xinyuan
 * @Date 11/21/18 5:12 PM
 **/
@RestController
@RequestMapping("/weixin")
public class WeixinController {
//    手动获取
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        System.out.println("进入auth方法" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxaaa66e5374bfb728&secret=95168057485a808ef735f34dcc10cb61&code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        System.out.print("进入auth方法" + code);
    }

}
