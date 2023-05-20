package hu.webuni.logistics.service;

import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

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

        addressRepository.deleteById(id);
    }

    @Transactional
    public Address modifyAddressById(long id, Address address) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        address.setId(id);
        return addressRepository.save(address);
    }
}
