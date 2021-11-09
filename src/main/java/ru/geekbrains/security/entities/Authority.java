package ru.geekbrains.security.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="authorities")
public class Authority extends Rights{

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column (name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
