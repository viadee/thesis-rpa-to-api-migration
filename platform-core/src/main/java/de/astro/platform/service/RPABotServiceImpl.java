package de.astro.platform.service;

import de.astro.platform.data.model.RPABot;
import de.astro.platform.data.repo.RPABotRepository;
import de.astro.platform.web.dto.CreateRPABotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RPABotServiceImpl implements RPABotService{
    @Autowired
    private RPABotRepository rpaBotRepository;

    @Override
    public RPABot findRobotByTopic(String topic) {
        return rpaBotRepository.findById(topic).orElse(null);
    }

    @Override
    public List<RPABot> getAllRobots(){
        return rpaBotRepository.findAll();
    }

    @Override
    public void create(CreateRPABotDto toStore){
        RPABot rpaBot = new RPABot();
        rpaBot.setTopic(toStore.getTopic());
        rpaBot.setFrontendStability(toStore.getFrontendStability());
        rpaBot.setEndOfLife(toStore.getEndOfLife());
        rpaBot.setQualityOfResults(toStore.getQualityOfResults());
        rpaBot.setNumberOfBotRunners(toStore.getNumberOfBotRunners());
        rpaBot.setBusinessImpact(toStore.getBusinessImpact());
        rpaBot.setNumberOfSystems(toStore.getNumberOfSystems());
        rpaBot.setRegulatoryCompliance(toStore.getRegulatoryCompliance());
        rpaBotRepository.save(rpaBot);
    }

}