/*
Ainhoa García Rebollo: 100%
*/

package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.NotificacionService;
import es.uma.tesaw.proyecto_bancosol.service.VoluntariosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/voluntarios")
@AllArgsConstructor
public class VoluntariosController {

    private final VoluntariosService voluntariosService;
    private final NotificacionService notificacionService;

    @GetMapping({"", "/"})
    public String doInit(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            Model model) {

        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        } else {
            List<VistaVoluntarioDTO> voluntarios = voluntariosService.listarVoluntarios(null);
            model.addAttribute("voluntarios", voluntarios);

            // Para mostrar en el header notificaciones sin leer
            model.addAttribute("hayNoLeidas", notificacionService.contarNoLeidas(user.getIdPersona()) > 0);

            return "voluntarios";
        }

    }

    @PostMapping("/filtrar")
    public String doFiltrar(
            @RequestParam(value = "disponibilidad", required = false) String disponibilidad,
            Model model) {

        List<VistaVoluntarioDTO> voluntarioFiltrado = this.voluntariosService.listarVoluntarios(disponibilidad);
        model.addAttribute("voluntarios", voluntarioFiltrado);

        // Mantener la opción elegida del filtro
        model.addAttribute("disponibilidad", disponibilidad);

        model.addAttribute("currentSection", "voluntarios");

        return "voluntarios";
    }


    @GetMapping("/editar")
    public String doEditar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam("id") Integer id,
            Model model) {

        if (user == null) {
            return "redirect:/";
        }

        VistaVoluntarioDTO voluntarioActual = voluntariosService.obtenerVoluntario(id).orElse(null);

        if (voluntarioActual == null) {
            return "redirect:/voluntarios";
        }

        model.addAttribute("voluntarioActual", voluntarioActual);
        model.addAttribute("editando", true);

        return "editarVoluntario";
    }

    @GetMapping("/nuevo")
    public String doNuevo(@SessionAttribute(name = "user", required = false) UsuarioDTO user,
                          @RequestParam(value = "id", required = false) Integer idVoluntario,
                          Model model) {

        boolean isEditando = (idVoluntario != null);

        model.addAttribute("editando", isEditando);
        model.addAttribute("viendo", false);
        model.addAttribute("currentSection", "voluntarios");

        if (isEditando) {
            VoluntarioDTO voluntario = this.voluntariosService.buscarVoluntario(idVoluntario);
            model.addAttribute("voluntarioActual", voluntario);
        }

        this.cargarDesplegablesFormulario(model);

        return "registroVoluntarios";
    }

    private void cargarDesplegablesFormulario(Model model) {
        model.addAttribute("cadenas", this.voluntariosService);
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam("disponibilidad") String disponibilidad) {

        this.voluntariosService.guardarVoluntario(id, nombre, email, telefono, disponibilidad);

        return "redirect:/voluntarios";
    }


    @GetMapping("/borrar")
    public String doBorrar(@SessionAttribute(name = "user", required = false) UsuarioDTO user,
                           @RequestParam("id") Integer id) {

        if (user == null) {
            return "redirect:/";
        }

        try {
            voluntariosService.eliminarVoluntarioConPersona(id);
        } catch (Exception e) {
            System.err.println("Error al intentar borrar el voluntario con ID: " + id);
            e.printStackTrace();
        }

        return "redirect:/voluntarios";
    }
}