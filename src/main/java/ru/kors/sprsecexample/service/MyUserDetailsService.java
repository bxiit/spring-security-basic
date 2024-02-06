package ru.kors.sprsecexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kors.sprsecexample.config.MyUserDetails;
import ru.kors.sprsecexample.model.MyUser;
import ru.kors.sprsecexample.repository.UserRepository;

import java.util.Optional;

@Service

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> optionalMyUser = userRepository.findByName(username);
        return optionalMyUser.map(MyUserDetails::new) // кастим ретурн до MyUserDetails ведь метод возвращает UserDetails (MyUserDetails реализует UserDetails)
                .orElseThrow( () -> new UsernameNotFoundException(username + " not found") ); // если не найдено то ошибка
    }
}
