package org.example.controller;

import org.example.data.model.ApiTopic;
import org.example.service.ApiTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-topics")
public class ApiTopicRestController {
    @Autowired
    private ApiTopicService apiTopicService;

    @GetMapping("/getTopics")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ApiTopic>> getAllApiTopics(){
        try {
            return ResponseEntity.ok(apiTopicService.getAllApiTopics());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
