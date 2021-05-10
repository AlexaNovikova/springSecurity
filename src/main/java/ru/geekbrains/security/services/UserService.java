package ru.geekbrains.security.services;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.security.entities.Authority;
import ru.geekbrains.security.entities.Rights;
import ru.geekbrains.security.entities.Role;
import ru.geekbrains.security.entities.User;
import ru.geekbrains.security.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        Collection<Role> roles = user.getRoles();
        Collection<Rights> userRolesAndAuthorities = new ArrayList<>(roles);
        for (Role role: roles) {
         userRolesAndAuthorities.addAll(role.getAuthorities());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapAuthoritiesToAuthorities(userRolesAndAuthorities));
    }

    private Collection<? extends GrantedAuthority> mapAuthoritiesToAuthorities(Collection<Rights> rolesAndAuthorities) {
        return rolesAndAuthorities.stream().map(right -> new SimpleGrantedAuthority(right.getName())).collect(Collectors.toList());
    }

}