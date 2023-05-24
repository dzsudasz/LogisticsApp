package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

    @EntityGraph(attributePaths = "sectionList")
    List<TransportPlan> findAll();
}
