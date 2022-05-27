package de.astro.platform.web.controller;

import de.astro.platform.service.ExternalTaskService;
import de.astro.platform.service.RPABotService;
import de.astro.platform.web.dto.CreateRPABotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path(value = "/rpa")
public class RPABotRestController {
    @Autowired
    private RPABotService rpaBotService;
    @Autowired
    private ExternalTaskService externalTaskService;

    @POST
    @Path(value = "/create")
    @Produces("application/json")
    public Response createRPABot(@RequestBody CreateRPABotDto createRPABotDto) {
        try {
            rpaBotService.create(createRPABotDto);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

    @GET
    @Path(value = "/getBots")
    @Produces("application/json")
    public Response getProcesses() {
        try {
            return Response.ok(rpaBotService.getAllRobots()).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

    @GET
    @Path(value = "/getRPATasksInModels")
    @Produces("application/json")
    public Response getInstanceData(){
        try {
            return Response.ok(externalTaskService.getExternTasks()).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }
}
