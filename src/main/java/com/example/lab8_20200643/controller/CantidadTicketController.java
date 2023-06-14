package com.example.lab8_20200643.controller;

import com.example.lab8_20200643.dto.Tickets_x_Usuario;
import com.example.lab8_20200643.entity.Usuario;
import com.example.lab8_20200643.repository.TipoTickeEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin

public class CantidadTicketController {
    @Autowired
    TipoTickeEventoRepository tipoTickeEventoRepository;
    @GetMapping("/cantidadTicketsUsuario")
    public List<Tickets_x_Usuario> TicketPorUsuario (){
        return tipoTickeEventoRepository.listaTicketsUsers();
    }
}
