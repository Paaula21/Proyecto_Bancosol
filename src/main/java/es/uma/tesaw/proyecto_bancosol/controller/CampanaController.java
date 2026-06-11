package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
// Asegúrate de importar tu servicio y/o DTO aquí
// import es.uma.tesaw.proyecto_bancosol.service.CampanaService;

import es.uma.tesaw.proyecto_bancosol.service.CampanaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@AllArgsConstructor
public class CampanaController {

    // 1. Declaramos el servicio. Lombok lo inyectará automáticamente gracias al @AllArgsConstructor
    private final CampanaService campanaService;

    @GetMapping("/campanas")
    public String verCampanas (@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            // Solo Administrador (1) y Coordinador (2) pueden ver campañas
            return "redirect:/dashboard";
        }

        // 2. Pedimos la lista al servicio y la guardamos en el modelo con el nombre "campanas"
        // (Asegúrate de que el método de tu servicio se llame listarCampanas o cámbialo aquí)
        model.addAttribute("campanas", campanaService.listarCampanas());

        return "campanas"; // Renderiza campanas.jsp
    }
}