package com.secu.springsec3.Repository;

import com.secu.springsec3.Entite.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {

    Roles findByRole(String role);
}
