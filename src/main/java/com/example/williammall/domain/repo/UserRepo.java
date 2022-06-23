package com.example.williammall.domain.repo;

import com.example.williammall.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findUserByLoginName(String loginName);

    User save(User user);
}
