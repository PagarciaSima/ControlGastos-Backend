package com.pgs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.pgs.model.EstadoModel;
import com.pgs.repository.IEstadoRepository;

class EstadoServiceImplTest {

    @Mock
    private IEstadoRepository estadoRepository;

    @InjectMocks
    private EstadoServiceImpl estadoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testListar() {
        // Configura datos de prueba
        EstadoModel estado3 = new EstadoModel();
        estado3.setId(3L);
        estado3.setNombre("Pagado");

        EstadoModel estado4 = new EstadoModel();
        estado4.setId(4L);
        estado4.setNombre("No Pagado");

        List<EstadoModel> estados = Arrays.asList(estado3, estado4);

        // Define el objeto Sort explícito
        Sort sort = Sort.by("id").descending();

        // Configura el mock del repositorio
        when(estadoRepository.findAll(sort)).thenReturn(estados);

        // Llama al método del servicio
        List<EstadoModel> resultado = estadoService.listar();

        // Verifica el resultado
        assertEquals(2, resultado.size());
        assertEquals("Pagado", resultado.get(0).getNombre());
        assertEquals("No Pagado", resultado.get(1).getNombre());

        // Verifica que el repositorio fue llamado correctamente
        verify(estadoRepository, times(1)).findAll(sort);
    }
    
    @Test
    void testBuscarPorId() {
        // Configura datos de prueba
        EstadoModel estado = new EstadoModel();
        estado.setId(3L);
        estado.setNombre("Pagado");

        // Configura el mock del repositorio
        when(estadoRepository.findById(3L)).thenReturn(Optional.of(estado));

        // Llama al método del servicio
        EstadoModel resultado = estadoService.buscarPorId(3L);

        // Verifica el resultado
        assertEquals(3L, resultado.getId());
        assertEquals("Pagado", resultado.getNombre());

        // Verifica que el repositorio fue llamado correctamente
        verify(estadoRepository, times(1)).findById(3L);
    }

    @Test
    void testListarParaGastos() {
        // Configura datos de prueba
        EstadoModel estado3 = new EstadoModel();
        estado3.setId(3L);
        estado3.setNombre("Pagado");

        EstadoModel estado4 = new EstadoModel();
        estado4.setId(4L);
        estado4.setNombre("No Pagado");

        List<EstadoModel> estados = Arrays.asList(estado3, estado4);

        // Configura el mock del repositorio
        when(estadoRepository.findByIdIn(Arrays.asList(3L, 4L))).thenReturn(estados);

        // Llama al método del servicio
        List<EstadoModel> resultado = estadoService.listarParaGastos(Arrays.asList(3L, 4L));

        // Verifica el resultado
        assertEquals(2, resultado.size());
        assertEquals("Pagado", resultado.get(0).getNombre());
        assertEquals("No Pagado", resultado.get(1).getNombre());

        // Verifica que el repositorio fue llamado correctamente
        verify(estadoRepository, times(1)).findByIdIn(Arrays.asList(3L, 4L));
    }

    @Test
    void testGuardar() {
        // Configura datos de prueba
        EstadoModel estado = new EstadoModel();
        estado.setId(1L);
        estado.setNombre("Pagado");

        // Llama al método del servicio
        estadoService.guardar(estado);

        // Verifica que el repositorio fue llamado correctamente
        verify(estadoRepository, times(1)).save(estado);
    }

    @Test
    void testEliminar() {
        // Llama al método del servicio
        estadoService.eliminar(1L);

        // Verifica que el repositorio fue llamado correctamente
        verify(estadoRepository, times(1)).deleteById(1L);
    }
}
