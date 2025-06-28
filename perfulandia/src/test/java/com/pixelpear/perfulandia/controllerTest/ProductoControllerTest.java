package com.pixelpear.perfulandia.controllerTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pixelpear.perfulandia.assemblers.ProductoModelAssembler;
import com.pixelpear.perfulandia.controller.ProductoControllerV2;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.service.ProductoService;
import org.springframework.hateoas.EntityModel;

@WebMvcTest(ProductoControllerV2.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssembler productoModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(ProductoControllerV2.class);

    @Test
    void testObtenerStock_DeberiaRetornar200YLista() throws Exception {

        // Given
        List<Producto> productos = List.of(
            new Producto(1L, "Perfume1", 111.1, 11),
            new Producto(2L, "Perfume2", 222.2, 22)
        );

        //When
        when(productoService.mostrarStock()).thenReturn(productos);

        // Then
        mockMvc.perform(get("/v2/inventario/stockInventario"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].idProducto").value(1L))
            .andExpect(jsonPath("$[0].nombre").value("Perfume1"))
            .andExpect(jsonPath("$[0].precio").value(111.1))
            .andExpect(jsonPath("$[0].stock").value(11))
            .andExpect(jsonPath("$[1].idProducto").value(2L))
            .andExpect(jsonPath("$[1].nombre").value("Perfume2"))
            .andExpect(jsonPath("$[1].precio").value(222.2))
            .andExpect(jsonPath("$[1].stock").value(22));
    }

    @Test
    void testObtenerProductoPorId_DeberiaRetornar200YProducto() throws Exception {

        //Given
        List<Producto> productos = List.of(
            new Producto(1L, "Perfume1", 111.1, 11),
            new Producto(2L, "Perfume2", 222.2, 22)
        );

        //When
        when(productoService.mostrarProductoPorId(1L)).thenReturn(Optional.of(productos.get(0)));
        when(productoModelAssembler.toModel(any(Producto.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

        //Then
        mockMvc.perform(get("/v2/inventario/obtenerProducto?idProducto=1"))
        .andExpect(status().isOk())
            .andExpect(jsonPath("$.idProducto").value(1L))
            .andExpect(jsonPath("$.nombre").value("Perfume1"))
            .andExpect(jsonPath("$.precio").value(111.1))
            .andExpect(jsonPath("$.stock").value(11))
            ;
    }

    @Test
    void testObtenerProductoPorId_ArrojaNoEncontradoY404() throws Exception {
        
        //Given
        Long idProductoNoEncontrado = 500L;
        
        //When
        when(productoService.mostrarProductoPorId(idProductoNoEncontrado)).thenReturn(Optional.empty());

       //Then
        mockMvc.perform(get("/v2/inventario/obtenerProducto?idProducto=500"))
        .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarStock_DeberiaRetornar200YStockActualizado() throws Exception {
        // Given
        String URL = "/inventario/actualizarStock?idProducto=1&cantidad=5";
        
        Producto productoActualizado = new Producto(1L, "Perfume1", 111.1, 10 + 5);

        // When
        when(productoService.actualizarStock(1L, 5)).thenReturn(productoActualizado);
        when(productoModelAssembler.toModel(any(Producto.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));
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
    void testActualizarStock_IdCero_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/actualizarStock?idProducto=0&cantidad=5";

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
    void testActualizarStock_CantidadCero_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/actualizarStock?idProducto=1&cantidad=0";

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
    void testAgregarProducto_Arroja201() throws Exception {
        // Given
        String URL = "/v2/inventario/nuevoProducto?nombre=Perfume1&precio=111.1&stock=11";

        Producto nuevoProducto = new Producto(null, "Perfume1", 111.1, 11);

        when(productoService.agregarProducto(any(Producto.class))).thenReturn(nuevoProducto);
        when(productoModelAssembler.toModel(any(Producto.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

        // When
        MvcResult response = mockMvc.perform(post(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.CREATED.value(), status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test
    void testAgregarProducto_NombreVacio_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/nuevoProducto?nombre=&precio=333.3&stock=33";

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

    @Test 
    void testAgregarProducto_PrecioCero_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/nuevoProducto?nombre=Perfume1&precio=0&stock=11";

        // When
        MvcResult response = mockMvc.perform(post(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado debería ser 400.");
        assertEquals("El producto no fue agregado al inventario. El precio y el stock deben ser mayor a 0.", body);
    }

    @Test 
    void testAgregarProducto_StockCero_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/nuevoProducto?nombre=Perfume1&precio=111.1&stock=0";

        // When
        MvcResult response = mockMvc.perform(post(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado debería ser 400.");
        assertEquals("El producto no fue agregado al inventario. El precio y el stock deben ser mayor a 0.", body);
    }

    @Test 
    void testEliminarProducto_Arroja200() throws Exception {
        // Given
        String URL = "/v2/inventario/borrarProducto?idProducto=1";
        Producto producto = new Producto(1L, "Perfume1", 111.1, 11);
        when(productoService.mostrarProductoPorId(1L)).thenReturn(Optional.of(producto));
        when(productoService.eliminarProducto(1L)).thenReturn(producto);
        when(productoModelAssembler.toModel(any(Producto.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

        // When
        MvcResult response = mockMvc.perform(delete(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status, "El estado de la respuesta debería ser 200.");
        assertFalse(body.isEmpty(), "El cuerpo de la respuesta no debería estar vacío.");
    }

    @Test 
    void testEliminarProducto_IdCero_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/borrarProducto?idProducto=0";

        // When
        MvcResult response = mockMvc.perform(delete(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado de la respuesta debería ser 400.");
        assertEquals("El producto no ha sido eliminado ya que el Id debe ser mayor a 0.", body);
    }

    @Test 
    void testEliminarProducto_IdNulo_Arroja400() throws Exception {
        // Given
        String URL = "/v2/inventario/borrarProducto?idProducto=";

        // When
        MvcResult response = mockMvc.perform(delete(URL)).andReturn();
        logger.info("Status: " + response.getResponse().getStatus());
        logger.info("Body: " + response.getResponse().getContentAsString());

        // Then
        int status = response.getResponse().getStatus();
        String body = response.getResponse().getContentAsString();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status, "El estado de la respuesta debería ser 400.");
        assertEquals("El producto no ha sido eliminado, debe ingresar el Id.", body);
    }
}