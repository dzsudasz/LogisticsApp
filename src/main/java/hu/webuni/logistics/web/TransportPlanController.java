package hu.webuni.logistics.web;


import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    TransportPlanService transportPlanService;

    @PostMapping("/{id}/delay")
    public void addDelay(@PathVariable long id, @RequestBody DelayDto delayDto) {

        transportPlanService.addDelay(id, delayDto);
    }
}
