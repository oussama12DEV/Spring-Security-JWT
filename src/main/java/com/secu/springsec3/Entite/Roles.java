package com.secu.springsec3.Entite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String role;
}
