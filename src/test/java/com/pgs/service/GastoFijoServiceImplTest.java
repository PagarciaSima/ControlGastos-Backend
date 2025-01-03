package com.pgs.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.pgs.model.GastoFijoModel;
import com.pgs.repository.IGastoFijoResitory;

class GastoFijoServiceImplTest {

    @InjectMocks
    private GastoFijoServiceImpl gastoFijoService;

    @Mock
    private IGastoFijoResitory gastoFijoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPorMesYanio() {
        // Arrange
        Integer mes = 5;
        Integer anio = 2023;
        List<GastoFijoModel> expectedGastos = Arrays.asList(new GastoFijoModel(), new GastoFijoModel());

        when(gastoFijoRepository.findAllByMonth(mes, anio)).thenReturn(expectedGastos);

        // Act
        List<GastoFijoModel> result = gastoFijoService.listarPorMesYanio(mes, anio);

        // Assert
        assertEquals(expectedGastos, result);
        verify(gastoFijoRepository).findAllByMonth(mes, anio);
    }

    @Test
    void testListar() {
        // Arrange
        List<GastoFijoModel> expectedGastos = Arrays.asList(new GastoFijoModel(), new GastoFijoModel());

        when(gastoFijoRepository.findAll(Sort.by("id").descending())).thenReturn(expectedGastos);

        // Act
        List<GastoFijoModel> result = gastoFijoService.listar();

        // Assert
        assertEquals(expectedGastos, result);
        verify(gastoFijoRepository).findAll(Sort.by("id").descending());
    }

    @Test
    void testGuardar() {
        // Arrange
        GastoFijoModel gastoFijo = new GastoFijoModel();

        // Act
        gastoFijoService.guardar(gastoFijo);

        // Assert
        verify(gastoFijoRepository).save(gastoFijo);
    }

    @Test
    void testBuscarPorId_Encontrado() {
        // Arrange
        Long id = 1L;
        GastoFijoModel expectedGasto = new GastoFijoModel();
        expectedGasto.setId(id);

        when(gastoFijoRepository.findById(id)).thenReturn(Optional.of(expectedGasto));

        // Act
        GastoFijoModel result = gastoFijoService.buscarPorId(id);

        // Assert
        assertEquals(expectedGasto, result);
        verify(gastoFijoRepository).findById(id);
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        // Arrange
        Long id = 1L;

        when(gastoFijoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        GastoFijoModel result = gastoFijoService.buscarPorId(id);

        // Assert
        assertNull(result);
        verify(gastoFijoRepository).findById(id);
    }

    @Test
    void testEliminar() {
        // Arrange
        Long id = 1L;

        // Act
        gastoFijoService.eliminar(id);

        // Assert
        verify(gastoFijoRepository).deleteById(id);
    }
}
