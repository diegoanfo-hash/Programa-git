package com.inventario.controlador;

import com.inventario.modelo.MateriaPrima;
import com.inventario.servicio.MateriaPrimaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/materias")
public class MateriaPrimaControlador {

    @Autowired
    private MateriaPrimaServicio servicio;

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("materias", servicio.obtenerTodos());
        return "materias/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model modelo) {
        modelo.addAttribute("materia", new MateriaPrima());
        modelo.addAttribute("titulo", "Nueva Materia Prima");
        return "materias/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEdicion(@PathVariable Long id, Model modelo) {
        Optional<MateriaPrima> mp = servicio.obtenerPorId(id);
        if (mp.isEmpty()) {
            return "redirect:/materias";
        }
        modelo.addAttribute("materia", mp.get());
        modelo.addAttribute("titulo", "Editar Materia Prima");
        return "materias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute MateriaPrima mp) {
        servicio.guardar(mp);
        return "redirect:/materias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/materias";
    }
}
