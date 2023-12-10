package dev.pr.habittracker.security;

import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HabitTrackerUserDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Optional<Person> target = peopleRepository.findByEmail(email);
        if(target.isEmpty())
            throw new UsernameNotFoundException("User details not found for user: " + email);
        Person person = target.get();
        person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
        return new User(person.getEmail(), person.getPassword(), person.isEnabled(), true, true, person.isNonLocked(), authorities);
    }
}
