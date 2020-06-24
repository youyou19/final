package mum.edu.service;


import mum.edu.model.User;

import java.util.List;

public interface UserService {
   public User saveUser(User user);
  public List<User> findAll();
  public User findById(Long id);
  public User findUserByEmail(String email);
    public String findUserByRole(Long id);
    public User approvalSeller(User user);
  public List<User> findUserByRole();

}
