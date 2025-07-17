package com.trainapp.resource;

import com.trainapp.model.Booking;
import com.trainapp.service.BookingService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

/**
 * REST resource class for managing bookings.
 * Provides end points to create, retrieve, list, cancel, and view bookings in HTML.
 */
@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    private final BookingService service = new BookingService();

    /**
     * POST /bookings
     * Create a new booking.
     * Returns 201 if created, or 400 if the input is invalid (e.g., no route/train available).
     */
    @POST
    public Response createBooking(Booking booking) {
        try {
            Booking created = service.createBooking(booking);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * GET /bookings/{id}
     * Retrieve a specific booking by ID.
     * Returns 200 and the booking if found, or 404 if not.
     */
    @GET
    @Path("/{id}")
    public Response getBooking(@PathParam("id") String id) {
        Booking found = service.getBooking(id);
        if (found == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Booking not found")
                           .build();
        }
        return Response.ok(found).build();
    }

    /**
     * GET /bookings/html/{id}
     * Return booking info as an HTML page (for testing/debugging purposes).
     */
    @GET
    @Path("/html/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getBookingHtml(@PathParam("id") String id) {
        Booking booking = service.getBooking(id);
        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("<html><body><h2>Booking not found</h2></body></html>")
                           .build();
        }

        String html = "<html><body>" +
            "<h2>Booking Confirmation</h2>" +
            "<p><strong>Booking ID:</strong> " + booking.getBookingId() + "</p>" +
            "<p><strong>Name:</strong> " + booking.getName() + "</p>" +
            "<p><strong>Email:</strong> " + booking.getEmail() + "</p>" +
            "<p><strong>From:</strong> " + booking.getFrom() + "</p>" +
            "<p><strong>To:</strong> " + booking.getTo() + "</p>" +
            "<p><strong>Travel Date:</strong> " + booking.getTravelDate() + "</p>" +
            "</body></html>";

        return Response.ok(html).build();
    }

    /**
     * GET /bookings/all
     * Retrieve all bookings currently stored in memory.
     */
    @GET
    @Path("/all")
    public Response getAllBookings() {
        Collection<Booking> all = service.getAllBookings();
        return Response.ok(all).build();
    }

    /**
     * DELETE /bookings/{id}
     * Cancel (delete) a booking by ID.
     * Returns 200 if successfully deleted, or 404 if not found.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN) // Override to return plain message instead of JSON
    public Response cancelBooking(@PathParam("id") String id) {
        boolean removed = service.cancelBooking(id);
        if (removed) {
            return Response.ok("Booking cancelled").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Booking not found")
                           .build();
        }
    }
}