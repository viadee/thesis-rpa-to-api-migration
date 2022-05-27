package org.example.service;

import org.example.controller.dtos.CreateApiTopicDto;
import org.example.data.model.ApiTopic;

import java.util.List;

public interface ApiTopicService {
    List<ApiTopic> getAllApiTopics();
    void create(CreateApiTopicDto createApiTopicDto);
}