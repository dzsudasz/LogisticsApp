package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
