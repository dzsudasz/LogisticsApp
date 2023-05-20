package hu.webuni.logistics.web;

import hu.webuni.logistics.dto.AddressDto;
import hu.webuni.logistics.mapper.AddressMapper;
import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    AddressMapper addressMapper;

    @PostMapping
    public AddressDto addNewAddress(@RequestBody @Valid AddressDto addressDto) {
        if (addressDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Address address = addressMapper.dtoToAddress(addressDto);
        Address added = addressService.addNew(address);
        return addressMapper.addressToDto(added);
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {

        List<Address> addresses = addressService.findAllAddresses();
        return addressMapper.adressesToDtos(addresses);
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable long id) {
        Address address = addressService.findAddressById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return addressMapper.addressToDto(address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable long id) {
        addressService.deleteAddress(id);
    }

    @PutMapping("/{id}")
    public AddressDto modifyAddressById(@PathVariable long id, @RequestBody @Valid AddressDto addressDto) {

        Address originalAddress = addressMapper.dtoToAddress(addressDto);
        Address modifiedAddress = addressService.modifyAddressById(id, originalAddress);
        return addressMapper.addressToDto(modifiedAddress);
    }
}
