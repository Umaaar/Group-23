package org.example.service;


import org.example.model.User;
import org.example.model.CustomUserDetail;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    public Integer getUserCount(){
        return Math.toIntExact(userRepository.count());
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return user.map(CustomUserDetail::new).get();
    }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
public User getAuthenticatedUser() {

    return userRepository.findUserByEmail(this.getAuthenticatedUser().getEmail()).get();
    
}


public List<User> getUsersByRole(int i) {
    Role role = roleRepository.findById(i).get();
    return userRepository.findUsersByRoles(role);
}



}
