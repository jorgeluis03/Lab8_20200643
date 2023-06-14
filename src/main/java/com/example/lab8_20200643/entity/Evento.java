package com.example.lab8_20200643.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fecha;
    private String nombre;
    private String descripcion;
    private String path_image;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "idLocal")
    private Local local;



}
