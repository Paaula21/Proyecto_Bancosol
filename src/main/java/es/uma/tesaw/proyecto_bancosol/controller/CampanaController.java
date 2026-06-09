package es.uma.tesaw.proyecto_bancosol.controller;

import es.uma.tesaw.proyecto_bancosol.entities.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@AllArgsConstructor
public class CampanaController {

    @GetMapping("/campanas")
    public String verCampanas (@SessionAttribute(name = "user", required = false) Usuario user, Model model) {
        if (user == null || (user.getRol().getIdRol() != 1 && user.getRol().getIdRol() != 2)) {
            // Solo Administrador (1) y Coordinador (2) pueden ver campañas
            return "redirect:/dashboard";
        }

        return "campana"; // Renderiza campana.jsp
    }
}
