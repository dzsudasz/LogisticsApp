package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
