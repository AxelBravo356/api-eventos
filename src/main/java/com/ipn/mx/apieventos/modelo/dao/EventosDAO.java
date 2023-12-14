package com.ipn.mx.apieventos.modelo.dao;

import com.ipn.mx.apieventos.modelo.entidades.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventosDAO extends CrudRepository<Evento, Long> {
}
