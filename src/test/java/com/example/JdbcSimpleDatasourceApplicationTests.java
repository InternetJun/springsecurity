package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcSimpleDatasourceApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    UserController userController;

    @Test
    public void springDataSourceTest() {
        //输出为true
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from user_info";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            System.out.println(list.size());
            statement.close();
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

//    @Test
//    public void testUser() {
//        JSONObject liuyifei = userController.getUserInfo("liuyifei");
//        System.out.println(liuyifei);
//    }
//}