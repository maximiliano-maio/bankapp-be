package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}