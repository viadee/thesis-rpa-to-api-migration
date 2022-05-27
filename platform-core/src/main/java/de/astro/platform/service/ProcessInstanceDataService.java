package de.astro.platform.service;

import de.astro.platform.web.dto.ProcessInstanceDataDto;

import java.util.List;

public interface ProcessInstanceDataService {
    List<ProcessInstanceDataDto> getInstanceData(String processDefinitionId);
}
