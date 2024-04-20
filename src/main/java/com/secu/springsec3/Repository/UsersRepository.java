package com.secu.springsec3.Repository;

import com.secu.springsec3.Entite.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String Username);
}
