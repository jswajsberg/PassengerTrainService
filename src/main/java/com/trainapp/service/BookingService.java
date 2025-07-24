package com.trainapp.service;

import com.trainapp.model.Booking;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing train ticket bookings.
 * Handles creation, retrieval, cancellation, and storage of bookings.
 */
public class BookingService {
    // Thread-safe map to store bookings by ID
    private static final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    // Reference to train service singleton to validate route and date
    private final TrainService trainService = TrainService.getInstance();

    /**
     * Creates a new booking if the route and travel date are valid.
     *
     * @param bookingRequest Booking details from user
     * @return Newly created Booking object with generated ID
     * @throws IllegalArgumentException if route or date is invalid
     */
    public Booking createBooking(Booking bookingRequest) {
        // Validate if route exists
        if (!trainService.isRouteValid(bookingRequest.getFrom(), bookingRequest.getTo())) {
            throw new IllegalArgumentException("No train available for this route");
        }

        // Validate if train runs on selected date
        if (!trainService.isRouteAvailableOnDate(
                bookingRequest.getFrom(),
                bookingRequest.getTo(),
                bookingRequest.getTravelDate())) {
            throw new IllegalArgumentException("No train available for this route on the selected date");
        }

        // Generate unique ID for booking
        String id = UUID.randomUUID().toString();

        // Use provided trainId from the request
        String trainId = bookingRequest.getTrainId();

        // Create and store booking
        Booking newBooking = new Booking(
                id,
                bookingRequest.getName(),
                bookingRequest.getEmail(),
                bookingRequest.getFrom(),
                bookingRequest.getTo(),
                bookingRequest.getTravelDate(),
                trainId
        );
        bookings.put(id, newBooking);
        return newBooking;
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId ID of the booking
     * @return Booking object or null if not found
     */
    public Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    /**
     * Cancels a booking by its ID.
     *
     * @param bookingId ID of the booking to cancel
     * @return true if successfully removed, false if not found
     */
    public boolean cancelBooking(String bookingId) {
        return bookings.remove(bookingId) != null;
    }

    /**
     * Returns all current bookings.
     * Made non-static to follow proper encapsulation principles.
     */
    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }

    // Static block to add sample demo bookings for testing
    static {
        Booking demo1 = new Booking(
                UUID.randomUUID().toString(),
                "Alice Tremblay",
                "alice@example.ca",
                "Montreal",
                "Quebec City",
                "2025-06-01",
                "T001"
        );
        Booking demo2 = new Booking(
                UUID.randomUUID().toString(),
                "John Singh",
                "john@example.ca",
                "Toronto",
                "Ottawa",
                "2025-06-10",
                "T003"
        );
        bookings.put(demo1.getBookingId(), demo1);
        bookings.put(demo2.getBookingId(), demo2);
    }
}
