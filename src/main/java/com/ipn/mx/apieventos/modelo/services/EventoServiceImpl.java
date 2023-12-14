package com.ipn.mx.apieventos.modelo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ipn.mx.apieventos.modelo.entidades.Evento;
import com.ipn.mx.apieventos.modelo.dao.EventosDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    EventosDAO dao;

    @Override
    @Transactional
    public List<Evento> findAll(){ return (List<Evento>) dao.findAll();}

    @Override
    @Transactional
    public Evento findById(Long id){ return dao.findById(id).orElse(null);}

    @Override
    @Transactional
    public Evento save(Evento evento){ return dao.save(evento);}

    @Override
    @Transactional
    public void delete(Long id) { dao.deleteById(id);}

}
