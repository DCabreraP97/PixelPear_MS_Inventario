package com.pixelpear.perfulandia.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class PerfulandiaController {
    private final ProductoRepository productoRepository;

    /* Para verificar el estado del ms */
    @GetMapping("/status")
    public String obtenerStatus() {
        return "El ms está activo";
    }

    /* Para mostrar todo el stock */
    @GetMapping("/stockInventario")
    public List<Producto> obtenerStockInventario() {
        List<Producto> stockActual = productoRepository.obtenerProducto;
        return stockActual;
    }
}
    
    


