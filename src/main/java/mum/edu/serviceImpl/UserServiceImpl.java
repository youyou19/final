package mum.edu.serviceImpl;

import mum.edu.model.User;
import mum.edu.repository.UserRepository;
import mum.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String findUserByRole(Long id) {
        return userRepository.findUserByRole(id);
    }


    @Override
    public User findById(Long id) {
       return userRepository.findById(id).get();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public User saveUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setApproved(0);
        return userRepository.save(user);
    }
    @Override
    public List<User> findUserByRole() {
        return userRepository.findUserByRole();
    }
    @Override
    public User approvalSeller(User user) {
     //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setApproved(1);
        return userRepository.save(user);
    }
    @Override
    public List<User> findAll(){
        return (List<User>)userRepository.findAll();
    }

}
