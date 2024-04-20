package com.secu.springsec3.Service;

import com.secu.springsec3.Entite.Roles;
import com.secu.springsec3.Entite.Users;
import com.secu.springsec3.Repository.RolesRepository;
import com.secu.springsec3.Repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServiceSecImpl implements ServiceSec {
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
   private PasswordEncoder passwordEncoder;
    public ServiceSecImpl(UsersRepository usersRepository, RolesRepository rolesRepository,PasswordEncoder   passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users addnewuser(Users user)
    {
        String pw = user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return usersRepository.save(user);
    }

    @Override
    public Roles addRolenamee(Roles role) {
        return rolesRepository.save(role);
    }

    @Override
    public void addroletousers(String Username, String role) {


        Users user = usersRepository.findByUsername(Username);


        Roles roles = rolesRepository.findByRole(role);


        user.getListRoles().add(roles);
    }



    @Override
    public Users LoadUserbyUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public List<Users> listUsers() {
        return usersRepository.findAll();
    }
}
