package com.example.lab8_20200643.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_ticket_evento")
public class TipoTicketEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String precio;
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "idEvento")
    private Evento evento;



}
