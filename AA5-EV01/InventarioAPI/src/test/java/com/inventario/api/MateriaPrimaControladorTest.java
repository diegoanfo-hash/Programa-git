package com.inventario.api;

import com.inventario.api.modelo.MateriaPrima;
import com.inventario.api.repositorio.MateriaPrimaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MateriaPrimaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    @BeforeEach
    void limpiar() {
        repositorio.deleteAll();
    }

    @Test
    void listarVacio() throws Exception {
        mockMvc.perform(get("/api/materias"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void crearYListar() throws Exception {
        String body = "{\"nombre\":\"Acero\",\"unidad\":\"kg\",\"cantidadActual\":100,\"cantidadMinima\":30}";

        mockMvc.perform(post("/api/materias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nombre").value("Acero"));

        mockMvc.perform(get("/api/materias"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerPorId() throws Exception {
        MateriaPrima mp = repositorio.save(new MateriaPrima("Vidrio", "m2", 50, 20));

        mockMvc.perform(get("/api/materias/" + mp.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Vidrio"));
    }

    @Test
    void obtenerNoExistente() throws Exception {
        mockMvc.perform(get("/api/materias/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    void actualizar() throws Exception {
        MateriaPrima mp = repositorio.save(new MateriaPrima("Aluminio", "ml", 80, 25));
        String body = "{\"nombre\":\"Aluminio\",\"unidad\":\"ml\",\"cantidadActual\":60,\"cantidadMinima\":25}";

        mockMvc.perform(put("/api/materias/" + mp.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.cantidadActual").value(60));
    }

    @Test
    void eliminar() throws Exception {
        MateriaPrima mp = repositorio.save(new MateriaPrima("Cobre", "kg", 30, 10));

        mockMvc.perform(delete("/api/materias/" + mp.getId()))
               .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/materias/" + mp.getId()))
               .andExpect(status().isNotFound());
    }
}
