package com.example.lab8_20200643.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "local")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String direccion;
    private String latitud;
    private String longitud;

    @OneToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
}
