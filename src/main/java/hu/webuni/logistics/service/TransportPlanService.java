package hu.webuni.logistics.service;

import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.repository.MilestoneRepository;
import hu.webuni.logistics.repository.SectionRepository;
import hu.webuni.logistics.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    SectionRepository sectionRepository;

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

        if (isFromMilestone) {
            foundSection.getToMilestone().getPlannedTime().plusMinutes(delayDto.getDelayMinutes());
            foundSection.getFromMilestone().getPlannedTime().plusMinutes(delayDto.getDelayMinutes());
            sectionRepository.save(foundSection);
        }
        if (!isFromMilestone) {
            foundSection.getToMilestone().getPlannedTime().plusMinutes(delayDto.getDelayMinutes());
            Section nextSection = sectionRepository.findByNumber(foundSection.getNumber()+1);
            nextSection.getFromMilestone().getPlannedTime().plusMinutes(delayDto.getDelayMinutes());
            sectionRepository.save(foundSection);
            sectionRepository.save(nextSection);
        }

        //Késésért járó levonás
    }
}
