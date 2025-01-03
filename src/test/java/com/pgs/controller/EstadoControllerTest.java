package com.pgs.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pgs.model.EstadoModel;
import com.pgs.service.IEstadoService;

class EstadoControllerTest {

	@Mock
    private IEstadoService estadoService;

    @InjectMocks
    private EstadoController estadoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(estadoController).build();
    }

    @Test
    public void testGetAllEstados() throws Exception {
        // Crea un objeto de prueba
        EstadoModel estado1 = new EstadoModel();
        estado1.setId(3L);
        estado1.setNombre("Pagado");

        EstadoModel estado2 = new EstadoModel();
        estado2.setId(4L);
        estado2.setNombre("No Pagado");

        List<EstadoModel> estados = Arrays.asList(estado1, estado2);

        // Mockea la respuesta del servicio
        when(estadoService.listar()).thenReturn(estados);

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/v1/estados"))
                .andExpect(status().isOk())  // Código de respuesta 200 OK
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].nombre").value("Pagado"))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[1].nombre").value("No Pagado"));
    }

    @Test
    public void testGetAllEstadosGastos() throws Exception {
        // Crea un objeto de prueba
        EstadoModel estado1 = new EstadoModel();
        estado1.setId(3L);
        estado1.setNombre("Pagado");

        EstadoModel estado2 = new EstadoModel();
        estado2.setId(4L);
        estado2.setNombre("No Pagado");

        List<EstadoModel> estados = Arrays.asList(estado1, estado2);

        // Mockea la respuesta del servicio
        when(estadoService.listarParaGastos(Arrays.asList(3L, 4L))).thenReturn(estados);

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/v1/estados-gastos"))
                .andExpect(status().isOk())  // Código de respuesta 200 OK
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].nombre").value("Pagado"))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[1].nombre").value("No Pagado"));
    }
}
