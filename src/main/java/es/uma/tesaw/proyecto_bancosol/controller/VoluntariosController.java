package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class VoluntariosController {

    private final VoluntariosService voluntariosService;

    @GetMapping("/voluntarios")
    public String doInit(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam(required = false) String nombre_completo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String disponibilidad,
            Model model) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        } else {
            List<VistaVoluntarioDTO> voluntarios = voluntariosService.listarVoluntariosFiltrados(null);
            model.addAttribute("voluntarios", voluntarios);

            return "voluntarios";
        }

        /*model.addAttribute("nombre_completo",
                nombre_completo != null ? nombre_completo : "");
        model.addAttribute("email",
                email != null ? email : "");
        model.addAttribute("telefono",
                telefono != null ? telefono : "");
        model.addAttribute("disponibilidad",
                disponibilidad != null ? disponibilidad : "");

        model.addAttribute("voluntarios"); */
    }

    @PostMapping("/filtrar")
    public String doFiltrar(
            @RequestParam(value = "disponibilidad", required = false) List<String> disponibilidad,
            Model model) {

        // Llamamos al nuevo método del servicio que filtra únicamente por disponibilidad
        List<VistaVoluntarioDTO> voluntarioFiltrado = this.voluntariosService.listarVoluntariosPorDisponibilidad(disponibilidad);

        // Enviamos el resultado a la vista bajo el atributo "voluntarios"
        model.addAttribute("voluntarios", voluntarioFiltrado);

        return "voluntarios";
    }

    @GetMapping("/editar")
    public String doEditar(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam("id") Integer id,
            Model model) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

        VistaVoluntarioDTO voluntario =
                voluntariosService.obtenerVoluntario(id).orElse(null);

        model.addAttribute("voluntario", voluntario);
        model.addAttribute("modoPanel",
                voluntario != null ? "editar" : "anadir");

        model.addAttribute("voluntarios",
                voluntariosService.listarVoluntariosFiltrados(null));

        return "voluntarios";
    }

    @GetMapping("/nuevo")
    public String doNuevo(@SessionAttribute(name = "user", required = false) Usuario user, Model model) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

        model.addAttribute("voluntario", null);
        model.addAttribute("modoPanel", "anadir");

        model.addAttribute("voluntarios",
                voluntariosService.listarVoluntariosFiltrados(null));

        return "voluntarios";
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "disponibilidad", required = false) String disponibilidad) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

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
    public String doBorrar(@SessionAttribute(name = "user", required = false) Usuario user, @RequestParam("id") Integer id) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

        voluntariosService.eliminarVoluntarioConPersona(id);

        return "redirect:/voluntarios/";
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}