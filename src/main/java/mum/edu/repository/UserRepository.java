package mum.edu.repository;

import mum.edu.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    @Query("Select distinct r.role from  User u join u.roles r where u.id=:id")
    public String findUserByRole(@Param(value="id") Long id);
    @Query("Select distinct u from User u join u.roles r where r.role='SELLER'")
    public List<User> findUserByRole();
}
