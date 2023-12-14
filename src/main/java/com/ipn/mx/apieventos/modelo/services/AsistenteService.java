package com.ipn.mx.apieventos.modelo.services;
import com.ipn.mx.apieventos.modelo.entidades.Asistente;

import java.util.List;

public interface AsistenteService {
    public List<Asistente> findAll();
    public Asistente findById(Long id);
    public Asistente save(Asistente asistente);
    public void delete(Long id);


}
