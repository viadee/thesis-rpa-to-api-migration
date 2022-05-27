package de.astro.platform.web.controller;

import de.astro.platform.service.CriteriaWeightsService;
import de.astro.platform.web.dto.CriteriaWeightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path(value="criteria")
public class CriteriaWeightsRestController {
    @Autowired
    private CriteriaWeightsService criteriaWeightsService;

    @POST
    @Path(value = "/create")
    @Produces("application/json")
    public Response createWeighing(@RequestBody CriteriaWeightsDto criteriaWeightsDto) {
        try {
            criteriaWeightsService.create(criteriaWeightsDto);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

    @GET
    @Path(value = "/getAllWeights")
    @Produces("application/json")
    public Response getProcesses() {
        try {
            return Response.ok(criteriaWeightsService.getAllCriteriaWeights()).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }

    @GET
    @Path(value = "/getWeightsByID")
    @Produces("application/json")
    public Response getInstanceData(@QueryParam("id") long id){
        try {
            return Response.ok(criteriaWeightsService.findCriteriaWeightsByID(id)).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    }
}
