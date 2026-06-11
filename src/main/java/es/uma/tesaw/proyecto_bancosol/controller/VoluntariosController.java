package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/voluntarios")
public class VoluntariosController {

    private final VoluntariosService voluntariosService;

    @GetMapping({ "/"})
    public String doInit(
            @RequestParam(required = false) String nombre_completo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String disponibilidad,
            Model model) {

        List<VistaVoluntarioDTO> voluntarios =
                voluntariosService.listarVoluntariosFiltrados(
                        nombre_completo,
                        email,
                        telefono,
                        disponibilidad
                );

        model.addAttribute("nombre_completo",
                nombre_completo != null ? nombre_completo : "");
        model.addAttribute("email",
                email != null ? email : "");
        model.addAttribute("telefono",
                telefono != null ? telefono : "");
        model.addAttribute("disponibilidad",
                disponibilidad != null ? disponibilidad : "");

        model.addAttribute("voluntarios", voluntarios);

        return "voluntarios";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(
            @RequestParam(required = false) String nombre_completo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String disponibilidad) {

        return "redirect:/voluntarios/?nombre_completo="
                + emptyIfNull(nombre_completo)
                + "&email=" + emptyIfNull(email)
                + "&telefono=" + emptyIfNull(telefono)
                + "&disponibilidad=" + emptyIfNull(disponibilidad);
    }

    @GetMapping("/editar")
    public String doEditar(
            @RequestParam("id") Integer id,
            Model model) {

        VistaVoluntarioDTO voluntario =
                voluntariosService.obtenerVoluntario(id).orElse(null);

        model.addAttribute("voluntario", voluntario);
        model.addAttribute("modoPanel",
                voluntario != null ? "editar" : "anadir");

        model.addAttribute("voluntarios",
                voluntariosService.listarVoluntariosFiltrados(
                        null, null, null, null));

        return "voluntarios";
    }

    @GetMapping("/nuevo")
    public String doNuevo(Model model) {

        model.addAttribute("voluntario", null);
        model.addAttribute("modoPanel", "anadir");

        model.addAttribute("voluntarios",
                voluntariosService.listarVoluntariosFiltrados(
                        null, null, null, null));

        return "voluntarios";
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "disponibilidad", required = false) String disponibilidad) {

        voluntariosService.guardarVoluntario(
                id,
                nombre,
                email,
                telefono,
                disponibilidad
        );

        return "redirect:/voluntarios/";
    }

    @GetMapping("/borrar")
    public String doBorrar(@RequestParam("id") Integer id) {

        voluntariosService.eliminarVoluntarioConPersona(id);

        return "redirect:/voluntarios/";
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}