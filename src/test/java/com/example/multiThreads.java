package com.example;

import com.example.controller.UserController;
import io.swagger.models.auth.In;
import org.jctools.queues.MpscArrayQueue;
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
public class multiThreads {
/*
* @主要是为了测试高性能的多线程工具类啊很牛掰的实现的！
* */

    @Test
    public void mpscqueueTest() {

        MpscArrayQueue<Integer> mpscArrayQueue = new MpscArrayQueue(10);
        boolean offer = mpscArrayQueue.offer(1);


    }
}
