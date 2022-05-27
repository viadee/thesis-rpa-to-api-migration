package org.example.service;

import org.example.controller.dtos.CreateVarMappingDto;
import org.example.data.model.VarMapping;
import org.example.data.repo.ApiTopicRepo;
import org.example.data.repo.VarMappingRepo;
import org.example.worker.GenericApiWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarMappingServiceImpl implements VarMappingService {

    @Autowired
    private VarMappingRepo varMappingRepo;

    @Autowired
    private ApiTopicRepo apiTopicRepo;

    @Autowired
    private GenericApiWorker genericApiWorker;

    @Override
    public List<VarMapping> getAllMappings(){
        return varMappingRepo.findAll();
    }

    @Override
    public List<VarMapping> getMappingOfTopic(String topic) {
        return varMappingRepo.findVarMappingsByTopic(topic);
    }

    @Override
    public void create (CreateVarMappingDto createVarMappingDto){
        VarMapping varMapping = new VarMapping();
        varMapping.setApiTopic(apiTopicRepo.getById(createVarMappingDto.getApiTopicId()));
        varMapping.setApiVar(createVarMappingDto.getApiVar());
        varMapping.setBotVar(createVarMappingDto.getBotVar());
        varMapping.setVarType(createVarMappingDto.getVarType());
        varMappingRepo.save(varMapping);
    }

    @Override
    public void deleteByApiTopic(String apiTopic){
        List<VarMapping> varMappings =
        varMappingRepo.findVarMappingsByTopic(apiTopic);
        varMappingRepo.deleteAll(varMappings);
        apiTopicRepo.delete(apiTopicRepo.getById(apiTopic));
        genericApiWorker.restart();
    }
}
