package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dto.CoberturaZonaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO;
import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import es.uma.tesaw.proyecto_bancosol.service.ColaboradoresService;
import es.uma.tesaw.proyecto_bancosol.service.EstablecimientoService;
import es.uma.tesaw.proyecto_bancosol.service.UsuarioService;
import es.uma.tesaw.proyecto_bancosol.service.ZonaGeograficaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {

    private final CampanaService campanaService;
    private final EstablecimientoService establecimientoService;
    private final ColaboradoresService colaboradoresService;
    private final UsuarioService usuarioService;
    private final ZonaGeograficaService zonaGeograficaService;

    @GetMapping("/dashboard")
    public String verDashboard (@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model) {
        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

        model.addAttribute("campanasActivasCount", campanaService.contarCampanasActivas());
        model.addAttribute("tiendasTotales", establecimientoService.contarEstablecimientos());
        model.addAttribute("zonasTotales", zonaGeograficaService.contarZonas());
        model.addAttribute("colaboradoresTotales", colaboradoresService.contarColaboradores());
        model.addAttribute("coordinadoresTotales", usuarioService.contarCoordinadores());

        List<CampanaDTO> proximasCampanas = campanaService.listarProximasCampanasDTO();
        model.addAttribute("proximasCampanas", proximasCampanas);

        List<CoberturaZonaDTO> coberturas = establecimientoService.obtenerCoberturaPorZona();
        model.addAttribute("coberturasZona", coberturas);

        return "administrador";
    }
}