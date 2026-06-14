package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExportarService {
    private final CampanaRepository campanaRepository;
    private final CadenaRepository cadenaRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final ColaboradorRepository colaboradoresRepository;
    private final VoluntarioRepository voluntarioRepository;


    //Hay que poner el IOException por si hay fallo al escribir en el archivo
    public ByteArrayInputStream generarExcel(List<String> tablas) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            //Creación del archivo a exportar
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            //Cada una de las hojas a exportar
            if (tablas != null && !tablas.isEmpty()) {
                if (tablas.contains("campana")) {
                    crearHojaCampanas(workbook, headerStyle);
                }
                if (tablas.contains("cadena")) {
                    crearHojaCadenas(workbook, headerStyle);
                }
                if (tablas.contains("establecimiento")) {
                    crearHojaEstablecimientos(workbook, headerStyle);
                }
                if (tablas.contains("colaborador")) {
                    crearHojaColaboradores(workbook, headerStyle);
                }
                if (tablas.contains("voluntario")) {
                    crearHojaVoluntarios(workbook, headerStyle);
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private void crearHojaCampanas(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Campañas");
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID Campaña", "Nombre", "Fecha Inicio", "Fecha Fin", "Estado"};

        //Por cada uno de los valores, se añade a su columna correspondiente el título establecido
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        //Guarda cada conjunto en su fila
        int rowIdx = 1;
        for (Campana c : campanaRepository.findAll()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(c.getIdCampana() != null ? c.getIdCampana() : "");
            row.createCell(1).setCellValue(c.getNombreCampana() != null ? c.getNombreCampana() : "");
            row.createCell(2).setCellValue(c.getFechaInicio() != null ? c.getFechaInicio().toString() : "");
            row.createCell(3).setCellValue(c.getFechaFin() != null ? c.getFechaFin().toString() : "");
            row.createCell(4).setCellValue(c.getEstado() != null ? c.getEstado() : "");
        }
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);
    }

    private void crearHojaCadenas(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Cadenas");
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID Cadena", "Nombre"};

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Cadena c : cadenaRepository.findAll()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(c.getIdCadena() != null ? c.getIdCadena() : "");
            row.createCell(1).setCellValue(c.getNombreCadena() != null ? c.getNombreCadena() : "");
        }
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);
    }

    private void crearHojaEstablecimientos(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Establecimientos");
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID Establecimiento", "Nombre", "Cadena", "Lineales", "Dirección", "CP", "Municipio"};

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Establecimiento e : establecimientoRepository.ExportarEstablecimientos()) {
            String direccion = "";
            if (e.getDireccion() != null) {
                direccion = (e.getDireccion().getNombreVia() != null ? e.getDireccion().getNombreVia() + "" : "") +
                        (e.getDireccion().getNumero() != null ? ", " + e.getDireccion().getNumero() : "");
            }
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getIdEstablecimiento() != null ? e.getIdEstablecimiento().toString() : "");
            row.createCell(1).setCellValue(e.getNombreResena() != null ? e.getNombreResena() : "");
            row.createCell(2).setCellValue(e.getCadena() != null ? e.getCadena().getNombreCadena() : "");
            row.createCell(3).setCellValue(e.getLineales() != null ? e.getLineales().toString() : "");
            row.createCell(4).setCellValue(direccion);
            row.createCell(5).setCellValue(e.getDireccion().getCp().getCodigo() != null ? e.getDireccion().getCp().getCodigo() : "");
            row.createCell(6).setCellValue(e.getDireccion().getCp().getDivision().getZona().getNombreZona() != null ? e.getDireccion().getCp().getDivision().getZona().getNombreZona() : "");
        }
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);
    }

    private void crearHojaColaboradores(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Colaboradores");
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID Colaborador", "Nombre", "Dirección", "Nombre Contacto", "Email Contacto", "Teléfono Contacto", "Observaciones"};

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Colaborador c : colaboradoresRepository.ExportarColaboradores()) {
            String direccion = (c.getDireccion().getNombreVia() != null ? c.getDireccion().getNombreVia() + "" : "") +
                               (c.getDireccion().getNumero() != null ? ", " + c.getDireccion().getNumero() : "") ;
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(c.getIdColaborador() != null ? c.getIdColaborador() : "");
            row.createCell(1).setCellValue(c.getNombreColaborador() != null ? c.getNombreColaborador() : "");
            row.createCell(2).setCellValue(direccion);
            row.createCell(3).setCellValue(c.getContacto() != null
                    && c.getContacto().getPersona() != null
                    && c.getContacto().getPersona().getNombreCompleto() != null ? c.getContacto().getPersona().getNombreCompleto() : "");
            row.createCell(4).setCellValue(c.getContacto() != null
                    && c.getContacto().getPersona() != null
                    && c.getContacto().getPersona().getEmail() != null? c.getContacto().getPersona().getEmail() : "");
            row.createCell(5).setCellValue(c.getContacto() != null
                    && c.getContacto().getPersona() != null
                    && c.getContacto().getPersona().getTelefono() != null? c.getContacto().getPersona().getTelefono() : "");
            row.createCell(6).setCellValue(c.getObservaciones() != null ? c.getObservaciones() : "");
        }
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);
    }

    private void crearHojaVoluntarios(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Voluntarios");
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID Voluntario", "Nombre Persona", "Preferencia Horario"};

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Voluntario v : voluntarioRepository.ExportarVoluntarios()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(v.getIdVoluntario() != null ? String.valueOf(v.getIdVoluntario()) : "");

            String nombrePersona = (v.getPersona() != null && v.getPersona().getNombreCompleto() != null) ? v.getPersona().getNombreCompleto() : "";
            row.createCell(1).setCellValue(nombrePersona);

            row.createCell(2).setCellValue(v.getDisponibilidad() != null ? v.getDisponibilidad() : "");

        }
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);
    }
}
