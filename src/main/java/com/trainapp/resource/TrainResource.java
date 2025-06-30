package com.trainapp.resource;

import com.trainapp.model.Train;
import com.trainapp.service.TrainService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * REST resource for accessing train schedule information.
 * Includes end points to list all trains and search by route/date.
 */
@Path("/trains")
@Produces(MediaType.APPLICATION_JSON)
public class TrainResource {

    private final TrainService service = new TrainService();

    /**
     * GET /trains
     * Returns the full list of all available trains.
     * Useful for frontend dropdowns, admin views, or testing.
     */
    @GET
    public Response getAllTrains() {
        List<Train> trainList = service.getAllTrains();
        return Response.ok(trainList).build();
    }

    /**
     * GET /trains/search?from=...&to=...&date=...
     * Search for trains that match a specific route and date.
     * Returns 400 if any parameter is missing.
     * Returns 404 if no matching train is found.
     */
    @GET
    @Path("/search")
    public Response searchTrains(@QueryParam("from") String from,
                                 @QueryParam("to") String to,
                                 @QueryParam("date") String date) {
        if (from == null || to == null || date == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Missing required query parameters: from, to, and date")
                           .build();
        }

        List<Train> results = service.findTrainsByRouteAndDate(from, to, date);

        if (results.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No trains found for the selected route and date")
                           .build();
        }

        return Response.ok(results).build();
    }
}