package com.example.controller;

import com.example.util.exception.BusinessException;
import com.example.util.exception.ErrorEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestContoller {

    @RequestMapping("query")
    @ResponseBody
    public Object query() {
        throw new BusinessException(ErrorEnum.NO_PERMISSION.getErrorCode(), ErrorEnum.NO_PERMISSION.getErrorMsg());
    }
}
