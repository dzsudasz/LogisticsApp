package hu.webuni.logistics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DelayDto {

    @NotNull
    private Long milestoneId;
    @Positive
    private int delayMinutes;

    public DelayDto(Long milestoneId, int delayMinutes) {
        this.milestoneId = milestoneId;
        this.delayMinutes = delayMinutes;
    }

    public DelayDto() {
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
}
