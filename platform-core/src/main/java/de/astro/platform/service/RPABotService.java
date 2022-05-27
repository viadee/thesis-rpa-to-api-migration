package de.astro.platform.service;

import de.astro.platform.data.model.RPABot;
import de.astro.platform.web.dto.CreateRPABotDto;

import java.util.List;
import java.util.Optional;

public interface RPABotService {
    RPABot findRobotByTopic(String topic);
    List<RPABot> getAllRobots();
    void create(CreateRPABotDto toStore);
}