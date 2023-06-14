package com.example.lab8_20200643.controller;

import com.example.lab8_20200643.entity.Evento;
import com.example.lab8_20200643.entity.TipoTicketEvento;
import com.example.lab8_20200643.repository.EventoRepository;
import com.example.lab8_20200643.repository.TipoTickeEventoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin

public class EventoController {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    TipoTickeEventoRepository tipoTickeEventoRepository;

    @GetMapping(value = "/evento",produces = MediaType.APPLICATION_JSON_VALUE+"; charset=utf-8")
    public List<Evento> ListaEventos(){
        return eventoRepository.findAll();
    }

    @GetMapping("/evento/{id}")
    public ResponseEntity<HashMap<String,Object>> ObtenerEvento(@PathVariable("id") String idStr){
        HashMap<String,Object> responseMap = new HashMap<>();

        //Se agrega la excepcion de que sea un numero
        try {
            int id = Integer.parseInt(idStr);
            //verificar si existe o np
            Optional<Evento> eventoopt =eventoRepository.findById(id);
            if(eventoopt.isPresent()){
                Evento evento = eventoopt.get();
                responseMap.put("Evento",evento);
                responseMap.put("resultado","Existoso");
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("msg","Evento no encontrado");
            }

        }catch (NumberFormatException e){
            responseMap.put("msg","El Id debe ser un numero entero positivo");
        }
        responseMap.put("resultado","Falla");
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PostMapping("/evento")
    public ResponseEntity<HashMap<String,Object>> CrearEvento (@RequestBody Evento evento,
                                                               @RequestParam(value = "fetchId",required = false) boolean fetchId){

        HashMap<String,Object> responseMap = new HashMap<>();
        eventoRepository.save(evento);
        if(fetchId){
            responseMap.put("id",evento.getId());
        }
        responseMap.put("Estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);  //Para docigo HTTP 201

    }
    @GetMapping("/eventoConTipoDeTicket/{id}")
    public ResponseEntity<HashMap<String,Object>> ListarEventoTicket(@PathVariable("id") String idStr){
        HashMap<String,Object> responseMap = new HashMap<>();
        //Validar que sea un numero
        try {
            int id = Integer.parseInt(idStr);
            //verificar si existe o np
            Optional<TipoTicketEvento> tipoTicketEventoOpt =tipoTickeEventoRepository.findById(id);
            if(tipoTicketEventoOpt.isPresent()){
                TipoTicketEvento tipoTicketEvento = tipoTicketEventoOpt.get();
                responseMap.put("TipoTicketEvento",tipoTicketEvento);
                responseMap.put("resultado","Existoso");
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("msg","TipoTicketEvento no encontrado");
            }

        }catch (NumberFormatException e){
            responseMap.put("msg","El Id debe ser un numero entero positivo");
        }
        responseMap.put("resultado","Falla");
        return ResponseEntity.badRequest().body(responseMap);
    }
    @PutMapping("/evento")
    public ResponseEntity<HashMap<String,Object>> EditarEvento (@RequestBody Evento evento){
        HashMap<String,Object> responseMap=new HashMap<>();
        if(evento.getId() != null && evento.getId() > 0){
            Optional<Evento> opt = eventoRepository.findById(evento.getId());

            if(opt.isPresent()){
                eventoRepository.save(evento);
                responseMap.put("Estado", "actualizado");
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("Estado","error");
                responseMap.put("msg","El evento a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        }else {
            responseMap.put("Estado","error");
            responseMap.put("msg","Debe ingresar un ID");
            return ResponseEntity.badRequest().body(responseMap);  //Error HTTP 400
        }

    }
    @DeleteMapping("/evento")
    public ResponseEntity<HashMap<String,Object>> borrarEvento(@PathVariable("id") String idStr){
        HashMap<String,Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Evento> optEvento = eventoRepository.findById(id);
            //solo retorna si existe o no el ID, No devuelve el producto
            if( optEvento.isPresent()){
                eventoRepository.deleteById(id);
                responseMap.put("Estado","borrado exitoso");
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("Estado","error");
                responseMap.put("msg","no se encontro el ID ");
                return ResponseEntity.badRequest().body(responseMap);  //error 400
            }

        }catch (NumberFormatException ex){
            responseMap.put("Estado","error");
            responseMap.put("msg","El ID debe ser un numero");
            return ResponseEntity.badRequest().body(responseMap);  //error 400
        }

    }




    //Gestionar excepcion cuando el no se mapea el JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, Object>> gestionExcepcion(HttpServletRequest request){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("Estado","error");
            responseMap.put("msg","Debe enviar un evento");
        }
        return ResponseEntity.badRequest().body(responseMap);   //enviar el error 400

    }
}
