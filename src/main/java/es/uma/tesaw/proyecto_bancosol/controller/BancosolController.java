package es.uma.tesaw.proyecto_bancosol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BancosolController {

    @GetMapping("/Colaborators")
    public String mostrarColaboradores(){
        return "Colaboradores";
    }

    @GetMapping("/")
    public String mostrarLogin(){
        return "login";
    }
}

