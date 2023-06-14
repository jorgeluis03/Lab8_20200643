package com.example.lab8_20200643.repository;

import com.example.lab8_20200643.dto.Tickets_x_Usuario;
import com.example.lab8_20200643.entity.TipoTicketEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTickeEventoRepository extends JpaRepository<TipoTicketEvento,Integer> {
    @Query(nativeQuery = true,value = "select  count(txe.id) as cantidad, u.correo as correo\n" +
            "from ticket t \n" +
            "inner join usuario u on (t.idUsuario=u.idusuario)\n" +
            "inner join tipo_ticket_evento txe on (txe.id=t.idTipoTicket)\n" +
            "group by u.idusuario")
    List<Tickets_x_Usuario> listaTicketsUsers();
}
