package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/voluntarios")
public class VoluntariosController {

    private final VoluntariosService voluntariosService;

    @GetMapping("/")
    public String doInit(
            @SessionAttribute(name = "user", required = false) Usuario user,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        List<VoluntarioDTO> voluntarios =
                voluntariosService.listarVoluntarios();

        model.addAttribute("voluntarios", voluntarios);

        return "voluntarios";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(
            @RequestParam(value = "filtro", required = false)
            Model model) {

        List<VoluntarioDTO> voluntarios =
                voluntariosService.listarVoluntarios();

        model.addAttribute("voluntarios", voluntarios);

        return "voluntarios_table";
    }

    protected String editarCrear(Integer id, Model model) {

        VoluntarioDTO voluntario = null;

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

            @RequestParam(value = "id", required = false)
            Integer id,

            @RequestParam("nombre")
            String nombre,

            @RequestParam(value = "email", required = false)
            String email,

            @RequestParam(value = "telefono", required = false)
            String telefono,

            @RequestParam(value = "disponibilidad", required = false)
            String disponibilidad) {

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