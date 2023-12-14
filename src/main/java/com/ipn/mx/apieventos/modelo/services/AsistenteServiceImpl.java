package com.ipn.mx.apieventos.modelo.services;

import com.ipn.mx.apieventos.modelo.dao.AsistenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.apieventos.modelo.entidades.Asistente;
import java.util.List;

@Service
public class AsistenteServiceImpl implements AsistenteService {

    @Autowired
    AsistenteDAO dao;

    @Override
    @Transactional(readOnly = false)
    public List<Asistente> findAll() {
        return (List<Asistente>) dao.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Asistente findById(Long id){ return dao.findById(id).orElse(null);}

    @Override
    @Transactional
    public Asistente save(Asistente asistente){ return dao.save(asistente);}

    @Override
    @Transactional
    public void delete(Long id){ dao.deleteById(id);}

}
