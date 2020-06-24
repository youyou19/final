package mum.edu.serviceImpl;


import mum.edu.model.Role;
import mum.edu.repository.RoleRepository;
import mum.edu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
//@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {

        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role get(Long id) {

        return roleRepository.findById(id).get();
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> findRole() {
        return roleRepository.findRole();
    }
}
