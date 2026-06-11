package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO; // CAMBIADO: Importamos la vista DTO
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
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

    @GetMapping("/")
    public String doInit(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam(required = false) String nombre_completo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String disponibilidad,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        // CAMBIADO: Ahora devuelve una lista de VistaVoluntarioDTO
        List<VistaVoluntarioDTO> voluntarios =
                voluntariosService.listarVoluntariosFiltrados(nombre_completo, email, telefono, disponibilidad);

        model.addAttribute("nombre_completo", nombre_completo);
        model.addAttribute("email", email);
        model.addAttribute("telefono", telefono);
        model.addAttribute("disponibilidad", disponibilidad);

        model.addAttribute("voluntarios", voluntarios);

        return "voluntarios";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(
            @RequestParam(required = false) String nombre_completo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String disponibilidad,
            Model model) {

        // CAMBIADO: Ahora devuelve una lista de VistaVoluntarioDTO
        List<VistaVoluntarioDTO> voluntarios =
                voluntariosService.listarVoluntariosFiltrados(nombre_completo, email, telefono, disponibilidad);

        model.addAttribute("voluntarios", voluntarios);

        return "voluntarios_table";
    }

    protected String editarCrear(Integer id, Model model) {

        // CAMBIADO: Usamos VistaVoluntarioDTO para que al editar también aparezcan rellenos los campos de texto
        VistaVoluntarioDTO voluntario = null;

        if (id != null) {
            voluntario = voluntariosService.obtenerVoluntario(id).orElse(null);
        }

        model.addAttribute("voluntario", voluntario);

        return "voluntario_edit";
    }

    @PostMapping("/anadir")
    public String doAnadir(
            @SessionAttribute(name = "user", required = false) Usuario user,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        return editarCrear(null, model);
    }

    @GetMapping("/editar")
    public String doEditar(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam("id") Integer id,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        return editarCrear(id, model);
    }

    @GetMapping("/borrar")
    public String doBorrar(
            @SessionAttribute(name = "user", required = false) Usuario user,
            @RequestParam("id") Integer id) {

        if (user == null) {
            return "redirect:/";
        }

        voluntariosService.eliminarVoluntarioConPersona(id);

        return "redirect:/voluntarios/";
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
}