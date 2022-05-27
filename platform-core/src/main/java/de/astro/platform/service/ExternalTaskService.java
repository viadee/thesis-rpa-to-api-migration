package de.astro.platform.service;

import de.astro.platform.web.dto.ExternalTaskDto;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface ExternalTaskService {
    List<ExternalTaskDto> getExternTasks() throws ParserConfigurationException, IOException, SAXException;
}
