package de.astro.platform.data.repo;

import de.astro.platform.data.model.RPABot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RPABotRepository extends JpaRepository<RPABot, String> {
    List<RPABot> findAll();
}