package com.pixelpear.perfulandia.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.HttpStatus;

import com.pixelpear.perfulandia.controller.ProductoController;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class PerfulandiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Test
    void testObtenerStock() throws Exception {
        // Given
        String URL = "/inventario/stockInventario";

        Producto producto1 = new Producto(1L, "Perfume1", 111.1, 11);
        Producto producto2 = new Producto(2L, "Perfume2", 222.2, 22);
        
        List<Producto> productos = List.of(producto1, producto2);

        when(productoService.mostrarStock()).thenReturn(productos);

        //When
        MvcResult response = mockMvc.perform(get(URL)).andReturn();

        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        //Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();

        assertEquals(HttpStatus.OK.value(),status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        //Given
        String URL = "/inventario/obtenerProducto?idProducto=1";
        Producto producto = new Producto(1L, "Perfume1", 111.1, 11);

        when(productoService.mostrarProductoPorId(1L)).thenReturn(java.util.Optional.of(producto));

        //When
        MvcResult response = mockMvc.perform(get(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());
        
        //Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test
    void testObtenerProductoPorId_Invalido_NoEncontrado() throws Exception {
        //Given
        String URL = "/inventario/obtenerProducto?idProducto=500";

        when(productoService.mostrarProductoPorId(500L)).thenReturn(java.util.Optional.empty());

        //When
        MvcResult response = mockMvc.perform(get(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());
        
        //Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.NOT_FOUND.value(), status, "El estado de debería ser 404.");
        assertEquals("Producto no encontrado.", body);
    }

    @Test
    void testActualizarStock() throws Exception {
        // Given
        String URL = "/inventario/actualizarStock?idProducto=1&cantidad=5";
        
        Producto productoActualizado = new Producto(1L, "Perfume1", 111.1, 10 + 5);

        when(productoService.actualizarStock(1L, 5)).thenReturn(productoActualizado);

        // When
        MvcResult response = mockMvc.perform(put(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test
    void testActualizarStock_IdCero() throws Exception {
        // Given
        String URL = "/inventario/actualizarStock?idProducto=0&cantidad=5";

        // When
        MvcResult response = mockMvc.perform(put(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado de la respuesta debería ser 400.");
        assertEquals("No se ha actualizado el stock del producto, debe ingresar la Id y debe ser mayor a 0.", body);
    }

    @Test
    void testActualizarStock_CantidadCero() throws Exception {
        // Given
        String URL = "/inventario/actualizarStock?idProducto=1&cantidad=0";

        // When
        MvcResult response = mockMvc.perform(put(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado de la respuesta debería ser 400.");
        assertEquals("No se ha actualizado el stock del producto, debe ingresar una cantidad mayor a 0.", body);
    }

    @Test
    void testAgregarProducto() throws Exception {
        // Given
        String URL = "/inventario/nuevoProducto?nombre=Perfume1&precio=111.1&stock=11";

        Producto nuevoProducto = new Producto(null, "Perfume1", 111.1, 11);

        when(productoService.agregarProducto(any(Producto.class))).thenReturn(nuevoProducto);

        // When
        MvcResult response = mockMvc.perform(post(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test
    void testAgregarProducto_NombreVacio() throws Exception {
        // Given
        String URL = "/inventario/nuevoProducto?nombre=&precio=333.3&stock=33";

        // When
        MvcResult response = mockMvc.perform(post(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado debería ser 400.");
        assertEquals("El producto no fue agregado al inventario. Debe ingresar todos los datos.", body);
    }


}
