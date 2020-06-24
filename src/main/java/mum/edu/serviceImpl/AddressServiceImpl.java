package mum.edu.serviceImpl;

import mum.edu.model.Address;
import mum.edu.repository.AddressRepository;
import mum.edu.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
   @Autowired
   private AddressRepository addressRepository;


    @Override
    public Address saveAddress(Address adr) {
       adr.setCreationDate(new Date());
        return addressRepository.save(adr);
    }

    @Override
    public Address editAddress(Address adr) {
        adr.setUpdateDate(new Date());
        return addressRepository.save(adr);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address findAddress(Long id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public List<Address> getAllAddress() {
        return (List<Address>)addressRepository.findAll();
    }
}
