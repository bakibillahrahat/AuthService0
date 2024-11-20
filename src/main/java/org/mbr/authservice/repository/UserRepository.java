package org.mbr.authservice.repository;

import org.apache.catalina.User;
import org.mbr.authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
     public UserInfo findByUsername(String username);
}
