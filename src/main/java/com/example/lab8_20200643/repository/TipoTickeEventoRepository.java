package com.example.lab8_20200643.repository;

import com.example.lab8_20200643.entity.TipoTicketEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTickeEventoRepository extends JpaRepository<TipoTicketEvento,Integer> {
}
