package org.example.controller;

import org.example.data.model.VarMapping;
import org.example.worker.GenericApiWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-worker")
public class GenericApiWorkerRestController {
    @Autowired
    private GenericApiWorker genericApiWorker;

    @GetMapping("/restart")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllMappings(){
        try {
            genericApiWorker.restart();
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
