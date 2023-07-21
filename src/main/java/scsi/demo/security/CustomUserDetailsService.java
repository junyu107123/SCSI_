package scsi.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import scsi.demo.model.User;
import scsi.demo.repository.UserRepository;
import scsi.demo.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.findByUserid(userid);
        if (user == null) {
            throw new UsernameNotFoundException("該帳號不存在");
        }
        return new CustomUserDetails(user);
    }
}