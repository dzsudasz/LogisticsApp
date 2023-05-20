package hu.webuni.logistics.mapper;

import hu.webuni.logistics.dto.AddressDto;
import hu.webuni.logistics.model.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address dtoToAddress(AddressDto addressDto);
    @InheritInverseConfiguration
    AddressDto addressToDto(Address address);
    List<Address> dtosToAddresses(List<AddressDto> addressDtos);
    List<AddressDto> adressesToDtos(List<Address> addresses);
}
