package com.trainapp.model;

public class Booking {
    private String bookingId;
    private String name;
    private String email;
    private String from;
    private String to;
    private String travelDate;
    private String trainId;

    public Booking() {}

    public Booking(String bookingId, String name, String email, String from, String to, String travelDate, String trainId) {
        this.bookingId = bookingId;
        this.name = name;
        this.email = email;
        this.from = from;
        this.to = to;
        this.travelDate = travelDate;
        this.trainId = trainId;
    }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    public String getTrainId() { return trainId; }
    public void setTrainId(String trainId) { this.trainId = trainId; } // ← String setter
}
