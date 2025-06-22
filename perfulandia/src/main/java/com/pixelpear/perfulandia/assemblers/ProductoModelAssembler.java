package com.pixelpear.perfulandia.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pixelpear.perfulandia.controller.ProductoControllerV2;
import com.pixelpear.perfulandia.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).obtenerProductoPorId(producto.getIdProducto())).withRel("producto"),
            linkTo(methodOn(ProductoControllerV2.class).agregarProducto(
                    producto.getNombre(), producto.getPrecio(), producto.getStock()
                )).withRel("agregar"),
            linkTo(methodOn(ProductoControllerV2.class).eliminarProducto(producto.getIdProducto())).withRel("eliminar"),
            linkTo(methodOn(ProductoControllerV2.class).actualizarStock(producto.getIdProducto(), 1)).withRel("reponer")
        );
    }
}
