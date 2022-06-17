package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.model.RespBean;
import com.example.pojo.User;
import com.example.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {


    @Override
    public RespBean register(User user) {
        String password = user.getPassword();
        String username = user.getUsername();
        //首先寻找username；
        User entity = new User();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User username1 = entity.selectOne(userQueryWrapper.eq("username", username));
        if ( > 0) {

        }
    }
}
