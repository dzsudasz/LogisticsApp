package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
