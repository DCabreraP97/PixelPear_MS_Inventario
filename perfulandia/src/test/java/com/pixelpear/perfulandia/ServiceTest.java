package com.pixelpear.perfulandia;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelpear.perfulandia.controller.ProductoController;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.repository.ProductoRepository;
import com.pixelpear.perfulandia.service.ProductoService;


@ExtendWith(MockitoExtension.class)
class ServiceTest {

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



}
