package hu.webuni.logistics.service;

import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.repository.AddressRepository;
import hu.webuni.logistics.repository.MilestoneRepository;
import hu.webuni.logistics.repository.SectionRepository;
import hu.webuni.logistics.repository.TransportPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InitDbService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MilestoneRepository milestoneRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TransportPlanRepository transportPlanRepository;

    @Transactional
    public void initDb() {

        Address testAddress1 = addressRepository.save(
                new Address("HU", "Budapest", "Test Street", "1000", "1"));
        Address testAddress2 = addressRepository.save(
                new Address("DE", "Hamburg", "Test Street1", "1001", "2"));
        Address testAddress3 = addressRepository.save(
                new Address("HU", "Miskolc", "Test Street2", "1002", "3"));
        Address testAddress4 = addressRepository.save(
                new Address("DE", "Stuttgart", "Test Street3", "1003", "4"));
        Address testAddress5 = addressRepository.save(
                new Address("HU", "Szeged", "Test Street4", "1004", "5"));
        Address testAddress6 = addressRepository.save(
                new Address("HU", "Pécs", "Test Street5", "1005", "6"));

        Milestone testMilestone1 = milestoneRepository.save(
                new Milestone(testAddress1, LocalDateTime.now().plusDays(1)));
        Milestone testMilestone2 = milestoneRepository.save(
                new Milestone(testAddress2, LocalDateTime.now().plusDays(2)));
        Milestone testMilestone3 = milestoneRepository.save(
                new Milestone(testAddress3, LocalDateTime.now().plusDays(3)));
        Milestone testMilestone4 = milestoneRepository.save(
                new Milestone(testAddress4, LocalDateTime.now().plusDays(4)));
        Milestone testMilestone5 = milestoneRepository.save(
                new Milestone(testAddress5, LocalDateTime.now().plusDays(5)));
        Milestone testMilestone6 = milestoneRepository.save(
                new Milestone(testAddress6, LocalDateTime.now().plusDays(6)));

        Section testSection1 = sectionRepository.save(
                new Section(testMilestone1, testMilestone2, 0));
        Section testSection2 = sectionRepository.save(
                new Section(testMilestone3, testMilestone4, 1));
        Section testSection3 = sectionRepository.save(
                new Section(testMilestone5, testMilestone6, 2));

        TransportPlan testTransportPlan1 = transportPlanRepository.save(
                new TransportPlan(30000, null));
        testTransportPlan1.getSectionList().add(testSection1);
        testTransportPlan1.getSectionList().add(testSection2);
        testTransportPlan1.getSectionList().add(testSection3);
    }
}
