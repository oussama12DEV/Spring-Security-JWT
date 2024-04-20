package com.secu.springsec3.Service;

import com.secu.springsec3.Entite.Roles;
import com.secu.springsec3.Entite.Users;

import java.util.List;

public interface ServiceSec {
    Users addnewuser(Users username);
    Roles addRolenamee(Roles role);
    void addroletousers(String username,String role);
     Users LoadUserbyUsername(String username);
     List<Users> listUsers();
}
