package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
@Api(description = "用户操作类")
public class UserController {
    @Autowired
    private UserMapper userMapper;

//    @ApiOperation(value = "", notes = "获取用户信息")
//    @RequestMapping(value = "/getUser", method= RequestMethod.GET)
//    public JSONObject getUserInfo(@RequestParam(value = "名字", required =true)String username) {
//        JSONObject jsonObject = new JSONObject();
//
//        User user = userMapper.selectOne();
//        jsonObject.put("result", user);
//        jsonObject.put("status", 200);
//        return jsonObject;
//    }

    @ApiOperation(value = "", notes = "获取用户信息")
    @RequestMapping(value = "/addUser", method= RequestMethod.POST)
    public JSONObject addUser(@RequestParam(value = "名字", required =true)String username,
                              @RequestParam(value = "密码", required =true)String passwd) {
        JSONObject jsonObject = new JSONObject();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(passwd);
        jsonObject.put("result", user);
        jsonObject.put("status", 200);
        return jsonObject;
    }

    @ApiOperation(value = "", notes = "获取用户信息")
    @RequestMapping(value = "/getUser", method= RequestMethod.POST)
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
