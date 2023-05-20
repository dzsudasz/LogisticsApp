package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findAll(Specification<Address> specification, Pageable pageable);
}
