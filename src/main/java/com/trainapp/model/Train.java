package com.trainapp.model;

public class Train {
    private String trainId;
    private String from;
    private String to;
    private String departureTime;
    private String arrivalTime;
    private String daysOfOperation;

    public Train() {}

    public Train(String trainId, String from, String to, String departureTime, String arrivalTime, String daysOfOperation) {
        this.trainId = trainId;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.daysOfOperation = daysOfOperation;
    }

    public String getTrainId() { return trainId; }
    public void setTrainId(String trainId) { this.trainId = trainId; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDaysOfOperation() { return daysOfOperation; }
    public void setDaysOfOperation(String daysOfOperation) { this.daysOfOperation = daysOfOperation; }
}