package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.model.RespBean;
import com.example.pojo.Role;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
//    @Autowired
//    // 存放权限
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询数据库用户名和密码
        //密码解析；自定义的user和passwd怎么去做呢？还有自定义的页面和拦截器！必须先登录啊。
        /**要的是自己初始化密码的操作*/
//        if (!"admin".equals(username)) {
//            throw new UsernameNotFoundException("user is not exist");
//        }fPasswordEncoder
//        String passWord = pw.encode("123");
//        return new User(username, passWord, AuthorityUtils.commaSeparatedStringToAuthorityList("admin, normal"));
        /**@对密码和用户查数据库的操作、
         **/
        User user = userMapper.loadUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 存放权限
        List<Role> userRolesById = userMapper.getUserRolesById(user.getId());
        user.setRoles(userRolesById);
        return user;
//        return new org.springframework.security.core.userdetails
//                .User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()), user.getAuthorities());
    }
}
