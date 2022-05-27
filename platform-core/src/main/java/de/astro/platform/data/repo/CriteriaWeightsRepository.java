package de.astro.platform.data.repo;

import de.astro.platform.data.model.CriteriaWeights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriteriaWeightsRepository extends JpaRepository<CriteriaWeights, Long> {
    List<CriteriaWeights> findAll();
}