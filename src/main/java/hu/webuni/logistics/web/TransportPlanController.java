package hu.webuni.logistics.web;


import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.TransportPlan;
import hu.webuni.logistics.service.TransportPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    TransportPlanService transportPlanService;

    @PostMapping("/{id}/delay")
//    @PreAuthorize("hasAnyAuthority('TransportManager')")
    public void addDelay(@PathVariable long id, @RequestBody @Valid DelayDto delayDto) {

        transportPlanService.addDelay(id, delayDto);
    }
}
