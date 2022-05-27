package org.example.service;

import org.example.controller.dtos.CreateVarMappingDto;
import org.example.data.model.VarMapping;

import java.util.List;

public interface VarMappingService {
    List<VarMapping> getAllMappings();
    List<VarMapping> getMappingOfTopic(String topic);
    void create (CreateVarMappingDto createVarMappingDto);
    void deleteByApiTopic(String apiTopic);
}
