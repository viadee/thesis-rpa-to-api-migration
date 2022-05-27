package de.astro.platform.service;

import de.astro.platform.web.dto.ProcessOverviewDto;

import java.util.List;

public interface ProcessOverviewService {
    List<ProcessOverviewDto> getOverviewOfProcesses();
    List<String> deleteProcessDefinitionByKey(String processKey);
}
