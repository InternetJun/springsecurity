package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@TableName("sys_user_role")
@Data
public class UserRole extends Model<UserRole> {
    private Integer id;

    private String userid;

    private Integer roleid;


}
