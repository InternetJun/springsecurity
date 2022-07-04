package com.example.handle;

import com.example.endpoint.Tokenpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MySuccessHandler {
    // 登录成功的处理逻辑
    @Autowired
    private Tokenpoint tokenpoint;
}
