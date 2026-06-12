package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO;
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
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String idCampana,
            @RequestParam(required = false) String idCadena,
            Model model) {

        List<CadenaDTO> cadenas = cadenaService.listarCadenas(nombre, idCampana);
        model.addAttribute("cadenas", cadenas);
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());
        model.addAttribute("campanas", campanaService.listarCampanasDTO());

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

        if (nombre == null) nombre = "";
        if (idCampana == null) idCampana = "";
        model.addAttribute("nombre", nombre);
        model.addAttribute("idCampana", idCampana);

        return "cadenas";
    }

    protected String editarCrear(String idCadena, Model model) {
        CadenaDTO cadena = cadenaService.buscarCadena(idCadena);
        model.addAttribute("cadenas", cadenaService.listarCadenas());
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());
        model.addAttribute("campanas", campanaService.listarCampanasDTO());
        model.addAttribute("cadenaSeleccionada", cadena);
        model.addAttribute("modoPanel", idCadena == null ? "anadir" : "editar");
        model.addAttribute("nombre", "");
        model.addAttribute("idCampana", "");
        return "cadenas";
    }

    @PostMapping("/anadir")
    public String doAnadir(Model model) {
        return editarCrear(null, model);
    }

    @GetMapping("/editar")
    public String doEditar(@RequestParam("idCadena") String idCadena, Model model) {
        return editarCrear(idCadena, model);
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @RequestParam(required = false) String idCadena,
            @RequestParam String nombreCadena,
            @RequestParam(required = false) List<String> campanasIds) {
        cadenaService.guardarCadena(idCadena, nombreCadena, campanasIds);
        return "redirect:/cadenas";
    }

    @GetMapping("/borrar")
    public String doBorrar(@RequestParam("idCadena") String idCadena) {
        cadenaService.borrarCadena(idCadena);
        return "redirect:/cadenas";
    }
}
