package com.ipn.mx.apieventos.controller;

import com.ipn.mx.apieventos.modelo.entidades.Evento;
import com.ipn.mx.apieventos.modelo.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping("/eventos")
    public List<Evento> readAll() {
        return service.findAll();
    }

    @GetMapping("/evento/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Evento newEvento = null;

        try {
            newEvento = service.findById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat("=").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }


        if (newEvento == null) {
            response.put("mensaje", "El evento ".concat((id.toString()).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(newEvento, HttpStatus.OK);
    }

    @DeleteMapping("/evento/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Evento deletedEvento = null;

        try {
            Evento EventoToDelete = service.findById(id);

            if (EventoToDelete == null) {
                response.put("mensaje", "El evento con ID " + id + " no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            deletedEvento = EventoToDelete;
            service.delete(id);

            response.put("mensaje", "Evento borrado correctamente");
            response.put("evento", deletedEvento);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al borrar el evento");
            response.put("error", "Ha ocurrido un error durante la operación");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/evento")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@Valid @RequestBody Evento evento, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("mensaje", "Error en los datos proporcionados");
            response.put("errores", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Evento savedEvento = service.save(evento);

            response.put("mensaje", "Evento creado correctamente");
            response.put("evento", savedEvento);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el evento");
            response.put("error", "Ha ocurrido un error durante la operación");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/evento/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Evento evento, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Evento updateEvento = null;
        Evento searchEvento = service.findById(id);

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream().map(err -> "El campo " + err.getField() + " " +
                    err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores", errores);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (searchEvento == null) {
            response.put("mensaje", "El Evento ".concat(id.toString()).concat(" no existe en la base de datos"));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }

        try {
            searchEvento.setDescripcionEvento(evento.getDescripcionEvento());
            searchEvento.setNombreEvento(evento.getNombreEvento());
            searchEvento.setFechaCreacion(evento.getFechaCreacion());

            updateEvento = service.save(searchEvento);


        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el evento");
            response.put("error", e.getMessage().concat("=").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El evento se actualizó satisfactoriamente");
        response.put("evento", updateEvento);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
