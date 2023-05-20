package hu.webuni.logistics.service;

import hu.webuni.logistics.model.Address;
import hu.webuni.logistics.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {

    public static Specification<Address> hasCountryCode(String countryCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Address_.COUNTRY_CODE), countryCode);
    }

    public static Specification<Address> hasCity(String city) {
        String cityLower = city.toLowerCase();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Address_.CITY)), cityLower);
    }

    public static Specification<Address> hasZipCode(String zipCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Address_.ZIP_CODE), zipCode);
    }

    public static Specification<Address> hasStreet(String street) {
        String streetLower = street.toLowerCase();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Address_.STREET)), streetLower);
    }
}
