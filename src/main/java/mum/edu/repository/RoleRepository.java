package mum.edu.repository;

import mum.edu.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
    List<Role> findAll();

    @Query("Select distinct  r from Role r where r.role<>'ADMIN'")
    List<Role> findRole();
}
