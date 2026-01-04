package com.jpmc.midascore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpmc.midascore.entity.UserRecord;

@Repository
public interface UserRepository extends CrudRepository<UserRecord, Long> {
    UserRecord findById(long id); // Ensure this is there for validation
    UserRecord findByName(String name); // Add this line to fix the red error!
}