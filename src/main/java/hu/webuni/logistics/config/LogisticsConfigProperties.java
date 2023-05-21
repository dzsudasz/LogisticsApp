package hu.webuni.logistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "logistics")
@Component
public class LogisticsConfigProperties {

    private Map<Integer, Integer> delayMinutesDeductionPercent;

    public Map<Integer, Integer> getDelayMinutesDeductionPercent() {
        return delayMinutesDeductionPercent;
    }

    public void setDelayMinutesDeductionPercent(Map<Integer, Integer> delayMinutesDeductionPercent) {
        this.delayMinutesDeductionPercent = delayMinutesDeductionPercent;
    }
}
