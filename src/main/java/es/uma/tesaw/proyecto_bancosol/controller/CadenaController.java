package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.CadenaService;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cadenas")
public class CadenaController {

    private final CadenaService cadenaService;
    private final CampanaService campanaService;

    @GetMapping("")
    public String doInit(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String idCampana,
            @RequestParam(required = false) String idCadena,
            Model model) {
        if (user == null) return "redirect:/";

        List<CadenaDTO> cadenas = cadenaService.listarCadenas(nombre, idCampana);
        model.addAttribute("cadenas", cadenas);
        cargarDesplegablesCadenas(model);

        String modoPanel = "ninguno";
        CadenaDTO cadenaSeleccionada = null;

        if (idCadena != null && !idCadena.isEmpty()) {
            cadenaSeleccionada = cadenaService.buscarCadena(idCadena);
            if (cadenaSeleccionada != null) {
                modoPanel = "detalle";
            }
        }

        model.addAttribute("cadenaSeleccionada", cadenaSeleccionada);
        model.addAttribute("modoPanel", modoPanel);

        model.addAttribute("nombre", nombre != null ? nombre : "");
        model.addAttribute("idCampana", idCampana != null ? idCampana : "");

        return "cadenas";
    }

    private void cargarDesplegablesCadenas(Model model) {
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());
        model.addAttribute("campanas", campanaService.listarCampanasDTO());
    }

    private String editarCrear(String idCadena, Model model) {
        CadenaDTO cadena = cadenaService.buscarCadena(idCadena);
        model.addAttribute("cadenas", cadenaService.listarCadenas());
        cargarDesplegablesCadenas(model);
        model.addAttribute("cadenaSeleccionada", cadena);
        model.addAttribute("modoPanel", idCadena == null ? "anadir" : "editar");
        model.addAttribute("nombre", "");
        model.addAttribute("idCampana", "");
        return "cadenas";
    }

    @PostMapping("/anadir")
    public String doAnadir(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            Model model) {
        if (user == null) return "redirect:/";
        return editarCrear(null, model);
    }

    @GetMapping("/editar")
    public String doEditar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam("idCadena") String idCadena,
            Model model) {
        if (user == null) return "redirect:/";
        return editarCrear(idCadena, model);
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) String idCadena,
            @RequestParam String nombreCadena,
            @RequestParam(required = false) List<String> campanasIds) {
        if (user == null) return "redirect:/";
        cadenaService.guardarCadena(idCadena, nombreCadena, campanasIds);
        return "redirect:/cadenas";
    }

    @GetMapping("/borrar")
    public String doBorrar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam("idCadena") String idCadena) {
        if (user == null) return "redirect:/";
        cadenaService.borrarCadena(idCadena);
        return "redirect:/cadenas";
    }
}
