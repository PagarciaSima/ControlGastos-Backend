package com.pgs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.GastosFijosRequestDto;
import com.pgs.model.EstadoModel;
import com.pgs.model.GastoFijoModel;
import com.pgs.model.ProveedorModel;
import com.pgs.service.IComunService;
import com.pgs.service.IEstadoService;
import com.pgs.service.IGastoFijoService;
import com.pgs.service.IProveedorService;

public class GastoFijoControllerTest {

    @InjectMocks
    private GastoFijoController gastoFijoController;

    @Mock
    private IGastoFijoService gastoFijoService;

    @Mock
    private IComunService comunService;
    
    @Mock
    private IProveedorService proveedorService;
    
    @Mock
    private IEstadoService estadoService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGastosFijosMesEnCurso() {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        Date fecha = java.sql.Date.valueOf(currentDate);
        
        // Mock de Estado y Proveedor
        EstadoModel estado = new EstadoModel();
        estado.setId(3L);
        estado.setNombre("Pagado");
        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setId(1L);
        proveedor.setNombre("Mi proveedor");

        GastoFijoModel gasto1 = new GastoFijoModel("Alquiler", 500L, fecha, estado, proveedor);
        GastoFijoModel gasto2 = new GastoFijoModel("Luz", 100L, fecha, estado, proveedor);
        List<GastoFijoModel> gastosFijos = Arrays.asList(gasto1, gasto2);
        
        when(gastoFijoService.listarPorMesYanio(currentDate.getMonthValue(), currentDate.getYear())).thenReturn(gastosFijos);

        // Act
        ResponseEntity<?> response = gastoFijoController.getAllGastosFijosMesEnCurso();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> result = (List<?>) response.getBody();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllGastosFijosPorMes() {
        // Arrange
        Integer mes = 6;
        LocalDate currentDate = LocalDate.now();
        Date fecha = java.sql.Date.valueOf(currentDate);
        
        // Mock de Estado y Proveedor
        EstadoModel estado = new EstadoModel();
        estado.setId(3L);
        estado.setNombre("Pagado");
        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setId(1L);
        proveedor.setNombre("Mi proveedor");

        GastoFijoModel gasto = new GastoFijoModel("Luz", 100L, fecha, estado, proveedor);
        List<GastoFijoModel> gastosFijos = Arrays.asList(gasto);

        when(gastoFijoService.listarPorMesYanio(mes, currentDate.getYear())).thenReturn(gastosFijos);

        // Act
        ResponseEntity<?> response = gastoFijoController.getAllGastosFijosPorMes(mes);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> result = (List<?>) response.getBody();
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllGastosFijosPorMes_InvalidMonth() {
        // Arrange
        Integer mes = 13; // Invalid month
        String errorMessage = "El mes introducido no es válido";

        // Hacer mock para que comunService devuelva un ResponseEntity<ErrorResponse>
        when(comunService.getResponseEntity(eq(HttpStatus.NOT_FOUND), eq(errorMessage)))
                .thenAnswer(invocation -> {
                    // Retornar un ResponseEntity<ErrorResponse>
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
                });

        // Act
        ResponseEntity<?> response = gastoFijoController.getAllGastosFijosPorMes(mes);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody()); // Obtener el mensaje del ErrorResponse
    }



    @Test
    void testGetAllGastosFijosPorMes_ValidMonth() {
        // Arrange
        Integer mes = 6; // Valid month
        LocalDate currentDate = LocalDate.now();
        Date fecha = java.sql.Date.valueOf(currentDate);

        // Mock de Estado y Proveedor
        EstadoModel estado = new EstadoModel();
        ProveedorModel proveedor = new ProveedorModel();

        GastoFijoModel gasto = new GastoFijoModel("Luz", 100L, fecha, estado, proveedor);
        List<GastoFijoModel> gastosFijos = Arrays.asList(gasto);

        when(gastoFijoService.listarPorMesYanio(mes, currentDate.getYear())).thenReturn(gastosFijos);

        // Act
        ResponseEntity<?> response = gastoFijoController.getAllGastosFijosPorMes(mes);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> result = (List<?>) response.getBody();
        assertEquals(1, result.size());
    }
    
    @Test
    void testPostGastosFijos_CreacionExitosa() {
        // Arrange
        GastosFijosRequestDto dto = new GastosFijosRequestDto();
        dto.setNombre("Test");
        dto.setMonto(1000L);
        dto.setProveedoresId(1L);

        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setId(dto.getProveedoresId());

        EstadoModel estado = new EstadoModel();
        estado.setId(ControlGastosConstants.ESTADO_NO_PAGADO); // o lo que sea necesario

        BindingResult bindingResult = new DirectFieldBindingResult(dto, "gastosFijosRequestDto");

        // Simulamos que el proveedor existe
        when(proveedorService.buscarPorId(dto.getProveedoresId())).thenReturn(proveedor);
        // Simulamos que el estado existe
        when(estadoService.buscarPorId(ControlGastosConstants.ESTADO_NO_PAGADO)).thenReturn(estado);
        // Simulamos que la respuesta de comunService es correcta
        when(comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_CREAR_REGISTRO))
        .thenAnswer(invocation -> {
            // Retornar un ResponseEntity<ErrorResponse>
            return ResponseEntity.status(HttpStatus.CREATED).body(ControlGastosConstants.EXITO_CREAR_REGISTRO);
        });

        // Act
        ResponseEntity<?> response = gastoFijoController.postGastosFijos(dto, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ControlGastosConstants.EXITO_CREAR_REGISTRO, response.getBody());
    }
    
    
   


}
