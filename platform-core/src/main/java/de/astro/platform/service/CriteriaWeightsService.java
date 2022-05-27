package de.astro.platform.service;

import de.astro.platform.data.model.CriteriaWeights;
import de.astro.platform.web.dto.CriteriaWeightsDto;

import java.util.List;

public interface CriteriaWeightsService {
    CriteriaWeights findCriteriaWeightsByID(long id);
    List<CriteriaWeights> getAllCriteriaWeights();
    void create(CriteriaWeightsDto criteriaWeights);
}
