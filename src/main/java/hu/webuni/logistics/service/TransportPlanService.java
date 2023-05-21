package hu.webuni.logistics.service;

import hu.webuni.logistics.config.LogisticsConfigProperties;
import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.repository.MilestoneRepository;
import hu.webuni.logistics.repository.SectionRepository;
import hu.webuni.logistics.repository.TransportPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    LogisticsConfigProperties config;

    @Transactional
    public void addDelay(long id, DelayDto delayDto) {
        TransportPlan transportPlan = transportPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Milestone milestone = milestoneRepository.findById(delayDto.getMilestoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean foundMatch = false;
        Long matchedSectionId = null;
        Boolean isFromMilestone = null;

        for (Section section : transportPlan.getSectionList()) {
            if (section.getFromMilestone().equals(milestone)) {
                foundMatch = true;
                matchedSectionId = section.getId();
                isFromMilestone = true;
            }
            if (section.getToMilestone().equals(milestone)) {
                foundMatch = true;
                matchedSectionId = section.getId();
                isFromMilestone = false;
            }
        }
        if (!foundMatch)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Section foundSection = sectionRepository.findById(matchedSectionId).get();

        LocalDateTime toMilestoneIncreasedTime = foundSection.getToMilestone()
                .getPlannedTime().plusMinutes(delayDto.getDelayMinutes());
        if (isFromMilestone) {
            LocalDateTime fromMilestoneIncreasedTime = foundSection.getFromMilestone()
                    .getPlannedTime().plusMinutes(delayDto.getDelayMinutes());

            foundSection.getToMilestone().setPlannedTime(toMilestoneIncreasedTime);
            foundSection.getFromMilestone().setPlannedTime(fromMilestoneIncreasedTime);

            sectionRepository.save(foundSection);
        }
        if (!isFromMilestone) {
            Optional<Section> nextSectionOptional = sectionRepository.findByNumber(foundSection.getNumber() + 1);
            if (nextSectionOptional.isPresent()) {
                Section nextSection = nextSectionOptional.get();
                LocalDateTime nextFromMilestoneIncreasedTime = nextSection.getFromMilestone()
                        .getPlannedTime().plusMinutes(delayDto.getDelayMinutes());

                nextSection.getFromMilestone().setPlannedTime(nextFromMilestoneIncreasedTime);
                sectionRepository.save(nextSection);
            }


            foundSection.getToMilestone().setPlannedTime(toMilestoneIncreasedTime);

            sectionRepository.save(foundSection);
        }


        double expectedIncome = transportPlan.getExpectedIncome();
        float deduction = (float) (100 - getDeductionPercent(delayDto)) / 100;
        transportPlan.setExpectedIncome(expectedIncome * deduction);
    }

    private int getDeductionPercent(DelayDto delayDto) {
        int percent = 0;
        Set<Map.Entry<Integer, Integer>> configDelayPercent = config.getDelayMinutesDeductionPercent().entrySet();
        for (Map.Entry<Integer, Integer> delayPercent : configDelayPercent) {
            if (delayDto.getDelayMinutes() >= delayPercent.getKey()) {
                percent = delayPercent.getValue();
            }
        }
        return percent;
    }
}
