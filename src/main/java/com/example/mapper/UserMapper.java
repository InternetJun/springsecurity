package com.example.mapper;

import com.example.pojo.Role;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**最基本的根据接口来写的sql要怎么设置呢？还有的是基本的不同是什么你？*/

    List<Role> getUserRolesById(@Param("id") String id);

    User loadUserByUsername(@Param("username") String username);
}
