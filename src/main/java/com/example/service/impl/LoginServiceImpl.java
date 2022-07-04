package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.UserMapper;
import com.example.model.RespBean;
import com.example.pojo.User;
import com.example.service.LoginService;
import com.example.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public RespBean register(User user) {
        String password = user.getPassword();
        String username = user.getUsername();
        //首先寻找username；
        User entity = new User();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User exist = entity.selectOne(userQueryWrapper.eq("username", username));
        if (exist != null) {
            return RespBean.error("此用户名已经存在！");
        } else {
            entity.setId(UUID.randomUUID().toString());
            entity.setImage(user.getImage());
            LocalDateTime now = LocalDateTime.now();
            entity.setAddTime(now);
            //加密密码数据
            entity.setUsername(username);
            entity.setPassword(passwordEncoder.encode(password));
            //注册一律设置为测试角色；
            entity.setRid("13");
            entity.setEnabled(true);
            entity.setEmail(user.getEmail());
            entity.setPhone(user.getPhone());
            userMapper.insertUser(entity);
        }
        return RespBean.ok("注册成功");
    }
}
