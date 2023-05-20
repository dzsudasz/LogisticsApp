package hu.webuni.logistics.dto;

import hu.webuni.logistics.model.Section;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.util.List;

public class TransportPlanDto {

    private long id;

    private double expectedIncome;
    private List<Section> sectionList;

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
