package com.ipn.mx.apieventos.controller;

import com.ipn.mx.apieventos.modelo.entidades.Asistente;
import com.ipn.mx.apieventos.modelo.services.AsistenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class AsistenteController {

    @Autowired
    private AsistenteService service;

    @GetMapping("/asistentes")
    public List<Asistente> readAll() {
        return service.findAll();
    }

    @GetMapping("/asistente/{id}")
    public ResponseEntity<?>read(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Asistente newAsistent = null;

        try {
            newAsistent = service.findById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat("=").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (newAsistent == null) {
            response.put("mensaje", "El asistente con ID ".concat((id.toString()).concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(newAsistent, HttpStatus.OK);
    }

    @DeleteMapping("/asistente/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Asistente deletedAsistent = null;

        try {
            Asistente AsistentToDelete = service.findById(id);

            if (AsistentToDelete == null) {
                response.put("mensaje", "El asistente con ID " + id + " no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            deletedAsistent = AsistentToDelete;
            service.delete(id);

            response.put("mensaje", "Asistente borrado correctamente");
            response.put("asistente", deletedAsistent);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al borrar al asistente");
            response.put("error", "Ha ocurrido un error durante la operación");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/asistente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@Valid @RequestBody Asistente asistente, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("mensaje", "Error en los datos proporcionados");
            response.put("errores", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Asistente savedAsistente = service.save(asistente);

            response.put("mensaje", "Asistente creado correctamente");
            response.put("asistente", savedAsistente);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el asistente");
            response.put("error", "Ha ocurrido un error durante la operación");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/asistente/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Asistente asistente, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Asistente updateUser = null;
        Asistente searchUser = service.findById(id);

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream().map(err -> "El campo " + err.getField() + " " +
                    err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores", errores);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (searchUser == null) {
            response.put("mensaje", "El Asistente ".concat(id.toString()).concat(" no existe en la base de datos"));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }

        try {
            searchUser.setNombre(asistente.getNombre());
            searchUser.setPaterno(asistente.getPaterno());
            searchUser.setMaterno(asistente.getMaterno());
            searchUser.setEmail(asistente.getEmail());
            //searchUser.setFechaRegistro(asistente.getFechaRegistro());

            updateUser = service.save(searchUser);


        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el asistente");
            response.put("error", e.getMessage().concat("=").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El asistente se actualizó satisfactoriamente");
        response.put("asistente", updateUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
