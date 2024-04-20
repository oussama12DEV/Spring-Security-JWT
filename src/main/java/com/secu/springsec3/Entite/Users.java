package com.secu.springsec3.Entite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String Password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Roles> ListRoles= new ArrayList<>();
}
