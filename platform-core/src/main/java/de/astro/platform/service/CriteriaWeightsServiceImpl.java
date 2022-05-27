package de.astro.platform.service;

import de.astro.platform.data.model.CriteriaWeights;
import de.astro.platform.data.repo.CriteriaWeightsRepository;
import de.astro.platform.web.dto.CriteriaWeightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaWeightsServiceImpl implements CriteriaWeightsService {
    @Autowired
    private CriteriaWeightsRepository criteriaWeightsRepository;

    @Override
    public CriteriaWeights findCriteriaWeightsByID(long id){
        return criteriaWeightsRepository.findById(id).orElse(null);
    }

    @Override
    public List<CriteriaWeights> getAllCriteriaWeights(){
        return criteriaWeightsRepository.findAll();
    }

    @Override
    public void create(CriteriaWeightsDto criteriaWeights){
        CriteriaWeights weighing = new CriteriaWeights();
        weighing.setId(criteriaWeights.getId());
        weighing.setFrontendStabilityWeight(criteriaWeights.getFrontendStabilityWeight());
        weighing.setEndOfLifeWeight(criteriaWeights.getEndOfLifeWeight());
        weighing.setCustomerWaitingTimeWeight(criteriaWeights.getCustomerWaitingTimeWeight());
        weighing.setAutomationRateWeight(criteriaWeights.getAutomationRateWeight());
        weighing.setQualityOfResultsWeight(criteriaWeights.getQualityOfResultsWeight());
        weighing.setNumberOfBotRunnersWeight(criteriaWeights.getNumberOfBotRunnersWeight());
        weighing.setExecutionFrequencyWeight(criteriaWeights.getExecutionFrequencyWeight());
        weighing.setNumberOfSystemsWeight(criteriaWeights.getNumberOfSystemsWeight());
        weighing.setBusinessImpactWeight(criteriaWeights.getBusinessImpactWeight());
        weighing.setRegulatoryComplianceWeight(criteriaWeights.getRegulatoryComplianceWeight());
        weighing.setFrequencyOfReuseWeight(criteriaWeights.getFrequencyOfReuseWeight());
        criteriaWeightsRepository.save(weighing);
    }


}
