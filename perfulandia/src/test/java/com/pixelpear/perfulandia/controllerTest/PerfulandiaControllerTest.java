package com.pixelpear.perfulandia.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.HttpStatus;

import com.pixelpear.perfulandia.controller.ProductoController;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class PerfulandiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Test
    void testObtenerStock_Ok() throws Exception {
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
        assertTrue(body.contains("Perfume1"), "El cuerpo de la respuesta debería contener 'Perfume1'.");
    }

    @Test
    void testObtenerStockPorId() throws Exception {
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


}
