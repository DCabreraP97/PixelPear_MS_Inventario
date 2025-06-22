package com.pixelpear.perfulandia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> mostrarStock(){
        return productoRepository.findAll();
    }

    public Optional<Producto> mostrarProductoPorId(Long idProducto){
        return productoRepository.findById(idProducto);
    }

    public Producto actualizarStock(Long idProducto, int cantidadAReponer){
        return productoRepository.findById(idProducto)
            .map(producto ->{
            producto.setStock(producto.getStock() + cantidadAReponer);
            return productoRepository.save(producto);
        }).orElse(null);
    }

    public Producto agregarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long idProducto){
        productoRepository.deleteById(idProducto);
    }
}

