package com.pixelpear.perfulandia.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pixelpear.perfulandia.assemblers.ProductoModelAssembler;
import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Producto", description = "Controlador para gestionar productos en el inventario de Perfulandia")
@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor

public class ProductoControllerV2 {

    @Autowired
    private final ProductoModelAssembler assembler;
    private final ProductoService productoService;

    /* Obtener todo el inventario*/
    @GetMapping("/v2/stockInventario")
    @Operation(summary = "Obtener todo el inventario", description = "Devuelve una lista de todos los productos en el inventario.")

    public List<Producto> obtenerStock() {
        return productoService.mostrarStock();
    }


    /* Obtener producto por id*/   
    @GetMapping("/v2/obtenerProducto")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico del inventario según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })

    public ResponseEntity<?> obtenerProductoPorId(@RequestParam Long idProducto) {
    Optional<Producto> producto = productoService.mostrarProductoPorId(idProducto);
        if (producto.isPresent()) {
            return ResponseEntity.ok(assembler.toModel(producto.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
        }
    }


    /* Actualizar stock LISTOOOO*/
    @PutMapping("/v2/actualizarStock")
    @Operation(summary = "Actualizar stock de un producto", description = "Actualiza la cantidad de stock de un producto específico en el inventario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, falta información o datos inválidos")
    })

    public ResponseEntity<?> actualizarStock(@RequestParam (required = false) Long idProducto, @RequestParam (required = false) int cantidad) {
        if (idProducto == null || idProducto <= 0) {
            return ResponseEntity.badRequest().body("No se ha actualizado el stock del producto, debe ingresar la Id y debe ser mayor a 0.");
        } else if (cantidad == 0){
            return ResponseEntity.badRequest().body("No se ha actualizado el stock del producto, debe ingresar una cantidad mayor a 0.");
        }
        Producto productoActualizado = productoService.actualizarStock(idProducto, cantidad);
        return ResponseEntity.ok(assembler.toModel(productoActualizado));
    }
    

    /* Agregar nuevo producto LISTOOOOOOOO*/
    @PostMapping("/v2/nuevoProducto")
    @Operation(summary = "Agregar nuevo producto", description = "Agrega un nuevo producto al inventario de Perfulandia.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto agregado correctamente"),
        @ApiResponse(responseCode = "400", description = "Producto no agregado, debe ingresar todos los datos y deben ser mayor a cero.")
    })

    public ResponseEntity<?> agregarProducto(@RequestParam(required = false) String nombre, @RequestParam(required = false) Double precio,
        @RequestParam(required = false) Integer stock) {

        if (nombre == null || precio == null || stock == null) {
            return ResponseEntity.badRequest().body("El producto no fue agregado al inventario. Debe ingresar todos los datos.");
        } else if (precio <= 0 || stock <= 0) {
            return ResponseEntity.badRequest().body("El producto no fue agregado al inventario. El precio y el stock deben ser mayor a 0.");
        } else {
            Producto nuevoProducto = Producto.builder()
                .nombre(nombre)
                .precio(precio)
                .stock(stock)
                .build();
            productoService.agregarProducto(nuevoProducto);
            URI location = URI.create("/inventario/obtenerProducto?idProducto=" + nuevoProducto.getIdProducto());
            return ResponseEntity.created(location).body(assembler.toModel(nuevoProducto));
        }
    }
    
    
    /* Eliminar producto de inventario LISTOOO*/
    @DeleteMapping("/v2/borrarProducto")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario de Perfulandia según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, falta ID del producto o ID inválido")
    })
    
    public ResponseEntity<?> eliminarProducto(@RequestParam(required = false) Long idProducto){
        if (idProducto == null) {
            return ResponseEntity.badRequest().body("El producto no ha sido eliminado, debe ingresar el Id.");
        } else if (idProducto <= 0) {
            return ResponseEntity.badRequest().body("El producto no ha sido eliminado ya que el Id debe ser mayor a 0.");
            } else {
                Optional<Producto> productoEliminado = productoService.mostrarProductoPorId(idProducto);
                productoService.eliminarProducto(idProducto);
                if (productoEliminado.isPresent()) {
                    return ResponseEntity.ok(assembler.toModel(productoEliminado.get()));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado para eliminar.");
                }
            }
    }
}
