package hu.webuni.logistics.web;


import hu.webuni.logistics.dto.DelayDto;
import hu.webuni.logistics.dto.TransportPlanDto;
import hu.webuni.logistics.model.TransportPlan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @PostMapping("/{id}/delay")
    public void addDelay(@PathVariable long id, @RequestBody DelayDto delayDto) {


    }
}
