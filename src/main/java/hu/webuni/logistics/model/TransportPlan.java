package hu.webuni.logistics.model;

import hu.webuni.logistics.model.Section;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class TransportPlan {

    @Id
    @GeneratedValue
    private long id;

    private double expectedIncome;
    @OneToMany
    private List<Section> sectionList;

    public TransportPlan(double expectedIncome, List<Section> sectionList) {
        this.expectedIncome = expectedIncome;
        this.sectionList = sectionList;
    }

    public TransportPlan() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(double expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }
}
