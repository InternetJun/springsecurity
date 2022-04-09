package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role")
public class Role implements Serializable {
    private String id;

    private String name;

    private String nameZh;

}
