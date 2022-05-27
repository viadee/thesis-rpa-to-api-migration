package org.example.controller;

import org.example.controller.dtos.CreateApiTopicDto;
import org.example.controller.dtos.CreateVarMappingDto;
import org.example.data.model.VarMapping;
import org.example.service.ApiTopicService;
import org.example.service.VarMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/var-mappings")
public class VarMappingRestController {
    @Autowired
    private VarMappingService varMappingService;

    @Autowired
    private ApiTopicService apiTopicService;

    @GetMapping("/getMappings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VarMapping>> getAllMappings(){
        try {
            return ResponseEntity.ok(varMappingService.getAllMappings());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getMapping")
    public ResponseEntity<List<VarMapping>> getMappingOfTopic(@RequestParam("topic") final String topic){
        try {
            return ResponseEntity.ok(varMappingService.getMappingOfTopic(topic));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity create(@RequestBody CreateVarMappingDto createVarMappingDto){
        try {
            CreateApiTopicDto createApiTopicDto = new CreateApiTopicDto();
            createApiTopicDto.setId(createVarMappingDto.getApiTopicId());
            createApiTopicDto.setRestEndpoint(createVarMappingDto.getRestEndpoint());
            apiTopicService.create(createApiTopicDto);
            varMappingService.create(createVarMappingDto);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteByTopic(@RequestParam(value="topic") String topic){
        try{
            varMappingService.deleteByApiTopic(topic);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
