package com.inventario.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador para la pagina de inicio
// Autor: Diego Andrés Fonseca
@Controller
public class InicioControlador {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/materias";
    }
}
