package com.ipn.mx.apieventos.modelo.services;

import com.ipn.mx.apieventos.modelo.entidades.Evento;
import java.util.List;
public interface EventoService {
    public List<Evento> findAll();
    public Evento findById(Long id);
    public Evento save(Evento evento);
    public void delete(Long id);

}
