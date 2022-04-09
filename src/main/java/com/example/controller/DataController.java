package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/insertData")
    public void insertStudent() {

    }
}
