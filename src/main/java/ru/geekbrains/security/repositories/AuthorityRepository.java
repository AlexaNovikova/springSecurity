package ru.geekbrains.security.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.security.entities.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}
