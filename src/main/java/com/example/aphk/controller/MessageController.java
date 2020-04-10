package com.example.aphk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    public List<Map<String, String>> messages = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String, String>(){{put("id", "1"); put("text" , "message_1");}});
        add(new HashMap<String, String>(){{put("id", "2"); put("text" , "message_2");}});
        add(new HashMap<String, String>(){{put("id", "3"); put("text" , "message_3");}});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    @GetMapping("id")
    public Map<String, String> ()
}
