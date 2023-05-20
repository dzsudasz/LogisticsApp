package hu.webuni.logistics.service;

import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.repository.MilestoneRepository;
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

    public void addDelay(long id, DelayDto delayDto) {
        TransportPlan transportPlan = transportPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Milestone milestone = milestoneRepository.findById(delayDto.getMilestoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Milestone> ToMilestoneListInTransportPlan = new ArrayList<>();
        List<Milestone> FromMilestoneListInTransportPlan = new ArrayList<>();

        transportPlan.getSectionList().forEach(s -> {
            ToMilestoneListInTransportPlan.add(s.getToMilestone());
            FromMilestoneListInTransportPlan.add(s.getFromMilestone());
        });

        if (!ToMilestoneListInTransportPlan.contains(milestone) && !FromMilestoneListInTransportPlan.contains(milestone))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (ToMilestoneListInTransportPlan.contains(milestone)) {
            milestone.setPlannedTime(milestone.getPlannedTime().plusMinutes(delayDto.getDelayMinutes()));
        }
    }
}
