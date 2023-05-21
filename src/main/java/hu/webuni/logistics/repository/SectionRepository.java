package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByNumber(int number);
}
