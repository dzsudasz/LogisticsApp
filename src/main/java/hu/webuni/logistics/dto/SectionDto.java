package hu.webuni.logistics.dto;

import hu.webuni.logistics.model.Milestone;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

public class SectionDto {

    private long id;
    private Milestone fromMilestone;
    private Milestone toMilestone;
    private int number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Milestone getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(Milestone fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public Milestone getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(Milestone toMilestone) {
        this.toMilestone = toMilestone;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
