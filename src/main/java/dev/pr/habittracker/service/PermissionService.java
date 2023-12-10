package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Role;
import dev.pr.habittracker.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService {
    private final PeopleRepository peopleRepository;
    public void authorizeWithRole(Role role, String id) {
        if(Role.ADMIN.equals(role))
            throw new NotCreatedException("Authorization as an admin is not available or accessible");
        Person person = peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
        person.getRoles().add(role);
        peopleRepository.save(person);
    }
    public void disallowWithoutRole(Role role, String id) {
        if(Role.ADMIN.equals(role))
            throw new NotCreatedException("Disallow role admin is not available or accessible");
        Person person = peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
        person.getRoles().remove(role);
        peopleRepository.save(person);
    }
    public void lock(String id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
        person.setNonLocked(false);
        peopleRepository.save(person);
    }
    public void unlock(String id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
        person.setNonLocked(true);
        peopleRepository.save(person);
    }
}
