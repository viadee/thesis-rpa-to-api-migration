package org.example.service;

import org.example.controller.dtos.CreateApiTopicDto;
import org.example.data.model.ApiTopic;
import org.example.data.repo.ApiTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiTopicServiceImpl implements ApiTopicService{

    @Autowired
    private ApiTopicRepo apiTopicRepo;

    @Override
    public List<ApiTopic> getAllApiTopics(){
        return apiTopicRepo.findAll();
    }

    @Override
    public void create(CreateApiTopicDto createApiTopicDto){
        if(!(apiTopicRepo.existsById(createApiTopicDto.getId()))){
            ApiTopic apiTopic = new ApiTopic();
            apiTopic.setId(createApiTopicDto.getId());
            apiTopic.setRestEndpoint(createApiTopicDto.getRestEndpoint());
            apiTopicRepo.save(apiTopic);
        }
    }
}
