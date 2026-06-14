package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.service.ExportarService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class ExportarController {
    private final ExportarService exportarService;

    @PostMapping("/perfil/exportar")
    public ResponseEntity<InputStreamResource> descargarExcelCompleto(
            @RequestParam(value = "tablas", required = false) List<String> tablasSeleccionadas) {
        try {
            ByteArrayInputStream in = exportarService.generarExcel(tablasSeleccionadas);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=DatosBancosol.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
