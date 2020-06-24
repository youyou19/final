package mum.edu.service;


import mum.edu.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAll();

    public Role get(Long id);
    public List<Role> findRole();
    public Role findRoleById(long id);
}
