package com.pixelpear.perfulandia.serviceTest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelpear.perfulandia.controller.ProductoController;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.repository.ProductoRepository;
import com.pixelpear.perfulandia.service.ProductoService;


@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

	@InjectMocks
	private ProductoService productoService;

	@Mock
	private ProductoRepository productoRepository;

	private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

	@Test
	void testMostrarStock_DeberiaMostrarTodoElStock() {
		
		//Given
		Producto producto1 = new Producto(1L, "Perfume1", 100.0, 10);
		Producto producto2 = new Producto(2L, "Perfume2", 200.0, 20);
		List<Producto> productos = List.of(producto1, producto2);
		when(productoRepository.findAll()).thenReturn(productos);

		// When
		List<Producto> stock = productoService.mostrarStock();

		// Then

		logger.info("Cantidad de productos en stock: {}", stock.size());
    	logger.info("Productos en stock: {}", stock);

		assert stock.size() == 2;
		assert stock.contains(producto1);
		assert stock.contains(producto2);
		verify(productoRepository).findAll();
	}

	@Test
	void testMostrarStock_ListaVacia_DeberiaRetornarSinStock() {
		
		// Given
		List<Producto> stock = List.of();

		// Simula que el repositorio devuelve la lista stock vacía
		when(productoRepository.findAll()).thenReturn(stock);

		// When
		List<Producto> resultado = productoService.mostrarStock();

		// Then
		logger.info("Cantidad de productos en stock: {}", stock.size());
		assert resultado.isEmpty();
		verify(productoRepository).findAll();
	}

	@Test
	void testMostrarProductoPorId_DeberiaRetornarProductoPorId() {

		// Given
		Long idProducto = 1L;	
		Producto producto = new Producto(idProducto, "Perfume1", 100.0, 10);
		when(productoRepository.findById(idProducto)).thenReturn(java.util.Optional.of(producto));

		// When
		java.util.Optional<Producto> resultado = productoService.mostrarProductoPorId(idProducto);

		// Then
		logger.info("Producto encontrado: {}", resultado.orElse(null));
		assert resultado.isPresent();
		assert resultado.get().getIdProducto().equals(idProducto);
		verify(productoRepository).findById(idProducto);
	}

	@Test
	void testMostrarProductoPorId_IdNoEncontrado_DeberiaRetornarVacio() {

		// Given
		Long idProducto = 1L;	
		when(productoRepository.findById(idProducto)).thenReturn(Optional.empty());

		// When
		Optional<Producto> resultado = productoService.mostrarProductoPorId(idProducto);

		// Then
		logger.info("Producto encontrado: {}", resultado.orElse(null));
		assert resultado.isEmpty();
		verify(productoRepository).findById(idProducto);	
	}

	@Test
	void testActualizarStock_DeberiaActualizarStockDelProducto() {
		// Given
		Long idProducto = 1L;
		int cantidadAReponer = 5;
		Producto producto = new Producto(idProducto, "Perfume1", 100.0, 10);
		when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));
		when(productoRepository.save(producto)).thenReturn(producto);

		// When
		Producto resultado = productoService.actualizarStock(idProducto, cantidadAReponer);

		// Then
		logger.info("Producto actualizado: {}", resultado);
		assert resultado.getStock() == 15; // 10 + 5
		verify(productoRepository).findById(idProducto);
		verify(productoRepository).save(producto);
	}

	@Test
	void testActualizarStock_IdNoEncontrado_DeberiaRetornarNull() {
		// Given
		Long idProducto = 1L;
		int cantidadAReponer = 5;
		when(productoRepository.findById(idProducto)).thenReturn(Optional.empty());

		// When
		Producto resultado = productoService.actualizarStock(idProducto, cantidadAReponer);

		// Then
		logger.info("Producto no encontrado: {}", resultado);
		assert resultado == null;
		verify(productoRepository).findById(idProducto);
	}

	@Test
	void testAgregarProducto_DeberiaAgregarNuevoProducto() {

		// Given
		Producto nuevoProducto = new Producto(1L, "Perfume Nuevo", 150.0, 5);
		when(productoRepository.save(nuevoProducto)).thenReturn(nuevoProducto);

		// When
		Producto resultado = productoService.agregarProducto(nuevoProducto);

		// Then
		logger.info("Producto agregado: {}", resultado);
		assert resultado.getNombre().equals("Perfume Nuevo");
		assert resultado.getPrecio() == 150.0;
		assert resultado.getStock() == 5;
		verify(productoRepository).save(nuevoProducto);
	}

	@Test
	void testEliminarProducto_DeberiaEliminarProductoPorId() {

		// Given
		Long idProducto = 1L;
		Producto producto = new Producto(idProducto, "Perfume1", 100.0, 10);
		when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

		// When
		Producto resultado = productoService.eliminarProducto(idProducto);

		// Then
		logger.info("Producto eliminado: {}", resultado);
		assert resultado.getIdProducto().equals(idProducto);
		verify(productoRepository).findById(idProducto);
		verify(productoRepository).deleteById(idProducto);
	}

	@Test
	void testEliminarProducto_IdNoEncontrado_DeberiaRetornarNull() {
		// Given
		Long idProducto = 1L;
		when(productoRepository.findById(idProducto)).thenReturn(Optional.empty());

		// When
		Producto resultado = productoService.eliminarProducto(idProducto);

		// Then
		logger.info("Producto no encontrado para eliminar: {}", resultado);
		assert resultado == null;
		verify(productoRepository).findById(idProducto);
	}

}
