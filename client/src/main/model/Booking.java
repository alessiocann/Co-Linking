package main.model;

import java.time.LocalDateTime;

public class Booking {
    private static int currentId = 0;
    private final int id;
    private String dateBooking;

    private LocalDateTime timestamp;
    private User user;
    private Workstation workstation;
    private Activity activity;

    public Booking(User user, Workstation workstation, String dateBooking) {
        this.id = currentId+1;
        this.timestamp = LocalDateTime.now();
        this.user = user;
        this.workstation = workstation;
        this.dateBooking = dateBooking;
    }

    public Booking(User user, Activity activity) {
        this.id = currentId+1;
        this.timestamp = LocalDateTime.now();
        this.user = user;
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public Workstation getWorkstation() {
        return workstation;
    }

    public String getDateBooking() {
        return dateBooking;
    }
}
