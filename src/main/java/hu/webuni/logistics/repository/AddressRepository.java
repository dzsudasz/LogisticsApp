package hu.webuni.logistics.repository;

import hu.webuni.logistics.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
