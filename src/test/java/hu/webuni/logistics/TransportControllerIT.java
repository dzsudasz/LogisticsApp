package hu.webuni.logistics;

import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.LoginDto;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.repository.MilestoneRepository;
import hu.webuni.logistics.repository.SectionRepository;
import hu.webuni.logistics.repository.TransportPlanRepository;
import hu.webuni.logistics.service.InitDbService;
import hu.webuni.logistics.service.TransportPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TransportControllerIT {

    private static final String LOGIN_URI = "/api/login";
    private static final String TRANSPORTPLAN_URI = "/api/transportPlans";
    @Autowired
    InitDbService initDbService;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    SectionRepository sectionRepository;

    @BeforeTestExecution
    void initDb() {
        initDbService.initDb();
    }


    private String correctLogin() {
        return webTestClient
                .post()
                .uri(LOGIN_URI)
                .bodyValue(new LoginDto("transportUser", "pass"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    private String inCorrectLogin() {
        return webTestClient
                .post()
                .uri(LOGIN_URI)
                .bodyValue(new LoginDto("addressUser", "pass"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    private WebTestClient.ResponseSpec addDelay(long id, String jwt, long milestoneId, int delayMinutes) {
        return webTestClient
                .post()
                .uri(TRANSPORTPLAN_URI + "/" + id + "/delay")
                .headers(headers -> headers.setBearerAuth(jwt))
                .bodyValue(new DelayDto(milestoneId, delayMinutes))
                .exchange();
    }

    @Test
    void checkIfThirtyMinutesDelayCanBeAddedToToMilestone() {
        int delayMinutes = 30;

        TransportPlan originalTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> originalSectionList = originalTransportPlan.getSectionList();
        Milestone originalToMilestone = milestoneRepository.findById(originalSectionList.get(0).getToMilestone().getId()).get();
        Milestone originalFromMilestone = milestoneRepository.findById(originalSectionList.get(1).getFromMilestone().getId()).get();

        String jwt = correctLogin();

        addDelay(originalTransportPlan.getId(), jwt, originalToMilestone.getId(), delayMinutes).expectStatus().isOk();

        TransportPlan modifiedTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> modifiedSectionList = originalTransportPlan.getSectionList();
        Milestone modifiedToMilestone = milestoneRepository.findById(modifiedSectionList.get(0).getToMilestone().getId()).get();
        Milestone modifiedFromMilestone = milestoneRepository.findById(modifiedSectionList.get(1).getFromMilestone().getId()).get();

        assertThat(originalTransportPlan.getExpectedIncome() * 0.95)
                .isCloseTo(modifiedTransportPlan.getExpectedIncome(), within(0.001));
        assertThat(originalFromMilestone.getPlannedTime().plusMinutes(delayMinutes))
                .isEqualTo(modifiedFromMilestone.getPlannedTime());
        assertThat(originalToMilestone.getPlannedTime().plusMinutes(delayMinutes))
                .isEqualTo(modifiedToMilestone.getPlannedTime());
    }

    @Test
    void checkIfThirtyMinutesDelayCanBeAddedToFromMilestone() {
        int delayMinutes = 30;

        TransportPlan originalTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> originalSectionList = originalTransportPlan.getSectionList();
        Milestone originalFromMilestone = milestoneRepository.findById(originalSectionList.get(0).getFromMilestone().getId()).get();
        Milestone originalToMilestone = milestoneRepository.findById(originalSectionList.get(0).getToMilestone().getId()).get();

        String jwt = correctLogin();

        addDelay(originalTransportPlan.getId(), jwt, originalFromMilestone.getId(), delayMinutes).expectStatus().isOk();

        TransportPlan modifiedTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> modifiedSectionList = originalTransportPlan.getSectionList();
        Milestone modifiedFromMilestone = milestoneRepository.findById(modifiedSectionList.get(0).getFromMilestone().getId()).get();
        Milestone modifiedToMilestone = milestoneRepository.findById(modifiedSectionList.get(0).getToMilestone().getId()).get();

        assertThat(originalTransportPlan.getExpectedIncome() * 0.95)
                .isCloseTo(modifiedTransportPlan.getExpectedIncome(), within(0.001));
        assertThat(originalFromMilestone.getPlannedTime().plusMinutes(delayMinutes))
                .isEqualTo(modifiedFromMilestone.getPlannedTime());
        assertThat(originalToMilestone.getPlannedTime().plusMinutes(delayMinutes))
                .isEqualTo(modifiedToMilestone.getPlannedTime());
    }

    @Test
    void checkIfNegativeDelayIsRefused() {
        int delayMinutes = -30;

        TransportPlan originalTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> originalSectionList = originalTransportPlan.getSectionList();
        Milestone originalFromMilestone = milestoneRepository.findById(originalSectionList.get(0).getFromMilestone().getId()).get();
        Milestone originalToMilestone = milestoneRepository.findById(originalSectionList.get(0).getToMilestone().getId()).get();

        String jwt = correctLogin();

        addDelay(originalTransportPlan.getId(), jwt, originalFromMilestone.getId(), delayMinutes).expectStatus().isBadRequest();
    }

    @Test
    void checkIfUnauthorizedUserIsRefused() {
        int delayMinutes = 30;

        TransportPlan originalTransportPlan = transportPlanRepository.findAll().get(0);
        List<Section> originalSectionList = originalTransportPlan.getSectionList();
        Milestone originalFromMilestone = milestoneRepository.findById(originalSectionList.get(0).getFromMilestone().getId()).get();
        Milestone originalToMilestone = milestoneRepository.findById(originalSectionList.get(0).getToMilestone().getId()).get();

        String jwt = inCorrectLogin();

        addDelay(originalTransportPlan.getId(), jwt, originalFromMilestone.getId(), delayMinutes).expectStatus().isForbidden();
    }
}
