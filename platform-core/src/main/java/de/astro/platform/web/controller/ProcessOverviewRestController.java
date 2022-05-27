package de.astro.platform.web.controller;

import de.astro.platform.service.ProcessOverviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path(value = "/processes")
public class ProcessOverviewRestController {

    @Autowired
    private ProcessOverviewService processOverviewService;

    @GET
    @Path(value = "/getProcesses")
    @Produces("application/json")
    public Response getProcesses() {
        try {
            return Response.ok(processOverviewService.getOverviewOfProcesses()).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path(value="/deleteProcessDefinitionsByKey")
    @Produces("application/json")
    public Response deleteProcessDefinitionsByKey(@QueryParam("processKey") String processKey){
        try {
            return Response.ok(processOverviewService.deleteProcessDefinitionByKey(processKey)).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

}
