package de.astro.platform.web.controller;

import de.astro.platform.service.ProcessInstanceDataService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path(value = "/instances")
public class ProcessInstanceDataRestController {

    @Autowired
    private ProcessInstanceDataService processInstanceDataService;

    @GET
    @Path(value = "/getInstanceData")
    @Produces("application/json")
    public Response getInstanceData(@QueryParam("processDefinitionId") String processDefinitionId){
        try {
            return Response.ok(processInstanceDataService.getInstanceData(processDefinitionId)).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

}
