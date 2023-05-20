package hu.webuni.logistics.dto;

import hu.webuni.logistics.model.Address;

import java.time.LocalDateTime;


public class MilestoneDto {

    private long id;

    private Address address;
    private LocalDateTime plannedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }
}
