package mum.edu.service;

import mum.edu.model.Address;

import java.util.List;

public interface AddressService {
    public Address saveAddress(Address a);
    public Address editAddress(Address a);
    public Address findAddress(Long id);
    public List<Address> getAllAddress();
    public void deleteAddress(Long id);
}
