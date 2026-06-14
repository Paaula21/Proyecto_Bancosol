package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tiendas")
public class EstablecimientoController {

    private final EstablecimientoService establecimientoService;
    private final CadenaService cadenaService;
    private final CampanaService campanaService;
    private final ZonaGeograficaService zonaGeograficaService;
    private final UsuarioService usuarioService;
    private final NotificacionService notificacionService;

    @GetMapping("")
    public String doInit(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) String idCadena,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String idCampana,
            @RequestParam(required = false) String tipoVia,
            @RequestParam(required = false) String nombreVia,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String localidad,
            @RequestParam(required = false) Integer idZona,
            @RequestParam(required = false) String coordinador,
            @RequestParam(required = false) Integer idTienda,
            Model model) {
        if (user == null) return "redirect:/";

        List<EstablecimientoDTO> tiendas = establecimientoService.listarTiendas(
                idCadena, nombre, idCampana, tipoVia, nombreVia, codigo, localidad, idZona, coordinador);
        model.addAttribute("tiendas", tiendas);
        cargarDesplegablesTiendas(model);

        String modoPanel = "ninguno";
        EstablecimientoDTO tiendaSeleccionada = null;

        if (idTienda != null) {
            tiendaSeleccionada = establecimientoService.buscarTienda(idTienda);
            if (tiendaSeleccionada != null && tiendaSeleccionada.getIdEstablecimiento() != null) {
                modoPanel = "detalle";
            }
        }

        model.addAttribute("tiendaSeleccionada", tiendaSeleccionada);
        model.addAttribute("modoPanel", modoPanel);

        model.addAttribute("idCadena", idCadena != null ? idCadena : "");
        model.addAttribute("nombre", nombre != null ? nombre : "");
        model.addAttribute("idCampana", idCampana != null ? idCampana : "");
        model.addAttribute("tipoVia", tipoVia != null ? tipoVia : "");
        model.addAttribute("nombreVia", nombreVia != null ? nombreVia : "");
        model.addAttribute("codigo", codigo != null ? codigo : "");
        model.addAttribute("localidad", localidad != null ? localidad : "");
        model.addAttribute("idZona", idZona);
        model.addAttribute("coordinador", coordinador != null ? coordinador : "");

        // Para mostrar en el header notificaciones sin leer
        model.addAttribute("hayNoLeidas", notificacionService.contarNoLeidas(user.getIdPersona()) > 0);

        return "tienda";
    }

    private void cargarDesplegablesTiendas(Model model) {
        model.addAttribute("todasCadenas", cadenaService.listarCadenas());
        model.addAttribute("campanas", campanaService.listarCampanasDTO());
        model.addAttribute("zonas", zonaGeograficaService.obtenerTodasLasZonas());
        model.addAttribute("coordinadores", usuarioService.listarCoordinadores());
    }

    private String editarCrear(Integer idTienda, Model model) {
        EstablecimientoDTO tienda = establecimientoService.buscarTienda(idTienda);
        model.addAttribute("tiendas", establecimientoService.listarTiendas());
        cargarDesplegablesTiendas(model);
        model.addAttribute("tiendaSeleccionada", tienda);
        model.addAttribute("modoPanel", idTienda == null ? "anadir" : "editar");
        model.addAttribute("idCadena", "");
        model.addAttribute("nombre", "");
        model.addAttribute("idCampana", "");
        model.addAttribute("tipoVia", "");
        model.addAttribute("nombreVia", "");
        model.addAttribute("codigo", "");
        model.addAttribute("localidad", "");
        model.addAttribute("idZona", (Object) null);
        model.addAttribute("coordinador", "");
        return "tienda";
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
            @RequestParam("idTienda") Integer idTienda,
            Model model) {
        if (user == null) return "redirect:/";
        return editarCrear(idTienda, model);
    }

    @PostMapping("/guardar")
    public String doGuardar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) Integer idEstablecimiento,
            @RequestParam String idCadena,
            @RequestParam String nombreResena,
            @RequestParam(required = false) Integer lineales,
            @RequestParam String tipoVia,
            @RequestParam String nombreVia,
            @RequestParam(required = false) String numero,
            @RequestParam String codigo,
            @RequestParam String localidad,
            @RequestParam Integer idZona) {
        if (user == null) return "redirect:/";
        establecimientoService.guardarTienda(idEstablecimiento, idCadena, nombreResena,
                lineales, tipoVia, nombreVia, numero, codigo, localidad, idZona);
        return "redirect:/tiendas";
    }

    @GetMapping("/borrar")
    public String doBorrar(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam("idTienda") Integer idTienda) {
        if (user == null) return "redirect:/";
        establecimientoService.borrarTienda(idTienda);
        return "redirect:/tiendas";
    }
}
