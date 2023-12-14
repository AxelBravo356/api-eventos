package com.ipn.mx.apieventos.modelo.services;

import com.ipn.mx.apieventos.modelo.entidades.Asistente;
import com.ipn.mx.apieventos.modelo.entidades.Evento;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class InformacionService {

    private final AsistenteService asistenteService;
    private final EventoService eventoService;

    @Autowired
    public InformacionService(AsistenteService asistenteService, EventoService eventoService) {
        this.asistenteService = asistenteService;
        this.eventoService = eventoService;
    }

    public void generarDocumentoPDF() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("./pdf/reporte.pdf"));
            document.open();

            // Agregar encabezado
            Paragraph encabezado = new Paragraph("Información de Asistentes y Eventos", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);

            // Agregar información de Asistentes
            List<Asistente> asistentes = asistenteService.findAll();
            agregarInformacionAsistentes(document, asistentes);

            // Agregar salto de página
            document.newPage();

            // Agregar información de Eventos
            List<Evento> eventos = eventoService.findAll();
            agregarInformacionEventos(document, eventos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void agregarInformacionAsistentes(Document document, List<Asistente> asistentes) throws DocumentException {
        Paragraph tituloAsistentes = new Paragraph("Asistentes:", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
        tituloAsistentes.setAlignment(Element.ALIGN_LEFT);
        document.add(tituloAsistentes);

        for (Asistente asistente : asistentes) {
            Paragraph infoAsistente = new Paragraph(
                    "ID: " + asistente.getIdAsistente() +
                            ", Nombre: " + asistente.getNombre() +
                            ", Email: " + asistente.getEmail() +
                            ", Evento: " + (asistente.getIdEvento() != null ? asistente.getIdEvento().getNombreEvento() : "N/A")
            );
            document.add(infoAsistente);
        }
    }

    private void agregarInformacionEventos(Document document, List<Evento> eventos) throws DocumentException {
        Paragraph tituloEventos = new Paragraph("Eventos:", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
        tituloEventos.setAlignment(Element.ALIGN_LEFT);
        document.add(tituloEventos);

        for (Evento evento : eventos) {
            Paragraph infoEvento = new Paragraph(
                    "ID: " + evento.getIdEvento() +
                            ", Nombre: " + evento.getNombreEvento() +
                            ", Descripción: " + evento.getDescripcionEvento() +
                            ", Fecha Creación: " + evento.getFechaCreacion()
            );
            document.add(infoEvento);
        }
    }
}

