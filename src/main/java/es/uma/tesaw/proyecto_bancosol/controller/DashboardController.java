package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.CoberturaZonaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {

    private final CampanaRepository campanaRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ZonaGeograficaRepository zonaRepository;

    @GetMapping("/dashboard")
    public String verDashboard (@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (user == null) {
            return "redirect:/"; // Protegemos la ruta si no hay sesión
        }

        List<Campana> campanasActivas = campanaRepository.findByEstado("Activa");
        model.addAttribute("campanasActivasCount", campanasActivas.size());
        model.addAttribute("tiendasTotales", establecimientoRepository.count());
        model.addAttribute("zonasTotales", zonaRepository.count());
        model.addAttribute("colaboradoresTotales", colaboradorRepository.count());
        model.addAttribute("coordinadoresTotales", usuarioRepository.countByIdRol(2).size());
        List<Campana> proximasCampanas = campanaRepository.findAll();
        model.addAttribute("proximasCampanas", proximasCampanas);

        List<Object[]> conteoPorZona = establecimientoRepository.countEstablecimientosPorZona();
        List<CoberturaZonaDTO> coberturas = new ArrayList<>();

        if (!conteoPorZona.isEmpty()) {
            // Como viene ordenado DESC, el primer elemento es el máximo
            Long maxTiendas = (Long) conteoPorZona.get(0)[1];

            for (Object[] fila : conteoPorZona) {
                String nombreZona = (String) fila[0];
                Long tiendas = (Long) fila[1];
                // Calculamos el porcentaje
                int porcentaje = (int) Math.round((tiendas.doubleValue() / maxTiendas) * 100);

                coberturas.add(new CoberturaZonaDTO(nombreZona, tiendas, porcentaje));
            }
        }
        model.addAttribute("coberturasZona", coberturas);

        return "administrador";
    }
}
