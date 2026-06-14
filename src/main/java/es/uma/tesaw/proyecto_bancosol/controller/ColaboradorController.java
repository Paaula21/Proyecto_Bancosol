/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.controller;

import java.util.List;

import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.dto.FormularioColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import es.uma.tesaw.proyecto_bancosol.service.ColaboradoresService;
import es.uma.tesaw.proyecto_bancosol.service.ZonaGeograficaService;
import org.springframework.web.bind.annotation.SessionAttribute;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ColaboradorController {

    private final ColaboradoresService colaboradoresService;
    private final ZonaGeograficaService zonaGeograficaService;

    // Listar colaboradores
    @GetMapping("/colaboradores")
    public String listarYGestionarColaboradores(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String accion,
            Model model) {
        if (user == null) {
            return "redirect:/";
        }
        List<ColaboradorDTO> colaboradores = this.colaboradoresService.listarColaboradoresDTO(busqueda, zona);
        List<ZonaGeografica> zonasDisponibles = this.zonaGeograficaService.obtenerTodasLasZonas();

        if (busqueda == null) busqueda = "";
        if (zona == null) zona = "Todas";

        // Gestión de la tabla lateral de información
        String modoPanel = "ninguno";
        Colaborador colaboradorSeleccionado = null;
        ContactoColaborador contactoSeleccionado = null;

        if (id != null && !id.isEmpty()) {
            colaboradorSeleccionado = this.colaboradoresService.obtenerColaboradorEntidad(id).orElse(null);
            if (colaboradorSeleccionado != null) {
                contactoSeleccionado = this.colaboradoresService.obtenerContactoPorColaborador(colaboradorSeleccionado).orElse(null);
                if ("editar".equals(accion)) {
                    modoPanel = "editar";
                } else {
                    modoPanel = "detalle";
                }
            }
        } else if ("nuevo".equals(accion)) {
            modoPanel = "anadir";
        }

        // Pasar todos los datos a la vista .jsp
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("zonasDisponibles", zonasDisponibles);
        model.addAttribute("colaboradorSeleccionado", colaboradorSeleccionado);
        model.addAttribute("contactoSeleccionado", contactoSeleccionado);
        model.addAttribute("modoPanel", modoPanel);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("zona", zona);

        return "colaboradores";
    }

    // Guardar colaborador
    @PostMapping("/colaboradores/guardar")
    public String guardarColaborador(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            FormularioColaboradorDTO nuevoColaborador) {
        if (user == null) {
            return "redirect:/";
        }
        this.colaboradoresService.guardarColaborador(nuevoColaborador);
        return "redirect:/colaboradores";
    }

    // Actualizar colaborador
    @PostMapping("/colaboradores/actualizar")
    public String actualizarColaborador(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            FormularioColaboradorDTO colaboradorEditado) {
        if (user == null) {
            return "redirect:/";
        }
        this.colaboradoresService.guardarColaborador(colaboradorEditado);
        return "redirect:/colaboradores?id=" + colaboradorEditado.getIdColaborador();
    }

    // Eliminar colaborador
    @PostMapping("/colaboradores/eliminar")
    public String eliminarColaboradorCompleto(
            @SessionAttribute(name = "user", required = false) UsuarioDTO user,
            @RequestParam String id) {
        if (user == null) {
            return "redirect:/";
        }
        this.colaboradoresService.eliminarColaboradorCompleto(id);
        return "redirect:/colaboradores";
    }


}