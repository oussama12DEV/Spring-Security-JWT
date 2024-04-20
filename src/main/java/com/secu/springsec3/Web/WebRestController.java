package com.secu.springsec3.Web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.springsec3.Entite.Roles;
import com.secu.springsec3.Entite.Users;
import com.secu.springsec3.Service.ServiceSec;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class WebRestController {
    private ServiceSec serviceSec;


    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<Users> listusers(){
        return serviceSec.listUsers();
    };
    @PostMapping("/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    Users saveusers(@RequestBody Users users)   {
        return serviceSec.addnewuser(users);
    }
    @PostMapping("/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    Roles saverole(@RequestBody Roles roles){
        return serviceSec.addRolenamee(roles);
    }
    @PostMapping("/addroletousers")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addroletousers(@RequestBody Addroletousers addroletousers){
        serviceSec.addroletousers(addroletousers.getUsername(),addroletousers.getRolename());
    }
    @GetMapping("/RefrechToken")
    public void refrechtokenn(HttpServletRequest requestT, HttpServletResponse response) throws IOException {
        String authToken = requestT.getHeader("Authorization");
        if(authToken!=null && authToken.startsWith("Bearer ")){
            try {

            String refrechToken = authToken.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("mysecret12345");
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refrechToken);
            String username = decodedJWT.getSubject();
            Users user = serviceSec.LoadUserbyUsername(username);
            String JwtAccesToken = JWT.create().withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000))
                    .withIssuer(requestT.getRequestURL().toString())
                    .withClaim("roles",user.getListRoles().stream().map(ro->ro.getRole()).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String,String> idtoken = new HashMap<>();
            idtoken.put("AccesToken",JwtAccesToken);
            idtoken.put("RefrechToken",refrechToken);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(),idtoken);
            }
            catch (Exception e){
              throw e;
            }
        }
        else {
            throw new RuntimeException("Refrech token required!!");
        }

    }
    @GetMapping("/profil")
    public Users profil(Principal principal){
        return serviceSec.LoadUserbyUsername(principal.getName());
    }

}
@Data
class  Addroletousers{
    private String username;
    private String rolename;
}
