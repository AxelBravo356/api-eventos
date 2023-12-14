package com.ipn.mx.apieventos.controller;

import com.ipn.mx.apieventos.modelo.services.InformacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class InformacionController {
    @Autowired
    private InformacionService service;

    @GetMapping("/informacion/GenerarPDF")
    public String generarDocumentoPDF(){
        try{
            service.generarDocumentoPDF();
            return "El documento PDF se ha generado correctamente";
        }catch(Exception e){
            e.printStackTrace();
            return "Error al generar el documento PDF";

        }
    }

}
