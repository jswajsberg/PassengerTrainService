package com.trainapp.service;

import com.trainapp.model.Train;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service class providing train schedule logic,
 * including lookup and validation based on route and travel date.
 */
public class TrainService {
    private static final List<Train> trains = new ArrayList<>();

    /**
     * Returns all available train entries.
     */
    public List<Train> getAllTrains() {
        return trains;
    }
    
    /**
     * Finds trains that match a given route and operate on the specified travel date.
     *
     * @param from Origin station
     * @param to Destination station
     * @param travelDate Travel date in yyyy-MM-dd format
     * @return List of matching Train objects
     */
    public List<Train> findTrainsByRouteAndDate(String from, String to, String travelDate) {
        List<Train> matching = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;

        // Parse travel date; return empty list on error
        try {
            date = LocalDate.parse(travelDate, formatter);
        } catch (Exception e) {
            return matching;
        }

        DayOfWeek requestedDay = date.getDayOfWeek();

        // Check if train matches route and operates on requested day
        for (Train t : trains) {
            if (t.getFrom().equalsIgnoreCase(from) && t.getTo().equalsIgnoreCase(to)) {
                String days = t.getDaysOfOperation().toLowerCase();

                if (
                    days.contains("daily") ||
                    (days.contains("weekends") && (requestedDay == DayOfWeek.SATURDAY || requestedDay == DayOfWeek.SUNDAY)) ||
                    (days.contains("mon") && requestedDay == DayOfWeek.MONDAY) ||
                    (days.contains("tue") && requestedDay == DayOfWeek.TUESDAY) ||
                    (days.contains("wed") && requestedDay == DayOfWeek.WEDNESDAY) ||
                    (days.contains("thu") && requestedDay == DayOfWeek.THURSDAY) ||
                    (days.contains("fri") && requestedDay == DayOfWeek.FRIDAY) ||
                    (days.contains("sat") && requestedDay == DayOfWeek.SATURDAY) ||
                    (days.contains("sun") && requestedDay == DayOfWeek.SUNDAY)
                ) {
                    matching.add(t);
                }
            }
        }

        return matching;
    }

    /**
     * Checks if a route exists between two stations.
     */
    public boolean isRouteValid(String from, String to) {
        return trains.stream().anyMatch(t ->
            t.getFrom().equalsIgnoreCase(from) && t.getTo().equalsIgnoreCase(to)
        );
    }

    /**
     * Checks if a route exists and is available on the given date.
     *
     * @param from Origin station
     * @param to Destination station
     * @param travelDate Date to check (yyyy-MM-dd)
     * @return true if available, false otherwise
     */
    public boolean isRouteAvailableOnDate(String from, String to, String travelDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;

        // Return false if date is not valid
        try {
            date = LocalDate.parse(travelDate, formatter);
        } catch (Exception e) {
            return false;
        }

        DayOfWeek requestedDay = date.getDayOfWeek();

        for (Train t : trains) {
            if (t.getFrom().equalsIgnoreCase(from) && t.getTo().equalsIgnoreCase(to)) {
                String days = t.getDaysOfOperation().toLowerCase();

                if (
                    days.contains("daily") ||
                    (days.contains("weekends") && (requestedDay == DayOfWeek.SATURDAY || requestedDay == DayOfWeek.SUNDAY)) ||
                    (days.contains("mon") && requestedDay == DayOfWeek.MONDAY) ||
                    (days.contains("tue") && requestedDay == DayOfWeek.TUESDAY) ||
                    (days.contains("wed") && requestedDay == DayOfWeek.WEDNESDAY) ||
                    (days.contains("thu") && requestedDay == DayOfWeek.THURSDAY) ||
                    (days.contains("fri") && requestedDay == DayOfWeek.FRIDAY) ||
                    (days.contains("sat") && requestedDay == DayOfWeek.SATURDAY) ||
                    (days.contains("sun") && requestedDay == DayOfWeek.SUNDAY)
                ) {
                    return true;
                }
            }
        }

        return false;
    }

    // Static initializer: hard-coded train data for demo/testing purposes
    static {
        trains.add(new Train("T001", "Montreal", "Quebec City", "08:00", "11:30", "Mon-Fri"));
        trains.add(new Train("T001R", "Quebec City", "Montreal", "14:30", "18:00", "Mon-Fri"));

        trains.add(new Train("T002", "Montreal", "Toronto", "06:15", "11:45", "Daily"));
        trains.add(new Train("T002R", "Toronto", "Montreal", "14:15", "19:45", "Daily"));

        trains.add(new Train("T003", "Toronto", "Ottawa", "09:15", "12:45", "Daily"));
        trains.add(new Train("T003R", "Ottawa", "Toronto", "15:00", "18:30", "Daily"));

        trains.add(new Train("T004", "Winnipeg", "Regina", "07:45", "12:15", "Tue, Thu, Sat"));
        trains.add(new Train("T004R", "Regina", "Winnipeg", "14:00", "18:30", "Tue, Thu, Sat"));
        
        trains.add(new Train("T005", "Vancouver", "Kelowna", "07:00", "10:30", "Weekends"));
        trains.add(new Train("T005R", "Kelowna", "Vancouver", "15:00", "18:30", "Weekends"));

        trains.add(new Train("T006", "Edmonton", "Calgary", "06:30", "09:45", "Mon-Sat"));
        trains.add(new Train("T006R", "Calgary", "Edmonton", "16:30", "19:45", "Mon-Sat"));

        trains.add(new Train("T007", "Halifax", "Moncton", "10:00", "13:15", "Mon, Wed, Fri"));
        trains.add(new Train("T007R", "Moncton", "Halifax", "15:00", "18:15", "Mon, Wed, Fri"));

        trains.add(new Train("T008", "Montreal", "Ottawa", "06:15", "08:15", "Daily"));
        trains.add(new Train("T008R", "Ottawa", "Montreal", "17:00", "19:00", "Daily"));
    }
}