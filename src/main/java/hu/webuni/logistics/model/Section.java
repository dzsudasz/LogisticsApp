package hu.webuni.logistics.model;

import jakarta.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Milestone fromMilestone;
    @ManyToOne
    private Milestone toMilestone;
    private int number;

    public Section(Milestone fromMilestone, Milestone toMilestone, int number) {
        this.fromMilestone = fromMilestone;
        this.toMilestone = toMilestone;
        this.number = number;
    }

    public Section() {
    }

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
