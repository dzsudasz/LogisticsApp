package hu.webuni.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class Milestone {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Address address;
    private LocalDateTime plannedTime;

    public Milestone(Address address, LocalDateTime plannedTime) {
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public Milestone() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Milestone milestone)) return false;

        if (id != milestone.id) return false;
        if (!address.equals(milestone.address)) return false;
        return plannedTime.equals(milestone.plannedTime);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + address.hashCode();
        result = 31 * result + plannedTime.hashCode();
        return result;
    }
}
