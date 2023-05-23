package hu.webuni.logistics.service;

import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.model.Milestone;
import hu.webuni.logistics.repository.AddressRepository;
import hu.webuni.logistics.repository.MilestoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Transactional
    public Address addNew(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> findAddressById(long id) {

        return addressRepository.findById(id);
    }

    @Transactional
    public void deleteAddress(long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Milestone milestone = milestoneRepository.findByAddress(address);

        if (milestone != null)
          milestone.setAddress(null);

        addressRepository.delete(address);
    }

    @Transactional
    public Address modifyAddressById(long id, Address address) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        address.setId(id);
        return addressRepository.save(address);
    }

    public List<Address> filterAndSort(Address example, Pageable pageable) {

        Specification<Address> specification = Specification.where(null);

        String countryCode = null;
        String city = null;
        String zipCode = null;
        String street = null;

        if (example.getCountryCode() != null)
            countryCode = example.getCountryCode();
        if (example.getCity() != null)
            city = example.getCity();
        if (example.getZipCode() != null)
            zipCode = example.getZipCode();
        if (example.getStreet() != null)
            street = example.getStreet();


        if (StringUtils.hasText(countryCode)) {
            specification = specification.and(AddressSpecifications.hasCountryCode(countryCode));
        }
        if (StringUtils.hasText(city)) {
            specification = specification.and(AddressSpecifications.hasCity(city));
        }
        if (StringUtils.hasText(zipCode)) {
            specification = specification.and(AddressSpecifications.hasZipCode(zipCode));
        }
        if (StringUtils.hasText(street)) {
            specification = specification.and(AddressSpecifications.hasStreet(street));
        }

        Page<Address> filteredAddressesPage = addressRepository.findAll(specification, pageable);

        return filteredAddressesPage.stream().toList();
    }
}
