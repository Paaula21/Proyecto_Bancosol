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
@RequestMapping("/voluntarios")
@AllArgsConstructor
public class VoluntariosController {

    private final VoluntariosService voluntariosService;

    @GetMapping({"", "/"})
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
            List<VistaVoluntarioDTO> voluntarios = voluntariosService.listarVoluntarios(null);
            model.addAttribute("voluntarios", voluntarios);

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
                voluntariosService.listarVoluntarios(null));

        return "voluntarios";
    }

    @GetMapping("/nuevo")
    public String doNuevo(@SessionAttribute(name = "user", required = false) Usuario user,
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

        return "RegistroVoluntarios";
    }

    private void cargarDesplegablesFormulario(Model model) {
        model.addAttribute("cadenas", this.voluntariosService);
        /*No se que tengo que poner más, Dani puso esto:
        model.addAttribute("cadenas", this.cadenaService.listarCadenas());
        model.addAttribute("zonas", this.zonaService.listarZonas());
        model.addAttribute("municipios", this.municipioService.listarMunicipios());
        model.addAttribute("localidades", this.localidadService.listarLocalidades());
        model.addAttribute("distritos", this.distritoService.listarDistritos());
        model.addAttribute("coordinadores", this.usuarioService.listarCoordinadores());
        model.addAttribute("capitanes", this.usuarioService.listarCapitanes());
         */
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam("disponibilidad") String disponibilidad) {

        // 1. Guardamos en la base de datos usando tu servicio
        this.voluntariosService.guardarVoluntario(id, nombre, email, telefono, disponibilidad);

        // 2. ¡CLAVE! Redirigimos al listado general mediante la URL, no el nombre del JSP
        return "redirect:/voluntarios";
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